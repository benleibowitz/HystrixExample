FROM maven:3-jdk-8

RUN apt-get update

COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn install
CMD ["mvn", "spring-boot:run"]
