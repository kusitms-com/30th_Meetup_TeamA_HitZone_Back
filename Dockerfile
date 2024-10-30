FROM openjdk:17-jdk-slim as build

WORKDIR /app

# 빌드된 JAR 파일과 정적 문서 파일을 Docker 이미지에 복사
COPY ./build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
COPY ./build/resources/main/static/docs /app/static/docs

# 최종 이미지
FROM openjdk:17-jdk-alpine as final

WORKDIR /app

COPY --from=build /app/app.jar app.jar
COPY --from=build /app/static/docs /app/static/docs

# HEALTHCHECK 추가
HEALTHCHECK --interval=5s --timeout=3s --start-period=30s --retries=3 \
  CMD curl --fail http://localhost:8080 || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080