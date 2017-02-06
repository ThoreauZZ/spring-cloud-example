FROM centos:centos6

RUN yum -y update && \
    yum -y install epel-release && \
    yum -y install mysql-server mysql &&\
    yum -y install pwgen supervisor bash-completion psmisc net-tools && \
    yum clean all

ADD classes/start.sh /start.sh
ADD classes/config_mysql.sh /config_mysql.sh
ADD classes/supervisord.conf /etc/supervisord.conf
ADD classes/springcloud.sql /springcloud.sql

RUN chmod 755 /start.sh && \
    chmod 755 /config_mysql.sh  && \
    /config_mysql.sh

EXPOSE 3306

CMD ["/bin/bash", "/start.sh"]
