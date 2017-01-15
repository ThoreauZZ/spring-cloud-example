FROM java:8
VOLUME /tmp
ADD cloud-admin-ui-1.0-SNAPSHOT.jar cloud-admin-ui.jar
RUN bash -c 'touch /cloud-admin-ui.jar'
EXPOSE 9003
ENTRYPOINT ["java","-jar","/cloud-admin-ui.jar"]