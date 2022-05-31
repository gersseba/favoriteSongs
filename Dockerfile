FROM adoptopenjdk/openjdk11:latest
MAINTAINER favoritesongs
COPY target/favoritesongs-1.0.jar favoritesongs.jar
ENTRYPOINT ["java","-jar","/favoritesongs.jar"]