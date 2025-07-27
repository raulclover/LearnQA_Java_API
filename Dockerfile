FROM maven:3.8.6-openjdk-11
WORKDIR /tests
COPY . .
CMD mvn clean test