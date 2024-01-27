# Assignment Management System


This Spring Boot application serves as an Assignment Management System, providing functionality for users to add, modify, and delete assignments. It supports the submission of assignments, complete with checks for maximum attempts and adherence to deadlines. The system also features user authentication, implemented through basic HTTP authentication using email and password, ensuring secure and controlled access. Additionally, it is integrated with a CI/CD pipeline for building AMIs, with the CI process including checks to ensure that integration tests are successfully executed, guaranteeing robustness and reliability of the deployments. 


## CI/CD Pipeline

### Continuous Integration (CI)

 CI pipeline is designed to ensure the code quality and functionality of the application:

- **Pull Request Checks**: Before merging any pull request, our CI system performs the following checks:
  - Code Quality: It checks for integration tests to ensure that new code changes don't break existing functionality.
  - Packer Validation: We validate the Packer configuration to ensure the AMI building process is error-free.
  
### Continuous Deployment (CD)

Our CD pipeline focuses on automating the deployment of the application as an Amazon Machine Image (AMI) to AWS:

- **AMI Build**: After successful CI, the CD pipeline is triggered. It uses Packer to create a custom AMI with the latest application version.

- **AMI Deployment**: The newly created AMI is deployed to AWS, making it available for auto-scaling instances.


## Technologies Used

- **Spring Boot**: Rapid application development.
- **Hibernate (ORM)**: Simplify database operations.
- **BCrypt**: Secure password hashing.
- **AWS SDK**: Integration with Amazon Web Services.
- **StatsD**: Custom metrics collection.
- **GitHub Actions**: Automated CI/CD pipeline.
- **Packer**: Creating custom AMIs.

## API Endpoints

### Health Check

- `/healthz`: Performs a health check, verifying the connection to the database.

### Assignment Endpoints

- `GET /v1/assignments`: Retrieve a list of all assignments.
- `GET /v1/assignments/:id`: Retrieve details of a specific assignment by ID.
- `POST /v1/assignments`: Create a new assignment with the following validations:
  - Name must be a non-empty string.
  - Points must be a number between 1 and 10.
  - Number of attempts must be a positive integer.
  - Deadline must be a valid date.
- `PUT /v1/assignments/:id`: Update an assignment (restricted to the owner) with similar validations as the creation.
- `DELETE /v1/assignments/:id`: Delete an assignment (restricted to the owner).

### Assignment Submission

- `POST /v1/assignments/:id/submission`: Submit an assignment with a submission URL and the following validations:
  - Submission URL must be a non-empty string.
  - Submission URL must be a valid URL ending with '.zip'.
  - Users cannot submit more times than the specified number of attempts.
  - Submissions after the deadline are not allowed.


## Prerequisites

Before you begin, ensure that you have the following prerequisites installed on your system:

1. **Java Development Kit (JDK)**:
   - Install Java JDK version 21 on your machine. You can download it from [Oracle](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html).

2. **MySQL Database**:
   - Install MySQL on your local machine. You can download it from [MySQL Downloads](https://dev.mysql.com/downloads/installer/).

3. **Spring Boot Project Setup**:
   - Set up a Spring Boot project either using Spring Initializer or manually. You can follow the Spring Boot documentation for project setup.

## Installation

Follow these steps to build and run your Spring Boot application:

1. **Clone the Application Source Code**:
   - Clone the source code for your Spring Boot application from your repository.

2. **Configure MySQL Connection**:
   - Open the `application.properties` file in your project and configure the MySQL database connection properties. Update the following properties with your local MySQL settings:
     - `spring.datasource.url`
     - `spring.datasource.username`
     - `spring.datasource.password`

3. **Build the Application**:
   - Open a terminal/command prompt and navigate to your project's root directory.

   - Build the application using Maven with the following command:
     ```shell
     mvn install -DskipTests
     ```
     This command will compile your code, run tests, and package your application into a JAR.

4. **Run the Application**:
   - Once the build is successful, you can run the JAR file using the following command:
     ```shell
     java -jar target/your-application-name.jar
     ```
     Replace `your-application-name` with the actual name of your JAR file.

5. **Access the Application**:
   - Your Spring Boot application should now be running. You can access it by opening a web browser and navigating to the application URL (e.g., `http://localhost:8080`).
