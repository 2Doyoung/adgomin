#!/bin/bash

BUILD_JAR=$(ls ./build/libs/*.jar | grep 'SNAPSHOT.jar')
JAR_NAME=$(basename $BUILD_JAR)
DEPLOY_PATH=/home/ec2-user/app  # 배포용 별도 디렉토리 사용 권장 (홈 디렉토리 바로 아래 말고)
DEPLOY_LOG="$DEPLOY_PATH/deploy.log"
APP_LOG="$DEPLOY_PATH/application.log"

# 1. 배포 폴더가 없으면 생성 (권한은 ec2-user 소유로)
if [ ! -d "$DEPLOY_PATH" ]; then
  mkdir -p "$DEPLOY_PATH"
  chown ec2-user:ec2-user "$DEPLOY_PATH"
  chmod 755 "$DEPLOY_PATH"
fi

echo "> 빌드 파일 복사" >> $DEPLOY_LOG
cp $BUILD_JAR $DEPLOY_PATH
chown ec2-user:ec2-user $DEPLOY_PATH/$JAR_NAME

# 2. 기존 실행중인 애플리케이션 종료 (ec2-user 권한 프로세스만)
PID=$(pgrep -f $JAR_NAME)
if [ -n "$PID" ]; then
    echo "> 실행 중인 애플리케이션 종료 (PID: $PID)" >> $DEPLOY_LOG
    kill -15 $PID
    sleep 5
fi

# 3. 애플리케이션 실행 (ec2-user 권한으로 실행 권장)
echo "> 애플리케이션 실행" >> $DEPLOY_LOG
# nohup java -jar $DEPLOY_PATH/$JAR_NAME >> $APP_LOG 2>&1 &
sudo -u ec2-user nohup java -jar $DEPLOY_PATH/$JAR_NAME >> $APP_LOG 2>&1 &

echo "> 배포 완료" >> $DEPLOY_LOG