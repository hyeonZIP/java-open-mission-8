#!/bin/bash

PID=$(pgrep -f 'java.*jar')

if [ -n "$PID" ]; then
  echo "기존 애플리케이션 종료 (PID: $PID)"
  kill -15 $PID
  sleep 5
fi