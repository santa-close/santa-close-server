FROM gradle:jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM ghcr.io/graalvm/jdk:ol8-java17-22.2.0
ENV LC_ALL=C.UTF-8
RUN useradd app
USER app
COPY --from=build /home/gradle/src/server-app/build/libs/server-app.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/urandom", "-jar", "app.jar"]
