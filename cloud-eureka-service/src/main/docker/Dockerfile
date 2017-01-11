FROM java:8
VOLUME /tmp
ADD cloud-eureka-service-0.1.jar cloud-eureka-service.jar
RUN bash -c 'touch /cloud-eureka-service.jar'
EXPOSE 9000
ENTRYPOINT ["java","-jar","/cloud-eureka-service.jar"]