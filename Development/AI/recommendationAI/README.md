<!-- readme -->
<!-- 오류 발생 시 -->

Python 3.8 버전을 다운로드

# 기존 가상 환경 삭제

rm -rf venv

# 새 가상 환경 생성

python -m venv venv

# 가상 환경 활성화

source venv/Scripts/activate

# 폴더 이동

ls

# recommendationAI

cd recommendationAI

# pip 버전 업그레이드

python -m pip install --upgrade pip

# 의존성 설치

pip install -r requirements.txt

# bash에서

source myenv/Scripts/activate

pip install tensorflow==2.3.1

# Train the neural network (경로 주의)

python rs_nn.py

# Startup API Server to serve API Request

pip install jinja2==3.0.3
pip install itsdangerous==2.0.1
pip install werkzeug==2.0.3

python rs_api.py

# yml 파일 경로 주의

### User Recommendation API

#### End Point (Default)

```
http://127.0.0.1:5000/recommendation/user
```

#### Request Parameter

```
{
	"product_name": "U62300",
	"3year_return": "11.37",
	"standard_deviation": "20.12",
	"dividend": "0.22",
	"asset_class": "Equity Developed Market"
}
```

![Image of User Recommendation API](api-doc/user_recommendation.png)

### Product Recommendation API

#### End Point (Default)

```
http://127.0.0.1:5000/recommendation/product
```

#### Request Parameter

```
{
	"user": "CUST00000134",
	"age": 20,
	"gender": "M",
	"maritalStatus": "SINGLE",
	"haveChild": "N",
	"education": "SECONDARY"
}
```

![Image of Product Recommendation API](api-doc/product_recommendation.png)

### Recommendation Data API

#### End Point (Default)

```
http://localhost:5000/recommendation/data
```

#### Request Parameter

```
http://localhost:5000/recommendation/data?user=CUST00000134
http://localhost:5000/recommendation/data?age=20
http://localhost:5000/recommendation/data?gender=M
http://localhost:5000/recommendation/data?marital_status=SINGLE
http://localhost:5000/recommendation/data?have_child=N
http://localhost:5000/recommendation/data?education=SECONDARY
http://localhost:5000/recommendation/data?product_name=U62300
http://localhost:5000/recommendation/data?3year_return=11.37
http://localhost:5000/recommendation/data?standard_deviation=20.12
http://localhost:5000/recommendation/data?dividend=0.22
http://localhost:5000/recommendation/data?asset_class=Equity Developed Market
http://localhost:5000/recommendation/data?age_category=18-38
http://localhost:5000/recommendation/data?user_index=131
http://localhost:5000/recommendation/data?age_index=1
http://localhost:5000/recommendation/data?gender_index=1
http://localhost:5000/recommendation/data?education_index=2
http://localhost:5000/recommendation/data?have_child_index=0
http://localhost:5000/recommendation/data?marital_status_index=2
http://localhost:5000/recommendation/data?product_index=3
http://localhost:5000/recommendation/data?asset_class_index=0
```

![Image of Recommendation Data API](api-doc/recommendation_data.png)

### Data Visualisation Tool

![Image of Data Visualisation Tool](api-doc/visualisation.png)

### Production Architecture Diagram

![Image of Data Visualisation Tool](api-doc/architecture.png)
