# 阶段1：构建阶段（包含测试执行）
FROM maven:3.9.9-openjdk-17 AS builder
WORKDIR /build

# 复制整个项目（包含测试代码）
COPY . .

# 运行测试并打包
RUN mvn clean package

# 阶段2：运行阶段
FROM openjdk:17-jdk-slim
WORKDIR /app

# 从构建阶段复制构建产物
COPY --from=builder /build/target/*.jar ./app.jar

# 保留测试代码（可选）
COPY --from=builder /build/src/test/ /app/src/test/

# 运行时配置
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]