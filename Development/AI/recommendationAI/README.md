<!-- readme -->
<!-- 오류 발생 시 -->

Python 3.8 버전을 다운로드

# 가상 환경 삭제 후 생성

cd Development/AI/recommendationAI 에 생성

rm -rf venv
rm -rf myenv

python -m venv venv

# 가상 환경 활성화

source venv/Scripts/activate

# pip 버전 업그레이드

python -m pip install --upgrade pip

# 의존성 설치

pip install -r requirements.txt

# 새로운 bash에서

cd Development/AI/recommendationAI

source venv/Scripts/activate

pip install tensorflow==2.3.1

# 새로운 bash에서

cd Development/AI/recommendationAI

source venv/Scripts/activate

pip install jinja2==3.0.3
pip install itsdangerous==2.0.1
pip install werkzeug==2.0.3

# Train the neural network (경로 주의)

cd src/main/python/

python rs_ds.py

python rs_nn.py

# Startup API Server to serve API Request

python rs_api.py

-----------postman 및 api 실행----------------

# 예시 User Recommendation API

#### End Point (Default)

```
http://127.0.0.1:5000/recommendation/user
```

#### Request Parameter

[POST]
http://127.0.0.1:5000/recommendation/product

[Headers]
Content-Type / application/json

{
"birth": "2000-01-13",
"gender": 1,
"salary": 4000000
}
