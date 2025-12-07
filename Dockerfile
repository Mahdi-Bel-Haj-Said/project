FROM eclipse-temurin:11-jre-alpine

LABEL maintainer="mahdi.belhajsaid"
LABEL project="eventsProject"

WORKDIR /app

COPY target/eventsProject-1.0.0-SNAPSHOT.jar eventsProject.jar

# Exposer le port 8089
EXPOSE 8089

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar eventsProject.jar"]