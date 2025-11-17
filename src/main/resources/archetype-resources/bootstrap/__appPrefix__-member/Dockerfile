# syntax=docker/dockerfile-upstream:master-labs

# 构建阶段：使用Maven和JDK11
FROM maven:3.8.6-eclipse-temurin-11 as build

# 设置Maven参数以加速构建
ENV MAVEN_OPTS="-Xmx4g -Xms2g -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200"

# 设置工作目录
WORKDIR /build

# 首先只复制所有pom.xml文件以利用Docker缓存
COPY --parents pom.xml **/pom.xml .


# 下载依赖，这一步会被缓存，除非pom文件发生变化
RUN mvn dependency:go-offline -B -T 2C

# 然后复制其余源代码
COPY . .

# 打包
RUN mvn clean package -pl bootstrap/app-admin -am -DskipTests -T 2C

# 运行时阶段
FROM eclipse-temurin:11-jdk


# 设置Git版本信息参数
ARG GIT_COMMIT_ID
LABEL git.commit.id=$GIT_COMMIT_ID


# 设置时区和创建非root用户
RUN apt-get update && apt-get install -y tzdata && \
    ln -fs /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata && \
    groupadd -r appgroup && useradd -r -g appgroup appuser


# 设置部署目录
WORKDIR /app

# 从构建阶段复制构建好的jar包并设置权限
COPY --from=build /build/bootstrap/app-member/target/app-member-*.jar /app/app-member.jar
# 创建日志目录并设置权限
RUN mkdir -p /home/appuser/logs/AppMemberApplication && \
    chown -R appuser:appgroup /home/appuser/logs && \
    chown -R appuser:appgroup /app

# 切换到非root用户
USER appuser

# 配置环境变量
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp" \
    TZ="Asia/Shanghai"

# 添加健康检查配置
HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# 暴露端口
EXPOSE 8080

# 启动命令
CMD ["sh", "-c", "java ${JAVA_OPTS} -jar app-member.jar"]