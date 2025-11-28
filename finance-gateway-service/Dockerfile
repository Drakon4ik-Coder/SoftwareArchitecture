FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY shared-contracts/pom.xml shared-contracts/
COPY gateway/pom.xml gateway/
COPY pricing-service/pom.xml pricing-service/
COPY inventory-service/pom.xml inventory-service/
COPY finance-gateway-service/pom.xml finance-gateway-service/
COPY notification-worker/pom.xml notification-worker/
RUN mvn -pl finance-gateway-service -am dependency:go-offline

COPY . .
RUN mvn -pl finance-gateway-service -am package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/finance-gateway-service/target/finance-gateway-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
