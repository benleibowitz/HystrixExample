# Hystrix example

This project uses Hystrix, Memcached, Spring Boot applications, and an Envoy sidecar proxy as a mini-example architecture. 

# Running the example

To run the application, run
```bash
mvn clean install
```
in the root directory, which will build and pull the required Docker images.

Then run
```bash
./scripts/start_all.sh
```

You can verify that the service is running by calling
```bash
curl -v localhost:10000/persons/12345
```

(you may need to run it twice, as the first request may timeout while both services initialize.)
