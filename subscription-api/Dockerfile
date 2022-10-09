#
# BUILD STAGE
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /usr/src/app/src
COPY src/db.txt /usr/src/app/src/db.txt
COPY src/test/java/com/example/subscriptionapi/mock-db.txt /usr/src/app/src/mock-db.txt
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

#
# PACKAGE STAGE
#
FROM openjdk:11-jre-slim
COPY --from=build /usr/src/app/target/subscription-api-0.0.1-SNAPSHOT.jar /usr/app/subscription-api-0.0.1-SNAPSHOT.jar
COPY --from=build /usr/src/app/src/db.txt /usr/src/app/db.txt
COPY --from=build /usr/src/app/src/mock-db.txt /usr/src/app/src/mock-db.txt
EXPOSE 8081
CMD ["java","-jar","/usr/app/subscription-api-0.0.1-SNAPSHOT.jar"]