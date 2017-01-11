FROM java:8
VOLUME /tmp
ADD cloud-api-gateway-0.1.jar cloud-api-gateway.jar
RUN bash -c 'touch /cloud-api-gateway.jar'
EXPOSE 9006
ENTRYPOINT ["java","-jar","/cloud-api-gateway.jar"]