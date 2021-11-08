#! /bin/bash
mvn clean package
docker-compose build quizApp
docker-compose up