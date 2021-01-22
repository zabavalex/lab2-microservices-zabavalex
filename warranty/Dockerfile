FROM openjdk:11-jdk

VOLUME /tmp

ENV PORT=8580
EXPOSE 8580

ADD target/warranty-0.0.1-SNAPSHOT.jar warranty.jar

RUN bash -c 'touch /warranty.jar'

ENTRYPOINT ["java","-Dserver.port=${PORT:-8081}","-Djava.security.egd=file:/dev/./urandom","-XX:+UseContainerSupport","-Xmx512m","-Xms128m","-jar","/warranty.jar"]