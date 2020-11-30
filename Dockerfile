FROM openjdk:8-jdk-alpine
EXPOSE $PORT

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME
COPY ./build/libs/*.jar ./app.jar

CMD ["java","-jar","app.jar"]
