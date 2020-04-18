FROM postgres:latest
RUN apt-get update
RUN apt-get install openjdk-11-jdk -y
COPY script.sql docker-entrypoint-initdb.d/script.sql
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=studres
RUN mkdir /usr/local/pgsql
RUN mkdir /usr/local/pgsql/data
RUN chown postgres:postgres  /usr/local/pgsql/data

COPY .mvn .mvn
COPY mvnw mvnw
COPY src src
COPY pom.xml pom.xml
RUN ./mvnw package
RUN mv target/studentsresourcesmanager-0.0.1-SNAPSHOT.jar stud-res.jar
RUN rm -rf target src .mvn mvnw pom.xml
RUN echo "java -jar stud-res.jar &" > docker-entrypoint-initdb.d/start-app.sh
EXPOSE 8080
EXPOSE 5432