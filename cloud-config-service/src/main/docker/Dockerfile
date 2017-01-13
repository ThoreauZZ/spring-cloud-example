FROM java:8
VOLUME /tmp
ADD cloud-config-service-1.0.jar cloud-config-service.jar
RUN bash -c 'touch /cloud-config-service.jar'
EXPOSE 9000
ENTRYPOINT ["java","-jar","/cloud-config-service.jar"]