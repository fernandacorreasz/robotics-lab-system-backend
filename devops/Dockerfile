FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

ENV MAVEN_OPTS="-Dfile.encoding=UTF-8" \
    JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]