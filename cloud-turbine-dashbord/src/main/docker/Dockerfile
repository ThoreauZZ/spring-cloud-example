FROM java:8
VOLUME /tmp
ADD cloud-turbine-dashbord-0.1.jar cloud-turbine-dashbord.jar
RUN bash -c 'touch /cloud-api-gateway.jar'
EXPOSE 9006
ENTRYPOINT ["java","-jar","/cloud-turbine-dashbord.jar"]