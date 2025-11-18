FROM openjdk:21-ea-13-jdk-slim-bullseye
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8080
EXPOSE 5006
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,address=*:5006,server=y,suspend=n", "-jar", "app.jar"]
