#!/bin/bash

APP_HOME=/home/ubuntu/app
JAR_NAME=$(ls $APP_HOME/build/libs/*.jar | grep -v plain | head -n 1)

echo "기존 프로세스 종료"
pkill -f 'java.*\.jar' || true
sleep 2

# 로그 파일 생성 (권한 확인)
touch $APP_HOME/application.log || {
  echo "로그 파일 생성 실패 - 권한 문제"
  exit 1
}

echo "애플리케이션 시작: $JAR_NAME"
nohup java -jar "$JAR_NAME" > $APP_HOME/application.log 2>&1 &

PID=$!
echo "PID: $PID"
sleep 5

# 프로세스 확인
if ps -p $PID > /dev/null; then
   echo "애플리케이션 시작 완료"
else
   echo "애플리케이션 시작 실패"
   cat $APP_HOME/application.log
   exit 1
fi