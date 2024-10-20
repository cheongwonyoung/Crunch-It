import tensorflow as tf
import os
import pandas as pd
import numpy as np
from config import Config

# 상수 정의
USER_COLUMN = 'CUST_NUM'
AGE_COLUMN = 'AGE'
GENDER_COLUMN = 'GENDER'
MARITAL_STATUS_COLUMN = 'MARITAL'
HAVE_CHILD_COLUMN = 'HAVE_CHILD'
EDUCATION_COLUMN = 'EDU_LEVEL'
CODE_COLUMN = 'CODE'  # CUST_INVESTMENT 테이블의 CODE 컬럼
PRODUCT_3_YR_RETURN_COLUMN = '3YEAR_RETURN'
PRODUCT_STD_DEV_COLUMN = 'STD_DEV'
PRODUCT_DEVIDEND_COLUMN = 'DIVIDEND'
PRODUCT_ASSET_CLASS_COLUMN = 'ASSET_CLASS'
PRODUCT_ASSET_CLASS_INDEX_COLUMN = 'asset_index'
USER_INDEX_COLUMN = 'user_index'
AGE_INDEX_COLUMN = 'age_index'
GENDER_INDEX_COLUMN = 'gender_index'
MARITAL_STATUS_INDEX_COLUMN = 'marital_status_index'
HAVE_CHILD_INDEX_COLUMN = 'have_child_index'
EDUCATION_INDEX_COLUMN = 'education_index'
PRODUCT_INDEX_COLUMN = 'product_index'
PROBABILITY_COLUMN = 'probability'

# 입력 데이터의 컬럼 이름 리스트
IN_HEADERS = [
    USER_COLUMN, AGE_COLUMN, GENDER_COLUMN, MARITAL_STATUS_COLUMN, 
    HAVE_CHILD_COLUMN, EDUCATION_COLUMN, CODE_COLUMN,
    PRODUCT_3_YR_RETURN_COLUMN, PRODUCT_STD_DEV_COLUMN, 
    PRODUCT_DEVIDEND_COLUMN, PRODUCT_ASSET_CLASS_COLUMN
]

input_file = './src/main/resources/CUST_INVESTMENT.csv'
output_file = './output/data_reference.csv'


# CSV 데이터 읽기
in_df = pd.read_csv(
    input_file,
    sep=",",
    dtype={
        USER_COLUMN: 'str',
        GENDER_COLUMN: 'str',
        MARITAL_STATUS_COLUMN: 'str',
        HAVE_CHILD_COLUMN: 'str',
        EDUCATION_COLUMN: 'str',
        CODE_COLUMN: 'str',
        PRODUCT_3_YR_RETURN_COLUMN: 'float64',
        PRODUCT_STD_DEV_COLUMN: 'float64',
        PRODUCT_DEVIDEND_COLUMN: 'float64',
        PRODUCT_ASSET_CLASS_COLUMN: 'int'
    },
    parse_dates=[AGE_COLUMN],
    date_parser=lambda x: pd.to_datetime(x, errors='coerce', format='%Y-%m-%d')
)

# 나이 컬럼을 연도로 변환
in_df[AGE_COLUMN] = in_df[AGE_COLUMN].dt.year
print(in_df[AGE_COLUMN].head())  # 변환된 AGE 값 확인

# 월급 인덱스 생성
in_df[PRODUCT_ASSET_CLASS_INDEX_COLUMN] = pd.cut(in_df[PRODUCT_ASSET_CLASS_COLUMN], bins=[1000000, 2000000, 3000000, 4000000, 5000000], labels=[1, 2, 3, 4])

# 나이 인덱스 생성
in_df[AGE_INDEX_COLUMN] = pd.cut(in_df[AGE_COLUMN], bins=[1950, 1970, 1990, 2010, 2100], labels=[1, 2, 3, 4])

# 다른 인덱스 생성
in_df[GENDER_INDEX_COLUMN] = pd.factorize(in_df[GENDER_COLUMN])[0]
in_df[MARITAL_STATUS_INDEX_COLUMN] = pd.factorize(in_df[MARITAL_STATUS_COLUMN])[0]
in_df[HAVE_CHILD_INDEX_COLUMN] = pd.factorize(in_df[HAVE_CHILD_COLUMN])[0]
in_df[EDUCATION_INDEX_COLUMN] = pd.factorize(in_df[EDUCATION_COLUMN])[0]

# 사용자 및 제품 인덱스 추가
in_df[USER_INDEX_COLUMN] = pd.factorize(in_df[USER_COLUMN])[0]
in_df[PRODUCT_INDEX_COLUMN] = pd.factorize(in_df[CODE_COLUMN])[0]
print("User Index 확인:", in_df[USER_INDEX_COLUMN].head())
print("Product Index 확인:", in_df[PRODUCT_INDEX_COLUMN].head())

in_df.loc[:, USER_INDEX_COLUMN] = pd.factorize(in_df[USER_COLUMN])[0]
in_df.loc[:, PRODUCT_INDEX_COLUMN] = pd.factorize(in_df[CODE_COLUMN])[0]

# 최종 처리된 데이터 확인
print(in_df.head())  # 처리된 데이터 확인
print(in_df.columns)

# 처리된 데이터를 새로운 CSV 파일로 저장
in_df.to_csv(output_file, index=False)
print(f"Output saved to {output_file}")


class DataStore:
    @staticmethod
    def getNNFileInput():
        return in_df[[USER_INDEX_COLUMN, GENDER_INDEX_COLUMN, MARITAL_STATUS_COLUMN, 
                      HAVE_CHILD_COLUMN, EDUCATION_COLUMN, AGE_COLUMN, 
                      PRODUCT_INDEX_COLUMN, PRODUCT_3_YR_RETURN_COLUMN, 
                      PRODUCT_STD_DEV_COLUMN, PRODUCT_DEVIDEND_COLUMN, 
                      PRODUCT_ASSET_CLASS_COLUMN]]  # 필요한 모든 컬럼 추가

    @staticmethod
    def getNNFileOutput():
        return pd.read_csv(output_file)

def main():
    print(os.getcwd())  # 현재 작업 디렉토리 출력

if __name__ == '__main__':
    main()
