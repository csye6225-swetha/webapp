#!/bin/bash

# Set the root password for MariaDB
export MYSQL_ROOT_PASSWORD="root@1234"

# Set DEBIAN_FRONTEND to noninteractive
export DEBIAN_FRONTEND=noninteractive

if [ -f webapp/target/webapp-0.0.1-SNAPSHOT.jar ]; then
  echo "The .jar file exists. Copying it to /opt/myapp/app.jar..."
  cp webapp/target/webapp-0.0.1-SNAPSHOT.jar /home/admin/webapp-0.0.1-SNAPSHOT.ja
else
  echo "The .jar file does not exist."
fi


# Install Oracle JDK 21

wget -O "jdk-21_linux-x64_bin.deb" "https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.deb"
sudo dpkg -i jdk-21_linux-x64_bin.deb
sudo apt --fix-broken install

# Update package repositories
sudo apt update

# Install Maven
sudo apt -y install maven

# Install MariaDB Server
sudo apt -y install mariadb-server

# Remove anonymous users
sudo mysql -e "DELETE FROM mysql.user WHERE User='';"

# Disallow remote root login
sudo mysql -e "DELETE FROM mysql.user WHERE User='root' AND Host NOT IN ('localhost','127.0.0.1','::1');"

# Remove the test database
sudo mysql -e "DROP DATABASE IF EXISTS test;"
sudo mysql -e "DELETE FROM mysql.db WHERE Db='test' OR Db='test\\_%';"


sudo mysql -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY 'root@1234' WITH GRANT OPTION;"

# Reload privilege tables
sudo mysql -e "FLUSH PRIVILEGES;"

echo "Software installation and configuration completed."
