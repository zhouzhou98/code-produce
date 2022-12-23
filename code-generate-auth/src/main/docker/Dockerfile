# 指定基础镜像
# 你的开发环境是jdk8,就使用下面命令
FROM openjdk:8-jdk-alpine
# 数据卷，宿主机文件夹挂载到容器
VOLUME /tmp
ENV NET_IP=xxxx
# 复制文件
ADD code-generate-*.jar app.jar

# Docker容器启动时执行的命令，注意：是 -D 开头。如果有换行，后面要加上英文状态的 \ 进行连接
ENTRYPOINT exec java -jar  -Xms512m -Xmx512m -Xmn170m  -Dspring.cloud.nacos.discovery.ip=${NET_IP} /app.jar