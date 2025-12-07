# Utiliser une image JDK 11 comme base
FROM openjdk:11-jre-slim

# Informations sur le projet
LABEL maintainer="mahdibelhajsaid"
LABEL project="eventsProject"
LABEL version="1.0.0"

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR généré depuis le répertoire target
COPY target/eventsProject-1.0.0-SNAPSHOT.jar eventsProject.jar

# Exposer le port de l'application Spring Boot
EXPOSE 8080

# Variables d'environnement
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=prod

# Commande pour lancer l'application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar eventsProject.jar"]