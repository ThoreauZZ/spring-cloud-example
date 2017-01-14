FROM java:8
VOLUME /tmp
ADD cloud-config-server-1.0-SNAPSHOT.jar cloud-config-server.jar
RUN bash -c 'touch /cloud-config-server.jar'
EXPOSE 9000
ENTRYPOINT ["java","-jar","/cloud-config-server.jar"]