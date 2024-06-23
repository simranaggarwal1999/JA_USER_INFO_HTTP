# JA_USER_INFO_HTTP

## Description
This project is a Spring Boot application that exposes a web service with basic HTTP authentication.
It accepts a username as a query parameter and checks if the user exists in a properties file.
If the user exists, it posts the user details to another web service.
The service returns 200 Ok response for success(when user exist in property) and 404 error for user not found.

## Dependencies
- Java 17
- Maven
- Tomcat server (embedded)
- Springboot Security (Basic Authentication)
- Mockito and JUnit5 (Unit Testing)
- Lombok
- Jasypt(Password Encryption)

## Components
- Security Config : Handles Basic HTTP authentication
- User Request Controller : The controller controls the user Get API request hit on the endpoint (http://localhost:8080/appName/userDetail?user=john) to search for the user in the properties file and if the user is found, it sends the user details to User Request Handler service.
- User Request Handler : The service catches the user details and post them to another web service. The final response is returned.
- User Response Controller : The controller controls the user POST API request hit on the endpoint(http://localhost:8080/appName2/addUserInfo) and sends the response back to the service.
- User Detail : It is a POJO of user details object.

## Building the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/simranaggarwal1999/JA_USER_INFO_HTTP.git
   cd JA_USER_INFO_HTTP
2. To build the project, navigate to the project root directory and run:
   ```bash
   mvn clean install
3. To encrypt a normal text, jasypt online tool is used.
4. The secret key used for decrypting password into normal text is passed as runtime parameter (-Djasypt.encryptor.password=secretKey)
5. To generate war file, run:
   ```bash
   mvn package