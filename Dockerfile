FROM ghcr.io/graalvm/jdk:ol8-java17-22.2.0

COPY server-app/build/libs/server-app.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
