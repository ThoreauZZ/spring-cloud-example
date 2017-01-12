#!/bin/bash

__mysql_config() {
    echo "Running the mysql_config function."
    yum -y erase mysql mysql-server
    rm -rf /var/lib/mysql/ /etc/my.cnf
    yum -y install mysql mysql-server
    mysql_install_db
    chown -R mysql:mysql /var/lib/mysql
    /usr/bin/mysqld_safe &
    sleep 10
}

__start_mysql() {
    echo "Running the start_mysql function."
    mysqladmin -u root password 1234
    mysql -uroot -p1234 -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '1234' WITH GRANT OPTION;flush privileges;"
    mysql -uroot -p1234 -e "UPDATE user SET password = password('1234') where user='root';flush privileges;"
    mysql -uroot -p1234 -e "select user, host, password FROM mysql.user;"
    mysql -uroot -p1234 < /springcloud.sql
    killall mysqld
    sleep 10
}

# Call all functions
__mysql_config
__start_mysql