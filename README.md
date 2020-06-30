# car-charging-session
The core function of the module is to implement RESTful Java based backend application to create a store for car charging entities.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a system.


### Built With:
1. Framework - SpringBoot
2. Dependency Management - Maven
3. Version Control - Git
4. Building images and running containers - Docker
5. Event Monitoring - Prometheus

### Installing

Clone the git repository in a local directory using git clone<repository_name> command.


### Deployment on Local Machine
1. Build the jar file using the command: mvn clean install.
2.
3. Submit a new charging session for the station:: Request Type: POST || URL: http://localhost:9000/chargingSessions
4. Stop charging session:: Request Type: PUT || URL: http://localhost:9000/chargingSessions/{id}
5. Retrieve all charging sessions:: Request Type: GET || URL: http://localhost:9000/chargingSessions
6. Swagger UI URL: http://localhost:9000/swagger-ui.html
7. Prometheus URL: http://localhost:9000/actuator/prometheus 
