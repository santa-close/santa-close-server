FROM openjdk:17-jdk-alpine

WORKDIR /usr/src/app

COPY . .

# Run as non-root
RUN addgroup -g 1001 -S appuser && adduser -u 1001 -S appuser -G appuser
# RUN mkdir /logs && chown -R 1001:1001 /logs
RUN chown -R 1001:1001 /usr/src/app
USER 1001

RUN ./gradlew build --no-daemon -x test

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/urandom", "-jar", "app.jar"]
