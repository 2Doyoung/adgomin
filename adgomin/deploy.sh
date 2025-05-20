#!/bin/bash

BUILD_JAR=$(ls ./build/libs/*.jar | grep 'SNAPSHOT.jar')
JAR_NAME=$(basename $BUILD_JAR)
DEPLOY_PATH=/opt/adgomin  # EC2 사용자 홈이 아닌, 중립적인 경로 사용
DEPLOY_LOG="$DEPLOY_PATH/deploy.log"
APP_LOG="$DEPLOY_PATH/application.log"

# 1. 배포 폴더가 없으면 생성
if [ ! -d "$DEPLOY_PATH" ]; then
  sudo mkdir -p "$DEPLOY_PATH"
  sudo chown jenkins:jenkins "$DEPLOY_PATH"
  sudo chmod 755 "$DEPLOY_PATH"
fi

echo "> 빌드 파일 복사" >> $DEPLOY_LOG
sudo cp $BUILD_JAR $DEPLOY_PATH
sudo chown ec2-user:ec2-user $DEPLOY_PATH/$JAR_NAME

# 2. 기존 실행중인 애플리케이션 종료
PID=$(pgrep -f $JAR_NAME)
if [ -n "$PID" ]; then
    echo "> 실행 중인 애플리케이션 종료 (PID: $PID)" >> $DEPLOY_LOG
    kill -15 $PID
    sleep 5
fi

# 3. 애플리케이션 실행
echo "> 애플리케이션 실행" >> $DEPLOY_LOG
sudo -u ec2-user nohup java -jar $DEPLOY_PATH/$JAR_NAME >> $APP_LOG 2>&1 &

echo "> 배포 완료" >> $DEPLOY_LOG