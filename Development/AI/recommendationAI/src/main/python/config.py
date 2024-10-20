import yaml
import os
from pathlib import Path
import pandas as pd

# YAML 파일 경로 설정
CONFIG_PATH = os.path.join(os.path.dirname(__file__), "..", "resources", "application.yml")

# YAML 파일을 UTF-8 인코딩으로 읽기
with open(CONFIG_PATH, 'r', encoding='utf-8') as file:
    cf = yaml.load(file, Loader=yaml.FullLoader)


class Config:
    @staticmethod
    def getNNFileInput():
        return cf['nn']['file']['input']

    @staticmethod
    def getNNFileOutput():
        return cf['nn']['file']['output']

    @staticmethod
    def getNNCheckpoint():
        return cf['nn']['checkpoint']

    @staticmethod
    def getNNModel():
        return cf['nn']['model']


# CSV 파일을 읽는 함수 추가
def read_csv_file(file_path):
    try:
        # 한글 인코딩 문제를 해결하기 위해 utf-8 또는 euc-kr로 파일을 읽음
        df = pd.read_csv(file_path, encoding='utf-8')
        print(df)
    except UnicodeDecodeError:
        # utf-8 인코딩이 실패하면 euc-kr 인코딩으로 시도
        df = pd.read_csv(file_path, encoding='euc-kr')
        print(df)


def main():
    # YAML 파일 내용 출력
    print(cf)

    # YAML 파일에서 CSV 파일 경로 가져오기
    csv_file_path = Config.getNNFileInput()  # application.yml에서 가져온 CSV 파일 경로
    read_csv_file(csv_file_path)


if __name__ == '__main__':
    main()
