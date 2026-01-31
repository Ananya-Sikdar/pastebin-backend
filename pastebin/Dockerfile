# build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# copy backend project from pastebin folder
COPY pastebin/pom.xml .
COPY pastebin/src ./src

# build the jar
RUN mvn clean package -DskipTests

# runtime stage
FROM eclipse-temurin:17-jdk

WORKDIR /app

# copy built jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]