#!/bin/bash

APP_HOME=/home/ubuntu/app

# Java 21 설치 확인
if ! command -v java &> /dev/null; then
    echo "Java 21 설치"
    sudo apt-get update
    sudo apt-get install -y openjdk-21-jdk
fi

# Java 버전 확인
java -version

# 디렉토리가 없으면 생성
if [ ! -d "$APP_HOME" ]; then
  mkdir -p $APP_HOME
fi

# 권한 설정
sudo chown -R ubuntu:ubuntu $APP_HOME
sudo chmod -R 755 $APP_HOME

# 로그 파일 미리 생성
touch $APP_HOME/application.log
chmod 644 $APP_HOME/application.log

echo "환경 준비 완료"