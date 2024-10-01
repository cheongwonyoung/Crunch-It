# evaluate_model.py 파일 수정 내용

import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from tensorflow.keras.models import load_model
from config import Config

# 모델 경로와 데이터 경로 가져오기
model_path = Config.getNNModel()
output_data_path = Config.getNNFileOutput()

try:
    # 모델 로드
    model = load_model(model_path)
except Exception as e:
    print(f"모델을 불러오는 중 오류가 발생했습니다: {e}")
    exit()

# 데이터 로드
try:
    df = pd.read_csv(output_data_path)
    print("데이터 컬럼:", df.columns)
except FileNotFoundError:
    print(f"파일을 찾을 수 없습니다: {output_data_path}")
    exit()
except Exception as e:
    print(f"데이터를 불러오는 중 오류가 발생했습니다: {e}")
    exit()

# 최대 user_index와 product_index 값 확인
max_user_index = df['user_index'].max()
max_product_index = df['product_index'].max()
print(f"최대 user_index: {max_user_index}, 최대 product_index: {max_product_index}")

# 모델의 임베딩 범위에 맞게 인덱스 조정
embedding_user_size = 25  # 모델의 user 임베딩 레이어 크기 (예시)
embedding_product_size = 25  # 모델의 product 임베딩 레이어 크기 (예시)

df['user_index'] = df['user_index'] % embedding_user_size  # user 인덱스를 임베딩 크기 내로 조정
df['product_index'] = df['product_index'] % embedding_product_size  # product 인덱스를 임베딩 크기 내로 조정

# 데이터 분할
x_train, x_test, y_train, y_test = train_test_split(
    df[['user_index', 'age_index', 'gender_index', 'marital_status_index',
        'have_child_index', 'education_index']],
    df['product_index'],
    test_size=0.2,
    random_state=42
)

# 모델 평가
try:
    test_loss, test_accuracy = model.evaluate([x_test, x_test], y_test, verbose=2)
    print(f"Test Loss: {test_loss:.4f}, Test Accuracy: {test_accuracy:.4f}")
except Exception as e:
    print(f"모델 평가 중 오류가 발생했습니다: {e}")
