import tensorflow as tf
import os
import pandas as pd
import numpy as np
# import ssl

# config.py에서 설정 값을 불러오기 위한 사용자 정의 클래스
from config import Config

# 상수 정의: 데이터 프레임의 컬럼 이름을 상수로 정의
USER_COLUMN = 'user'
AGE_COLUMN = 'age'
GENDER_COLUMN = 'gender'
MARITAL_STATUS_COLUMN = 'marital_status'
HAVE_CHILD_COLUMN = 'have_child'
EDUCATION_COLUMN = 'education'
PRODUCT_COLUMN = 'product_name'
PRODUCT_3_YR_RETURN_COLUMN = '3year_return'
PRODUCT_STD_DEV_COLUMN = 'standard_deviation'
PRODUCT_DEVIDEND_COLUMN = 'dividend'
PRODUCT_ASSET_CLASS_COLUMN = 'asset_class'
USER_INDEX_COLUMN = 'user_index'
AGE_CATEGORY_COLUMN = 'age_category'
AGE_INDEX_COLUMN = 'age_index'
GENDER_INDEX_COLUMN = 'gender_index'
MARITAL_STATUS_INDEX_COLUMN = 'marital_status_index'
HAVE_CHILD_INDEX_COLUMN = 'have_child_index'
EDUCATION_INDEX_COLUMN = 'education_index'
PRODUCT_INDEX_COLUMN = 'product_index'
PRODUCT_ASSET_CLASS_INDEX_COLUMN = 'asset_class_index'

# 입력 데이터의 컬럼 이름 리스트
IN_HEADERS = [
    USER_COLUMN, AGE_COLUMN, GENDER_COLUMN, MARITAL_STATUS_COLUMN, HAVE_CHILD_COLUMN, EDUCATION_COLUMN,
    PRODUCT_COLUMN, PRODUCT_3_YR_RETURN_COLUMN, PRODUCT_STD_DEV_COLUMN, PRODUCT_DEVIDEND_COLUMN, PRODUCT_ASSET_CLASS_COLUMN
]

# 출력 데이터의 컬럼 이름 리스트
OUT_HEADERS = [
    USER_COLUMN, AGE_COLUMN, GENDER_COLUMN, MARITAL_STATUS_COLUMN, HAVE_CHILD_COLUMN, EDUCATION_COLUMN,
    PRODUCT_COLUMN, PRODUCT_3_YR_RETURN_COLUMN, PRODUCT_STD_DEV_COLUMN, PRODUCT_DEVIDEND_COLUMN, PRODUCT_ASSET_CLASS_COLUMN,
    AGE_CATEGORY_COLUMN, USER_INDEX_COLUMN, AGE_INDEX_COLUMN, GENDER_INDEX_COLUMN, EDUCATION_INDEX_COLUMN,
    HAVE_CHILD_INDEX_COLUMN, MARITAL_STATUS_INDEX_COLUMN, PRODUCT_INDEX_COLUMN, PRODUCT_ASSET_CLASS_INDEX_COLUMN
]

PROBABILITY_COLUMN = 'probability (%)'  # 확률 컬럼 이름

# 입력 파일 읽기
in_df = pd.read_csv(
    Config.getNNFileInput(),  # 입력 파일 경로를 config에서 가져옴
    sep=",",  # 구분자 설정
    names=IN_HEADERS,  # 컬럼 이름 설정
    header=0,  # 첫 번째 줄을 헤더로 사용
    dtype={  # 각 컬럼의 데이터 타입 설정
        USER_COLUMN: 'str',
        AGE_COLUMN: np.int32,
        GENDER_COLUMN: 'str',
        MARITAL_STATUS_COLUMN: 'str',
        HAVE_CHILD_COLUMN: 'str',
        EDUCATION_COLUMN: 'str',
        PRODUCT_COLUMN: 'str',
        PRODUCT_3_YR_RETURN_COLUMN: np.float64,
        PRODUCT_STD_DEV_COLUMN: np.float64,
        PRODUCT_DEVIDEND_COLUMN: np.float64,
        PRODUCT_ASSET_CLASS_COLUMN: 'str'
    }
)

# 출력 파일 읽기
out_df = pd.read_csv(
    Config.getNNFileOutput(),  # 출력 파일 경로를 config에서 가져옴
    sep=",",  # 구분자 설정
    names=OUT_HEADERS,  # 컬럼 이름 설정
    header=0,  # 첫 번째 줄을 헤더로 사용
    dtype={  # 각 컬럼의 데이터 타입 설정
        USER_COLUMN: 'str',
        AGE_COLUMN: np.int32,
        GENDER_COLUMN: 'str',
        MARITAL_STATUS_COLUMN: 'str',
        HAVE_CHILD_COLUMN: 'str',
        EDUCATION_COLUMN: 'str',
        PRODUCT_COLUMN: 'str',
        PRODUCT_3_YR_RETURN_COLUMN: np.float64,
        PRODUCT_STD_DEV_COLUMN: np.float64,
        PRODUCT_DEVIDEND_COLUMN: np.float64,
        PRODUCT_ASSET_CLASS_COLUMN: 'str',
        AGE_CATEGORY_COLUMN: 'str',
        USER_INDEX_COLUMN: np.int32,
        AGE_INDEX_COLUMN: np.int32,
        GENDER_INDEX_COLUMN: np.int32,
        EDUCATION_INDEX_COLUMN: np.int32,
        HAVE_CHILD_INDEX_COLUMN: np.int32,
        MARITAL_STATUS_INDEX_COLUMN: np.int32,
        PRODUCT_INDEX_COLUMN: np.int32,
        PRODUCT_ASSET_CLASS_INDEX_COLUMN: np.int32
    }
)

# DataStore 클래스: 데이터 프레임을 반환하는 메서드를 제공
class DataStore:
    @staticmethod
    def getNNFileInput():
        return in_df

    @staticmethod
    def getNNFileOutput():
        return out_df


def main():
    print(os.getcwd())  # 현재 작업 디렉토리 출력


if __name__ == '__main__':
    main()
