FROM adoptopenjdk/openjdk9
EXPOSE 8800

VOLUME /tmp
VOLUME /files
VOLUME /logs
#RUN ["chmod", "a+w", "/files"]
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
ENV JAVA_OPTS=""
ADD scp.jar app.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=local -DloggerPath=/logs -jar app.jar" ]
