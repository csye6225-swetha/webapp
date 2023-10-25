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


sudo useradd -m -s /bin/bash webappusr

sudo cp packer/webapp.service /etc/systemd/system/

sudo systemctl daemon-reload
sudo systemctl enable webapp.service
sudo systemctl start webapp.service


echo "Software installation and configuration completed."
