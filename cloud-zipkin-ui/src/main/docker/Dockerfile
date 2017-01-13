FROM java:8
VOLUME /tmp
ADD cloud-zipkin-ui-1.0-SNAPSHOT.jar cloud-zipkin-ui.jar
RUN bash -c 'touch /cloud-zipkin-ui.jar'
EXPOSE 9006
ENTRYPOINT ["java","-jar","/cloud-zipkin-ui.jar"]
