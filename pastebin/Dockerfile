# build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# copy everything from the current context (pastebin folder)
COPY . .

# build the jar
RUN mvn clean package -DskipTests

# runtime stage
FROM eclipse-temurin:17-jdk

WORKDIR /app

# copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]