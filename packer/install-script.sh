#!/bin/bash


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

sudo apt install maven 


echo "Software installation and configuration completed."
