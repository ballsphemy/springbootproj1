FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
RUN ls -al /target  # Add this line to debug the contents

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/InventoryManagementSystemApplication-0.0.1-SNAPSHOT.jar InventoryManagementSystemApplication.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "InventoryManagementSystemApplication.jar"]