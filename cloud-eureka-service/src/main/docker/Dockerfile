FROM java:8
VOLUME /tmp
ADD cloud-eureka-service-1.0-SNAPSHOT.jar cloud-eureka-service.jar
RUN bash -c 'touch /cloud-eureka-service.jar'
EXPOSE 9000
ENTRYPOINT ["java","-jar","/cloud-eureka-service.jar"]