import tensorflow as tf
import pandas as pd
import numpy as np
from tensorflow.keras.layers import Dense, Flatten, Embedding, Concatenate, Input
from tensorflow.keras.losses import BinaryCrossentropy
from tensorflow.keras.optimizers import Adam
from tensorflow.keras import Model
import scipy.sparse as sp
import random
import os
from config import Config
import rs_ds as ds
from rs_ds import DataStore

strategy = tf.distribute.experimental.MultiWorkerMirroredStrategy()
print('Number of devices: {}'.format(strategy.num_replicas_in_sync))

def get_train_samples(train_mat, num_negatives):
    user_input, item_input, labels = [], [], []
    num_user, num_item = train_mat.shape
    for (u, i) in train_mat.keys():
        user_input.append(u)
        item_input.append(i)
        labels.append(1)

        # Negative sampling
        negative_sampling_list = random.sample(range(num_item), num_negatives)
        for j in negative_sampling_list:
            if (u, j) not in train_mat.keys():
                user_input.append(u)
                item_input.append(j)
                labels.append(0)
    return user_input, item_input, labels

def get_model(num_users, num_items, latent_dim=8):
    input_user = Input(shape=(6,), dtype='float32', name='user_input')  # 사용자 입력 차원
    input_item = Input(shape=(6,), dtype='float32', name='item_input')  # 제품 입력 차원

    # 사용자 임베딩
    user_embedding = Embedding(input_dim=num_users + 1, output_dim=latent_dim)(input_user[:, 0:1])
    item_embedding = Embedding(input_dim=num_items + 1, output_dim=latent_dim)(input_item[:, 0:1])

    # Flatten 임베딩 레이어
    user_latent = Flatten()(user_embedding)
    item_latent = Flatten()(item_embedding)

    # 나머지 사용자 및 제품 특성
    user_features = input_user[:, 1:]  # 5개의 사용자 특성
    item_features = input_item[:, 1:]  # 5개의 제품 특성

    # 모든 특성 결합
    concat_features = Concatenate()([user_latent, item_latent, user_features, item_features])

    # MLP 레이어 추가
    mlp_vector = concat_features
    for i in [256, 128, 64]:
        mlp_vector = Dense(i, activation='relu')(mlp_vector)

    # 출력 레이어
    output = Dense(1, activation='sigmoid')(mlp_vector)

    # 모델 정의
    model = Model(inputs=[input_user, input_item], outputs=output)
    return model

def main():
    df = DataStore.getNNFileInput()

    # 데이터프레임의 컬럼을 출력하여 확인
    print("데이터프레임 첫 5행:\n", df.head())  # 데이터의 첫 5행 출력
    print("데이터프레임 컬럼:", df.columns)

    # 사용자와 제품 인덱스 변환
    df.loc[:, ds.USER_INDEX_COLUMN] = df['user_index']  # user_index 사용

    # 여기에 제품 인덱스를 생성하는 코드 추가
    df.loc[:, ds.PRODUCT_INDEX_COLUMN] = df['product_index']  # product_index 사용

    # 나머지 인덱스들 생성 (원본 컬럼을 사용)
    df.loc[:, ds.GENDER_INDEX_COLUMN] = df['gender_index']
    df.loc[:, ds.MARITAL_STATUS_INDEX_COLUMN] = pd.factorize(df['MARITAL'])[0]  # marital_status_index 생성
    df.loc[:, ds.HAVE_CHILD_INDEX_COLUMN] = pd.factorize(df['HAVE_CHILD'])[0]  # have_child_index 생성
    df.loc[:, ds.EDUCATION_INDEX_COLUMN] = pd.factorize(df['EDU_LEVEL'])[0]  # education_index 생성
    df.loc[:, ds.AGE_INDEX_COLUMN] = pd.cut(df['AGE'], bins=[1950, 1970, 1990, 2010, 2100], labels=[1, 2, 3, 4])
    df.loc[:, ds.PRODUCT_ASSET_CLASS_INDEX_COLUMN] = pd.cut(df['ASSET_CLASS'], bins=[1000000, 2000000, 3000000, 4000000, 5000000], labels=[1, 2, 3, 4])

    x_train = df[ds.USER_INDEX_COLUMN]
    y_train = df[ds.PRODUCT_INDEX_COLUMN]

    mat_train = sp.dok_matrix((x_train.shape[0], y_train.nunique()), dtype=np.float32)
    for userIndex, productIndex in zip(x_train, y_train):
        mat_train[userIndex, productIndex] = 1

    num_negatives = 2
    user_input_train, item_input_train, labels_train = get_train_samples(mat_train, num_negatives)

    modified_x_train = []
    modified_y_train = []

    for ui, ii in zip(user_input_train, item_input_train):
        user_df = df[df[ds.USER_INDEX_COLUMN] == ui]
        product_df = df[df[ds.PRODUCT_INDEX_COLUMN] == ii]
        if not user_df.empty and not product_df.empty:
            modified_x_train_element = [
                ui,
                user_df[ds.GENDER_INDEX_COLUMN].to_numpy()[0],
                user_df[ds.MARITAL_STATUS_INDEX_COLUMN].to_numpy()[0],
                user_df[ds.HAVE_CHILD_INDEX_COLUMN].to_numpy()[0],
                user_df[ds.EDUCATION_INDEX_COLUMN].to_numpy()[0],
                user_df[ds.AGE_INDEX_COLUMN].to_numpy()[0]  # 나이 추가
            ]

            modified_x_train.append(modified_x_train_element)

            modified_y_train_element = [
                ii,
                product_df[ds.PRODUCT_3_YR_RETURN_COLUMN].to_numpy()[0],
                product_df[ds.PRODUCT_STD_DEV_COLUMN].to_numpy()[0],
                product_df[ds.PRODUCT_DEVIDEND_COLUMN].to_numpy()[0],
                product_df[ds.PRODUCT_ASSET_CLASS_INDEX_COLUMN].to_numpy()[0],
                0  # 여기에 실제 6번째 특성을 추가해야 함 (ex: product_df[ds.ANOTHER_FEATURE_COLUMN].to_numpy()[0])
            ]
            modified_y_train.append(modified_y_train_element)

    modified_x_train = np.array(modified_x_train)
    modified_y_train = np.array(modified_y_train)

    print("사용자 입력 형태:", modified_x_train.shape)  # (n_samples, 6)
    print("제품 입력 형태:", modified_y_train.shape)    # (n_samples, 6)
    print("라벨 크기:", np.array(labels_train).shape)  # (n_samples,)

    num_users = df[ds.USER_INDEX_COLUMN].nunique()
    num_items = df[ds.PRODUCT_INDEX_COLUMN].nunique()
    latent_dim = 64
    batch_size = 256
    epochs = 40
    learning_rate = 0.001

    with strategy.scope():
        model = get_model(num_users + 1, num_items + 1, latent_dim)
        model.compile(optimizer=Adam(learning_rate=learning_rate),
                      loss=BinaryCrossentropy(from_logits=False), metrics=['accuracy'])

    # 모델 학습
    hist = model.fit([modified_x_train, modified_y_train], np.array(labels_train),
                     batch_size=batch_size, epochs=epochs, verbose=1, shuffle=True)

    # 예측 테스트
    test_user_input = np.array(modified_x_train)
    test_item_input = np.array(modified_y_train)
    print("테스트 사용자 입력 형태:", test_user_input.shape)  # (n_samples, 6)
    print("테스트 제품 입력 형태:", test_item_input.shape)    # (n_samples, 6)

    # predictions를 수행하기 전에 shape 확인
    if test_user_input.shape[1] != 6 or test_item_input.shape[1] != 6:
        print("입력 형태가 모델의 기대와 일치하지 않습니다.")
    else:
        predictions = model.predict([test_user_input, test_item_input])
        print("예측 결과:", predictions)

if __name__ == '__main__':
    main()
