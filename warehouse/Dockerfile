FROM openjdk:11-jdk

VOLUME /tmp

ENV PORT=8086
EXPOSE 8086

ADD target/warehouse-0.0.1-SNAPSHOT.jar warehouse.jar

RUN bash -c 'touch /warehouse.jar'

ENTRYPOINT ["java","-Dserver.port=${PORT:-8082}","-Djava.security.egd=file:/dev/./urandom","-XX:+UseContainerSupport","-Xmx512m","-Xms128m","-jar","/warehouse.jar"]