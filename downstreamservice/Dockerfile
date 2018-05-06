FROM maven:3-jdk-8

RUN apt-get update

COPY target/*.jar /usr/src/app/
WORKDIR /usr/src/app
CMD ["/bin/sh", "-c", "java -jar downstreamservice*.jar"]
