FROM java:8
VOLUME /tmp
ADD cloud-service-ext-1.0-SNAPSHOT.jar cloud-service-ext.jar
RUN bash -c 'touch /cloud-service-ext.jar'
EXPOSE 9005
ENTRYPOINT ["java","-jar","/cloud-service-ext.jar"]