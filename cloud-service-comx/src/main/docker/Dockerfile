FROM 10.69.42.85:5000/mxsev/java8
MAINTAINER zhaozhou
ADD @project.build.finalName@.jar @project.build.finalName@.jar
RUN sh -c 'touch /@project.build.finalName@.jar'
ADD bs-comx-conf /bs-comx-conf
ENV JAVA_OPTS=""
CMD ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker  -jar /@project.build.finalName@.jar" ]
