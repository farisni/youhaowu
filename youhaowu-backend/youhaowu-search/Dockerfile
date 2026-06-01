FROM eclipse-temurin:17-jre-alpine
COPY target/youhaowu-search-*.jar app.jar
HEALTHCHECK --interval=5s --retries=12 --timeout=3s \
    CMD wget -qO- http://localhost:8094/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "/app.jar", "--server.port=8094"]
