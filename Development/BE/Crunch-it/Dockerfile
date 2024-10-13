# 1. Gradle을 사용하여 빌드하는 이미지를 설정 (Gradle 7.x 버전 사용, 필요에 따라 변경)
FROM gradle:7.5.1-jdk17 AS build

# 2. Gradle 환경 변수 설정
ENV GRADLE_USER_HOME /home/gradle/.gradle

# 3. 프로젝트 소스를 복사
COPY --chown=gradle:gradle . /home/gradle/project

# 4. 작업 디렉토리를 이동
WORKDIR /home/gradle/project

# 5. 프로젝트 빌드 (war 파일 생성)
RUN gradle clean war --no-daemon


# 4. JRE 기반의 런타임 이미지
FROM tomcat:9.0.96-jdk17-temurin-jammy
# 6. 빌드 단계에서 생성된 WAR 파일 복사
COPY --from=build /home/gradle/project/build/libs/*.war /usr/local/tomcat/webapps/

EXPOSE 8080
# 7. Tomcat 서버 실행
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]