FROM java:8
VOLUME /tmp
ADD cloud-eureka-server-1.0-SNAPSHOT.jar cloud-eureka-server.jar
RUN bash -c 'touch /cloud-eureka-server.jar'
EXPOSE 9001
ENTRYPOINT ["java","-jar","/cloud-eureka-server.jar"]