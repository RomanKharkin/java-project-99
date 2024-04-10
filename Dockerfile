FROM gradle:8.4.0-jdk20

WORKDIR /

COPY / .

RUN ./gradlew --no-daemon dependencies

RUN ./gradlew --no-daemon build

CMD java -jar build/libs/app-0.0.1-SNAPSHOT.jar