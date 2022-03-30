#docker 镜像/容器名字或者jar名字 这里都命名为这个
SERVER_NAME=simple-web
echo "---------Start---------"

#容器id
CID=$(docker ps -a | grep "$SERVER_NAME" | awk '{print $1}')
#镜像id
IID=$(docker images | grep "$SERVER_NAME" | awk '{print $3}')

if [ -e dist ]
then
    rm -f $FILE
fi
cp -r ../dist ./

# 构建镜像
docker build -t $SERVER_NAME:latest .

# 构建docker镜像
#if [ -n "$IID" ]; then
#        echo "删除$SERVER_NAME镜像，IID=$IID"
#        docker rmi $IID
#        echo "已删除$SERVER_NAME，重新构建镜像"
#        docker build -t $SERVER_NAME .
#else
#        echo "开始构建$SERVER_NAME"
#        docker build -t $SERVER_NAME .
#        echo "$SERVER_NAME构建完成！准备启动"
#fi
# 停掉原来的
if [ -n "$CID" ]; then
        echo "正在停止$SERVER_NAME，CID=$CID"
        docker stop $CID
        echo "$SERVER_NAME已停止！"
else
        echo "容器 $SERVER_NAME 未运行，即将启动..."
fi

# 清理无效镜像<none>
docker image prune -f

# 运行docker容器
docker run --rm --name $SERVER_NAME -d -p 8000:80 $SERVER_NAME

# 是否启动成功
RES=$(docker ps | grep "$SERVER_NAME" | awk '{print $3}')

if [ -n "$RES" ]; then
    echo "$SERVER_NAME 已启动..."
else
    echo "$SERVER_NAME 启动失败！"
fi
echo "---------End---------"
