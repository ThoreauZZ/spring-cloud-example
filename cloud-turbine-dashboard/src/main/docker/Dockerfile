FROM java:8
VOLUME /tmp
ADD cloud-turbine-dashboard-1.0-SNAPSHOT.jar cloud-turbine-dashboard.jar
RUN bash -c 'touch /cloud-turbine-dashboard.jar'
EXPOSE 9010
ENTRYPOINT ["java","-jar","/cloud-turbine-dashboard.jar"]
