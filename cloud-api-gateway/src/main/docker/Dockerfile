FROM java:8
VOLUME /tmp
ADD cloud-api-gateway-1.0.jar cloud-api-gateway.jar
RUN bash -c 'touch /cloud-api-gateway.jar'
EXPOSE 9006
ENTRYPOINT ["java","-jar","/cloud-api-gateway.jar"]