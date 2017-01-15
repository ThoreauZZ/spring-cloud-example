FROM java:8
VOLUME /tmp
ADD cloud-turbine-dashbord-1.0-SNAPSHOT.jar cloud-turbine-dashbord.jar
RUN bash -c 'touch /cloud-turbine-dashbord.jar'
EXPOSE 9010
ENTRYPOINT ["java","-jar","/cloud-turbine-dashbord.jar"]
