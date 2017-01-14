FROM java:8
VOLUME /tmp
ADD cloud-trade-service-1.0-SNAPSHOT.jar cloud-trade-service.jar
ADD wait-for-it.sh wait-for-it.sh
RUN bash -c 'touch /cloud-trade-service.jar' && \
    chmod +x wait-for-it.sh
EXPOSE 9002
CMD ["/bin/sh", "wait-for-it.sh", "config:9000"]
CMD ["java","-jar","/cloud-trade-service.jar"]