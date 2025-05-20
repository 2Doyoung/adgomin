#!/bin/bash

BUILD_JAR=$(ls ./build/libs/*.jar | grep 'SNAPSHOT.jar')
JAR_NAME=$(basename $BUILD_JAR)
DEPLOY_PATH=/home/ec2-user/app
DEPLOY_LOG="$DEPLOY_PATH/deploy.log"
APP_LOG="$DEPLOY_PATH/application.log"

# 1. 배포 폴더가 없으면 생성
if [ ! -d "$DEPLOY_PATH" ]; then
  mkdir -p "$DEPLOY_PATH"
  chmod 755 "$DEPLOY_PATH"
fi

echo "> 빌드 파일 복사" >> $DEPLOY_LOG

# 2. 기존 실행 중인 애플리케이션 종료
PID=$(pgrep -f $JAR_NAME)
if [ -n "$PID" ]; then
    echo "> 실행 중인 애플리케이션 종료 (PID: $PID)" >> $DEPLOY_LOG
    kill -15 $PID
    sleep 5
fi

# 3. 애플리케이션 실행
echo "> 애플리케이션 실행" >> $DEPLOY_LOG
nohup java -jar $DEPLOY_PATH/$JAR_NAME >> $APP_LOG 2>&1 &  # ✅ Jenkins 유저가 직접 실행

echo "> 배포 완료" >> $DEPLOY_LOG
