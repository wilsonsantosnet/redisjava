FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY demo/target/demo-0.0.1-SNAPSHOT.war redisjava.war
COPY demo/agent/applicationinsights-agent-3.6.2.jar applicationinsights-agent-3.6.2.jar
EXPOSE 8080
ENV APPLICATIONINSIGHTS_CONNECTION_STRING="..."
ENTRYPOINT exec java $JAVA_OPTS -javaagent:"applicationinsights-agent-3.6.2.jar" -jar redisjava.war
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar redisjava.jar
