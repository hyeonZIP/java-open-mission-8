#!/bin/bash

APP_HOME=/home/ubuntu/app

# JAR 파일 찾기
echo "JAR 파일 검색 중..."
for jar in $APP_HOME/build/libs/*.jar; do
  # 실제 파일인지 확인 + plain.jar 제외
  if [[ -f "$jar" && "$jar" != *plain* ]]; then
    JAR_NAME="$jar"
    break
  fi
done

# JAR 파일 없으면 명확한 에러 메시지와 함께 종료
if [[ -z "$JAR_NAME" ]]; then
  echo "❌ 에러: $APP_HOME/build/libs 에서 JAR 파일을 찾을 수 없습니다"
  ls -la $APP_HOME/build/libs/ || echo "디렉토리가 존재하지 않습니다"
  exit 1
fi

echo "✅ 찾은 JAR: $JAR_NAME"

echo "기존 프로세스 종료"
pkill -f 'java.*\.jar' || true
sleep 2

# 로그 파일 생성 (권한 확인)
touch $APP_HOME/application.log || {
  echo "로그 파일 생성 실패 - 권한 문제"
  exit 1
}

echo "애플리케이션 시작: $JAR_NAME"
nohup java -jar "$JAR_NAME" \
  --spring.profiles.active=prod \
  > $APP_HOME/application.log 2>&1 &

PID=$!
echo "PID: $PID"
sleep 5

# 프로세스 확인
if ps -p $PID > /dev/null; then
   echo "✅ 애플리케이션 시작 완료"
   exit 0
else
   echo "❌ 애플리케이션 시작 실패"
   echo "--- 로그 내용 ---"
   tail -n 50 $APP_HOME/application.log
   exit 1
fi