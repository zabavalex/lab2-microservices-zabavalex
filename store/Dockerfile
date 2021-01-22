FROM openjdk:11-jdk

VOLUME /tmp

ENV PORT=8084
EXPOSE 8084

ADD target/store-0.0.1-SNAPSHOT.jar store.jar

RUN bash -c 'touch /store.jar'

ENTRYPOINT ["java","-Dserver.port=${PORT:-8084}","-Djava.security.egd=file:/dev/./urandom","-XX:+UseContainerSupport","-Xmx512m","-Xms128m","-jar","/store.jar"]