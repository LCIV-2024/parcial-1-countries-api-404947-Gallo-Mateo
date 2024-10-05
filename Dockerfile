FROM openjdk:17-jdk
COPY target/lciii-scaffolding-0.0.1-SNAPSHOT.jar countries.jar

ENTRYPOINT ["java", "-jar", "countries.jar"]