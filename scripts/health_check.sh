#!/bin/bash

# 헬스체크 (Spring Boot Actuator 사용 시)
for i in {1..10}; do
  echo "헬스체크 시도 $i/10"

  HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/actuator/health)

  if [ "$HTTP_CODE" -eq 200 ]; then
    echo "헬스체크 성공!"
    exit 0
  fi

  sleep 10
done

echo "헬스체크 실패"
exit 1