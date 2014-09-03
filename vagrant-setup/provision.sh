#!/bin/bash
# 
# Install and prepare mysql instance
# 
############

apt-get update

echo "mysql-server-5.5 mysql-server-5.5/root_password ''" | debconf-set-selections
echo "mysql-server-5.5 mysql-server-5.5/root_password_again ''" | debconf-set-selections

DEBIAN_FRONTEND=noninteractive apt-get install mysql-server -y

# mysql configuration for listening on all IP address
sed -i 's/bind-address\s*= .*/bind-address = 0.0.0.0/' /etc/mysql/my.cnf

service mysql restart

# set privileges for user root
mysql -u root -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '<password>' WITH GRANT OPTION ;"

# configure new database with new user
mysql -u root -e "create database wicket_demo;"
mysql -u root -e "create user demo identified by 'demo'; grant all privileges on wicket_demo.* to 'demo'@'%' IDENTIFIED BY 'demo'; flush privileges;"
 
