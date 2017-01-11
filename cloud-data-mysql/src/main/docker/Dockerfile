FROM centos:centos6

RUN yum -y update; yum clean all
RUN yum -y install epel-release; yum clean all
RUN yum -y install mysql-server mysql pwgen supervisor bash-completion psmisc net-tools; yum clean all

ADD classes/start.sh /start.sh
ADD classes/config_mysql.sh /config_mysql.sh
ADD classes/supervisord.conf /etc/supervisord.conf
ADD classes/service-user.sql /service-user.sql

RUN chmod 755 /start.sh
RUN chmod 755 /config_mysql.sh
RUN /config_mysql.sh

EXPOSE 3306

CMD ["/bin/bash", "/start.sh"]