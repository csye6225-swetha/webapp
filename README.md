# webapp



Prerequisites for building and deploying this application locally: 



1. Install Java(JDK) version 21 
2. Install MySQL on your local machine
3. use Spring Initializer to generate a Spring Boot project or set it up manually.

Build and Deploy Instructions:

1. Clone the application source code 
2. Configure the MySQL database connection properties like spring.datasource.url, spring.datasource.username, and    spring.datasource.password to connect to your local MySQL instance.
3. Open a terminal and navigate to your project's root directory.
4. Build the application using Maven using following command: 
    mvn install -DskipTests 
   This command will compile your code, run tests, and package your application into a JAR.
5. Run the jar file using the command: 
   java -jar target/your-application-name.jar
   
   

