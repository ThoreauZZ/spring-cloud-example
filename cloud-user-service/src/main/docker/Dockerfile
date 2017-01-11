FROM java:8
VOLUME /tmp
ADD cloud-user-service-0.1.jar cloud-user-service.jar
RUN bash -c 'touch /cloud-user-service.jar'
EXPOSE 9002
ENTRYPOINT ["java","-jar","/cloud-user-service.jar"]