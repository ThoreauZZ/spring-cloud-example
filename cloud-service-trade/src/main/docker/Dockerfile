FROM java:8
MAINTAINER erdaoya

ADD @project.build.finalName@.jar cloud-app.jar
ADD wait-for-it.sh wait-for-it.sh
RUN sh -c 'touch /cloud-app.jar'  && \
    chmod +x wait-for-it.sh

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar", "/cloud-app.jar"]




