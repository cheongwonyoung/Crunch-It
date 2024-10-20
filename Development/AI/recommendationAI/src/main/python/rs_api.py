import tensorflow as tf
import pandas as pd
import numpy as np
from flask import Flask, request, jsonify
from flask_cors import CORS
from tensorflow.keras.models import load_model
from config import Config
import rs_ds as ds
from rs_ds import DataStore

app = Flask(__name__)
CORS(app)

# 모델 로드
try:
    model = load_model(Config.getNNModel(), compile=False, custom_objects={'tf': tf})
    model.summary()
except Exception as e:
    print("모델 로드 중 오류 발생:", str(e))

df = DataStore.getNNFileOutput()
print("데이터프레임 컬럼:", df.columns)

@app.route("/recommendation/product", methods=['POST'])
def productRecommendationApi():
    content = request.json
    print("요청 내용:", content)
    print(df.head())

    # 필수 입력값 확인
    if not content or not all(key in content for key in ["birth", "gender", "salary"]):
        return jsonify({"message": "필수 입력값이 누락되었습니다."}), 400

    desired_birth = pd.to_datetime(content["birth"], format='%Y-%m-%d', errors='coerce')
    desired_gender = content["gender"]
    desired_salary = float(content["salary"])

    # 입력값 유효성 검사
    if pd.isnull(desired_birth) or desired_gender not in [0, 1] or desired_salary <= 0:
        return jsonify({"message": "잘못된 입력값입니다."}), 400

    # 사용자 데이터 선택
    user_df = df[(df[ds.AGE_COLUMN] == desired_birth.year) & (df[ds.GENDER_COLUMN] == desired_gender)]

    if user_df.empty:
        return jsonify({"message": "해당 조건에 맞는 사용자를 찾을 수 없습니다."}), 400

    desired_user_index = user_df[ds.USER_INDEX_COLUMN].iloc[0]

    # 사용자 특징 가져오기
    user_features = user_df[[ds.USER_INDEX_COLUMN, ds.GENDER_INDEX_COLUMN,
                             ds.MARITAL_STATUS_INDEX_COLUMN, ds.HAVE_CHILD_INDEX_COLUMN,
                             ds.EDUCATION_INDEX_COLUMN, ds.AGE_INDEX_COLUMN]].iloc[0].values

    # 잠재적인 제품 선택 (중복 제거하지 않음)
    potential_products = df[df[ds.USER_INDEX_COLUMN] != desired_user_index]
    print(f"잠재적 제품 수: {len(potential_products)}")

    if potential_products.empty:
        return jsonify({"message": "잠재적인 제품이 없습니다."}), 400

    # 모든 제품 선택 (중복 포함)
    products = potential_products[ds.PRODUCT_INDEX_COLUMN]
    print(f"선택된 제품 수: {len(products)}")

    if len(products) == 0:
        return jsonify({"message": "잠재적인 제품이 없습니다."}), 400

    # AGE 값을 인덱스로 변환하는 함수
    def age_to_index(age):
        if age < 30:
            return 0
        elif age < 40:
            return 1
        elif age < 50:
            return 2
        elif age < 60:
            return 3
        else:
            return 4

    # Salary 값을 asset_index로 변환하는 함수 (기존에 생성한 방식)
    def salary_to_asset_index(salary):
        if salary < 2000000:
            return 1
        elif salary < 3000000:
            return 2
        elif salary < 4000000:
            return 3
        elif salary < 5000000:
            return 4
        else:
            return 5

    # Salary 값을 asset_index로 변환
    desired_asset_index = salary_to_asset_index(desired_salary)

    # products 리스트의 각 제품 특성을 생성하는 부분에서 수정
    modified_products = []
    product_codes = []
    for p in products:
        product_df = df[df[ds.PRODUCT_INDEX_COLUMN] == p]
        if not product_df.empty:
            # 제품 특성 배열 (6개의 특성을 가져오도록 수정)
            product_features = product_df[[ds.AGE_COLUMN, ds.GENDER_COLUMN, ds.MARITAL_STATUS_INDEX_COLUMN,
                                           ds.HAVE_CHILD_INDEX_COLUMN, ds.PRODUCT_STD_DEV_COLUMN,
                                           ds.PRODUCT_ASSET_CLASS_INDEX_COLUMN]].iloc[0].values

            # AGE 값을 인덱스로 변환
            product_features[0] = age_to_index(product_features[0])

            # Salary를 asset_index로 변환한 값으로 제품 특성을 수정
            product_features[5] = desired_asset_index

            # 제품 특성이 6개로 맞춰지지 않은 경우 0으로 채우기
            if len(product_features) < 6:
                product_features = np.pad(product_features, (0, 6 - len(product_features)), 'constant')

            modified_products.append([p] + list(product_features))  # 제품 인덱스 포함
            product_codes.append(product_df[ds.CODE_COLUMN].iloc[0])  # 제품의 CODE 값 추가

    # 배열로 변환
    modified_products = np.array(modified_products)

    # 사용자 입력과 제품 입력 크기를 맞춰서 배열 준비
    if len(modified_products) > 0:
        items = np.array(modified_products[:, 1:])  # 제품 특성만 추출
        users = np.tile(user_features, (len(items), 1))  # 사용자 특성을 아이템의 수만큼 반복

        # 입력 데이터를 float32로 변환
        users = users.astype(np.float32)
        items = items.astype(np.float32)

        # 배열 크기 확인
        print("사용자 입력 형태:", users.shape)  # (n_samples, 6)
        print("제품 입력 형태:", items.shape)    # (n_samples, 6)

        # 예측 수행
        try:
            results = model.predict([users, items], batch_size=256, verbose=1)
            print("예측 결과 크기:", results.shape)
            results = results.flatten()

            # 예측 결과 크기 확인
            if len(results) == len(modified_products):  # 제품 인덱스 수와 동일한지 확인
                print("예측 결과의 길이와 제품의 수가 일치합니다.")

                # 결과를 데이터프레임에 저장
                result_df = pd.DataFrame({
                    ds.PROBABILITY_COLUMN: 100 * results,  # 예측 확률을 퍼센트로 변환
                    ds.CODE_COLUMN: product_codes          # 제품의 CODE 값
                })

                # 데이터프레임을 예측 확률에 따라 정렬
                result_df = result_df.sort_values(by=[ds.PROBABILITY_COLUMN], ascending=False)

                # 상위 5개의 결과만 반환
                top_5_results = result_df.head(5)

                # 결과를 출력하여 확인
                print("예측 결과 샘플:", top_5_results)

                return top_5_results.to_json(orient="records")  # JSON 형식으로 반환

            else:
                print(f"Error: 예측 결과({len(results)})와 제품({len(modified_products)}) 수가 일치하지 않습니다.")
                return jsonify({"message": "예측 결과 길이 오류"}), 500
        except Exception as e:
            print("예측 중 오류 발생:", str(e))
            return jsonify({"message": "예측 중 오류가 발생했습니다.", "error": str(e)}), 500

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000, debug=True)
