# Build stage
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Download all required dependencies into one layer
RUN mvn dependency:go-offline -B

# Copy your src directory
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17.0.1-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/InventoryManagementSystemApplication-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]