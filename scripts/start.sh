#!/bin/bash

APP_HOME=/home/ubuntu/app
JAR_NAME=$(ls $APP_HOME/build/libs/*.jar | head -n 1)

echo "기존 프로세스 종료"
pkill -f 'java.*\.jar' || true

echo "애플리케이션 시작: $JAR_NAME"
nohup java -jar "$JAR_NAME" > $APP_HOME/application.log 2>&1 &

echo "PID: $!"
sleep 5

# 프로세스 확인
if ps -p $! > /dev/null; then
   echo "애플리케이션 시작 완료"
else
   echo "애플리케이션 시작 실패"
   cat $APP_HOME/application.log
   exit 1
fi