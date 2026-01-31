FROM eclipse-temurin:17-jdk

WORKDIR /app

# copy whole project
COPY . .

# build the jar
RUN ./mvn clean package -DskipTests

# expose spring boot port
EXPOSE 8080

# run whatever jar was produced in target/
CMD sh -c "java -jar target/*.jar"