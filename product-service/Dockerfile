FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

RUN adduser --disabled-password --gecos '' appuser && chown appuser:appuser app.jar
USER appuser

EXPOSE 8080

ENV JAVA_OPTS=""
ENV SERVER_PORT=8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl --fail http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
