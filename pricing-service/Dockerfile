FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copy root and module poms for dependency resolution
COPY pom.xml .
COPY shared-contracts/pom.xml shared-contracts/
COPY gateway/pom.xml gateway/
COPY pricing-service/pom.xml pricing-service/
COPY inventory-service/pom.xml inventory-service/
COPY finance-gateway-service/pom.xml finance-gateway-service/
COPY notification-worker/pom.xml notification-worker/
RUN mvn -pl pricing-service -am dependency:go-offline

# Copy full sources and build only the pricing-service module
COPY . .
RUN mvn -pl pricing-service -am package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/pricing-service/target/pricing-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
