FROM eclipse-temurin:17-jre-alpine
COPY target/youhaowu-product-*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "--server.port=8091"]
