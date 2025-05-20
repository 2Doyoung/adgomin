
echo ">>> 권한 복구 시작"

# ec2-user 홈 디렉토리 소유자 및 그룹 복구
sudo chown -R ec2-user:ec2-user /home/ec2-user

# 권한 복구 (홈 디렉토리는 755 권한 권장)
sudo chmod 755 /home/ec2-user

# 하위 파일/디렉토리는 기본 권한으로(필요하면 조절)
sudo find /home/ec2-user -type d -exec chmod 755 {} \;
sudo find /home/ec2-user -type f -exec chmod 644 {} \;

echo ">>> 권한 복구 완료"