FROM java:8
EXPOSE 8081
COPY target/quizApp-0.0.1-SNAPSHOT.jar quizApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/quizApp-0.0.1-SNAPSHOT.jar"]