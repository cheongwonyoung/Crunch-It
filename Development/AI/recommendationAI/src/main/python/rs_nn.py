import tensorflow as tf
import pandas as pd
import numpy as np
from pandas import HDFStore

# 필요한 라이브러리와 레이어들을 임포트
from tensorflow.keras.layers import Dense, Flatten, Embedding, Concatenate, Multiply, Input
from tensorflow.keras.losses import BinaryCrossentropy
from tensorflow.keras.regularizers import l2
from tensorflow.keras.optimizers import Adam
from tensorflow.keras import Model
import scipy.sparse as sp
import random
import os

# 커스텀 클래스와 데이터 로드
from config import Config
import rs_ds as ds
from rs_ds import DataStore

# MultiWorkerMirroredStrategy를 사용하여 분산 학습 설정
strategy = tf.distribute.experimental.MultiWorkerMirroredStrategy()
print('Number of devices: {}'.format(strategy.num_replicas_in_sync))


# 모델 생성 함수
def get_model(num_users, num_items, latent_dim=8, dense_layers=[64, 32, 16, 8],
              reg_layers=[0, 0, 0, 0], reg_mf=0):
    # 입력 레이어 생성
    input_user = Input(shape=(6,), dtype='float64', name='user_input')
    input_item = Input(shape=(6,), dtype='float64', name='item_input')

    # 임베딩 레이어 설정
    mf_user_embedding = Embedding(input_dim=num_users, output_dim=latent_dim,
                                  name='mf_user_embedding',
                                  embeddings_initializer='glorot_uniform',
                                  embeddings_regularizer=l2(reg_mf),
                                  input_length=1)
    mf_item_embedding = Embedding(input_dim=num_items, output_dim=latent_dim,
                                  name='mf_item_embedding',
                                  embeddings_initializer='glorot_uniform',
                                  embeddings_regularizer=l2(reg_mf),
                                  input_length=1)
    mlp_user_embedding = Embedding(input_dim=num_users, output_dim=int(dense_layers[0]/2),
                                   name='mlp_user_embedding',
                                   embeddings_initializer='glorot_uniform',
                                   embeddings_regularizer=l2(reg_layers[0]),
                                   input_length=1)
    mlp_item_embedding = Embedding(input_dim=num_items, output_dim=int(dense_layers[0]/2),
                                   name='mlp_item_embedding',
                                   embeddings_initializer='glorot_uniform',
                                   embeddings_regularizer=l2(reg_layers[0]),
                                   input_length=1)

    # MF와 MLP의 잠재 벡터를 생성하여 결합
    mf_user_latent = Flatten()(mf_user_embedding(input_user))
    mf_item_latent = Flatten()(mf_item_embedding(input_item))
    mf_cat_latent = Multiply()([mf_user_latent, mf_item_latent])

    mlp_user_latent = Flatten()(mlp_user_embedding(input_user))
    mlp_item_latent = Flatten()(mlp_item_embedding(input_item))
    mlp_cat_latent = Concatenate()([mlp_user_latent, mlp_item_latent])

    mlp_vector = mlp_cat_latent

    # MLP의 밀집 레이어를 생성하여 학습
    for i in range(1, len(dense_layers)):
        layer = Dense(dense_layers[i],
                      activity_regularizer=l2(reg_layers[i]),
                      activation='relu',
                      name='layer%d' % i)
        mlp_vector = layer(mlp_vector)

    # MF와 MLP의 결과를 결합하여 최종 예측 레이어를 생성
    predict_layer = Concatenate()([mf_cat_latent, mlp_vector])
    result = Dense(1, activation='sigmoid',
                   kernel_initializer='lecun_uniform', name='result')

    model = Model(inputs=[input_user, input_item],
                  outputs=result(predict_layer))
    return model


# 학습 샘플을 가져오는 함수
def get_train_samples(train_mat, num_negatives):
    user_input, item_input, labels = [], [], []
    num_user, num_item = train_mat.shape
    for (u, i) in train_mat.keys():
        user_input.append(u)
        item_input.append(i)
        labels.append(1)

        negative_sampling_list = random.sample(
            range(num_item), min(num_negatives, num_item))
        for j in negative_sampling_list:
            if (u, j) not in train_mat.keys():
                user_input.append(u)
                item_input.append(j)
                labels.append(0)
    return user_input, item_input, labels


# 메인 함수, 학습 데이터 준비 및 모델 학습
def main():
    # 파일 읽기
    df = DataStore.getNNFileInput()

    # 연령대 범주 설정
    age_bins = [0, 18, 30, 44, 54, 90, np.inf]
    age_names = ['<18', '18-30', '30-44', '44-54', '54-90', '90+']
    df[ds.AGE_CATEGORY_COLUMN] = pd.cut(
        df[ds.AGE_COLUMN], age_bins, labels=age_names)

    # 사용자와 제품을 고유한 카테고리 코드로 변환
    df[ds.USER_INDEX_COLUMN] = df[ds.USER_COLUMN].astype('category').cat.codes
    df[ds.AGE_INDEX_COLUMN] = df[ds.AGE_CATEGORY_COLUMN].astype(
        'category').cat.codes
    df[ds.GENDER_INDEX_COLUMN] = df[ds.GENDER_COLUMN].astype(
        'category').cat.codes
    df[ds.EDUCATION_INDEX_COLUMN] = df[ds.EDUCATION_COLUMN].astype(
        'category').cat.codes
    df[ds.HAVE_CHILD_INDEX_COLUMN] = df[ds.HAVE_CHILD_COLUMN].astype(
        'category').cat.codes
    df[ds.MARITAL_STATUS_INDEX_COLUMN] = df[ds.MARITAL_STATUS_COLUMN].astype(
        'category').cat.codes
    df[ds.PRODUCT_INDEX_COLUMN] = df[ds.PRODUCT_COLUMN].astype(
        'category').cat.codes
    df[ds.PRODUCT_ASSET_CLASS_INDEX_COLUMN] = df[ds.PRODUCT_ASSET_CLASS_COLUMN].astype(
        'category').cat.codes

    x_train = df[ds.USER_INDEX_COLUMN]
    y_train = df[ds.PRODUCT_INDEX_COLUMN]

    # 희소 행렬 형태로 학습 데이터 저장
    mat_train = sp.dok_matrix((x_train.shape[0], len(
        y_train.unique())), dtype=np.float32)
    for userIndex, productIndex in zip(x_train, y_train):
        mat_train[userIndex, productIndex] = 1

    # 하이퍼파라미터 설정
    verbose = 1
    epochs = 40
    batch_size = 256
    latent_dim = 64
    dense_layers = [512, 256, 128, 64, 32, 16, 8]
    reg_layers = [0.000001] * len(dense_layers)
    reg_mf = 0.000001
    num_negatives = 2
    learning_rate = 0.001

    num_users, num_items = mat_train.shape

    # 모델 생성 및 컴파일
    with strategy.scope():
        model = get_model(num_users+1, (num_items*3)+1, latent_dim,
                          dense_layers, reg_layers, reg_mf)

        model.compile(optimizer=Adam(learning_rate=learning_rate),  # 'lr'을 'learning_rate'로 변경
                      loss=BinaryCrossentropy(from_logits=True), metrics=['accuracy'])
    print(model.summary())

    user_input_train, item_input_train, labels_train = get_train_samples(
        mat_train, num_negatives)

    # 학습 입력 데이터 구성
    modified_x_train = []
    for ui in user_input_train:
        user_df = df[df[ds.USER_INDEX_COLUMN] == ui]
        modified_x_train_element = [
            ui,
            user_df[ds.AGE_INDEX_COLUMN].to_numpy()[0],
            user_df[ds.GENDER_INDEX_COLUMN].to_numpy()[0],
            user_df[ds.MARITAL_STATUS_INDEX_COLUMN].to_numpy()[0],
            user_df[ds.HAVE_CHILD_INDEX_COLUMN].to_numpy()[0],
            user_df[ds.EDUCATION_INDEX_COLUMN].to_numpy()[0]
        ]
        modified_x_train.append(modified_x_train_element)

    modified_y_train = []
    for ii in item_input_train:
        product_df = df[df[ds.PRODUCT_INDEX_COLUMN] == ii]
        modified_y_train_element = [
            ii,
            product_df[ds.PRODUCT_3_YR_RETURN_COLUMN].to_numpy()[0],
            product_df[ds.PRODUCT_STD_DEV_COLUMN].to_numpy()[0],
            product_df[ds.PRODUCT_DEVIDEND_COLUMN].to_numpy()[0],
            product_df[ds.PRODUCT_ASSET_CLASS_INDEX_COLUMN].to_numpy()[0],
            1
        ]
        modified_y_train.append(modified_y_train_element)

    # 체크포인트 설정 및 모델 학습
    cp_callback = tf.keras.callbacks.ModelCheckpoint(filepath=os.path.join(Config.getNNCheckpoint(), 'model_checkpoint.weights.h5'),
                                                     save_weights_only=True,
                                                     verbose=0)

    hist = model.fit([np.array(modified_x_train), np.array(modified_y_train)], np.array(labels_train),
                     batch_size=batch_size, epochs=epochs, verbose=verbose, shuffle=True, callbacks=[cp_callback])

    # 모델 저장 및 출력 데이터 저장
    model.save(Config.getNNModel())
    // 여기가 pkl 저장하는 곳
    // spring에서 joblib 라이브러리로 pkl 파일을 불러와서 사용
    
    df.to_csv(Config.getNNFileOutput(), index=False)


if __name__ == '__main__':
    main()
