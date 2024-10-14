# 1. 빌드 스테이지
FROM gradle:8.3.0-jdk17 AS build
WORKDIR /app

# 프로젝트의 모든 파일을 Docker 컨테이너로 복사
COPY . .

# Spring Boot 애플리케이션 빌드 (JAR 파일 생성)
RUN gradle clean build -x test --no-daemon

# 2. 실행 스테이지
FROM openjdk:17-jdk-slim

# 빌드된 JAR 파일을 복사
COPY --from=build /app/build/libs/*.jar /app/app.jar

# 애플리케이션 포트
EXPOSE 8080

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]