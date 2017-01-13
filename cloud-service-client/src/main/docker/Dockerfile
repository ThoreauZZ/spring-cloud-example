FROM java:8
VOLUME /tmp
ADD cloud-service-client-1.0.jar cloud-service-client.jar
RUN bash -c 'touch /cloud-service-client.jar'
EXPOSE 9005
ENTRYPOINT ["java","-jar","/cloud-service-client.jar"]