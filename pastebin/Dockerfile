# ---------- build stage (has Maven) ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# copy only pom first for better caching
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

# now copy the rest of the source
COPY src ./src

# build the jar
RUN mvn clean package -DskipTests

# ---------- runtime stage (only Java) ----------
FROM eclipse-temurin:17-jdk

WORKDIR /app

# copy built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]