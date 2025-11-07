#!/bin/bash

APP_HOME=/home/ubuntu/app
JAR_NAME=$(ls $APP_HOME/build/libs/*.jar | head -n 1)

echo "애플리케이션 시작: $JAR_NAME"

nohup java -jar "$JAR_NAME" > $APP_HOME/application.log 2>&1 &

sleep 3
echo "애플리케이션 시작 완료"