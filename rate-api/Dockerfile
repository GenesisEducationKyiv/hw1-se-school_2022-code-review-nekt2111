#
# BUILD STAGE
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
ENV CRYPTO_CURRENCY_PROVIDER=coinbase
RUN mvn -f /usr/src/app/pom.xml clean package

#
# PACKAGE STAGE
#
FROM openjdk:11-jre-slim
COPY --from=build /usr/src/app/target/rate-api-0.0.1-SNAPSHOT.jar /usr/app/rate-api-0.0.1-SNAPSHOT.jar
ENV CRYPTO_CURRENCY_PROVIDER=coinbase
EXPOSE 8083
CMD ["java","-jar","/usr/app/rate-api-0.0.1-SNAPSHOT.jar"]