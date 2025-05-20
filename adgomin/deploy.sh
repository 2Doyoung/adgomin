BUILD_JAR=$(ls ./build/libs/*.jar | grep 'SNAPSHOT.jar')
JAR_NAME=$(basename $BUILD_JAR)
DEPLOY_PATH=/home/ec2-user
DEPLOY_LOG="$DEPLOY_PATH/deploy.log"
APP_LOG="$DEPLOY_PATH/application.log"
PID=$(pgrep -f $JAR_NAME)

echo "> 빌드 파일 복사" >> $DEPLOY_LOG
cp $BUILD_JAR $DEPLOY_PATH

echo "> 실행 중인 애플리케이션 종료" >> $DEPLOY_LOG
if [ -n "$PID" ]; then
    kill -15 $PID
    sleep 5
fi

echo "> 애플리케이션 실행" >> $DEPLOY_LOG
nohup java -jar $DEPLOY_PATH/$JAR_NAME >> $APP_LOG 2>&1 &