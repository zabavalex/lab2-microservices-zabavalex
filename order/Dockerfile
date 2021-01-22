FROM openjdk:11-jdk

VOLUME /tmp

ENV PORT=8080
EXPOSE 8080

ADD target/order-0.0.1-SNAPSHOT.jar order.jar

RUN bash -c 'touch /order.jar'

ENTRYPOINT ["java","-Dserver.port=${PORT:-8083}","-Djava.security.egd=file:/dev/./urandom","-XX:+UseContainerSupport","-Xmx512m","-Xms128m","-jar","/order.jar"]