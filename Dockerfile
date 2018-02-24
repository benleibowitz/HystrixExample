FROM maven:3-jdk-8

# Don't ask me why I need to do this instead of using pre-built images...
RUN apt-get update

RUN ls
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn install
CMD ["mvn", "spring-boot:run"]
