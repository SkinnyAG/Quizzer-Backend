# Quizzer Back-End

Welcome to the quizzer back-end! This file should help you get started with this project.

## Requirements
You need docker and maven to run this application.

## Preperation
We recommend doing a mvn clean install to prepare the project for development and testing on your local machine. This ensures a clean and consistent state with all dependencies resolved and the project correctly built!
```sh
mvn clean install
```

## Run Back-end
To start up the docker container and fill the database with test-data use:
```sh
docker-compose up -d
```

Then to run the server, use:

```sh
mvn spring-boot:run
```


## Run JUnit tests
To only run the JUnit tests:
```sh
mvn test
```
