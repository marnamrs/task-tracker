
# task-tracker

Task tracking project developed with Spring, Maven and MySql. 

[Dependencies](#tech-stack-and-dependencies) | [Run](#run-locally) | [Security](#security) | [Default Data](#default-data)


## Tech Stack and Dependencies

- Java (v. 17)
- Spring Boot (v3.0.2) | Maven
    - DevTools
    - Lombok (v.1.18.24)
    - Spring Web
    - Spring Security (v.6)
    - Spring Data JPA
    - MySQL JDBC Driver (v.8.0.32)
    - Jakarta Validation
    - Thymeleaf (v.3.1.1)
- Thymeleaf-extras-springsecurity (v.3.1.1)
- Frontend tools through CDN:
    - Bootstrap CSS (v.5.2.3)
    - Bootstrap JS (v.4.3.1)
    - Jquery (v.3.3.1)
    - Popper.js (v.1.14.7)

Enrironments used: 
- IntelliJ IDEA Community Ed. 2022.3.2
- MySQL Workbench Community v.8.0


## Run Locally

To setup the project locally, you will need to run a local instance of MySql server and clone the repo into your local. 

After setting up a local copy, build the Maven dependencies in the `pom.xml`. 

Configure `application.properties` file in `src/main/resources` with the following variables or setup the corresponding environment variables:

```
spring.datasource.url=jdbc:mysql//${DB_HOST}:${DB_PORT}/${DB_NAME}?serverTimezone=UTC
spring.datasource.username= ${DB_USER}
spring.datasource.password= ${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
server.error.include-stacktrace=never

spring.jpa.show-sql=true

```
| Variable | Description |
| :-------- | :------- | 
| `DB_HOST`      | localhost port | 
| `DB_PORT`      | dedicated mysql server port | 
| `DB_NAME`      |   mysql db/schema name| 
| `DB_USER`      | mysql server user | 
| `DB_PASSWORD`      | mysql server password | 

`ddl-auto=update` is set for development purposes.


## Security

For demo purposes, when running the project two default users will be generated if no users are present in the database. Usernames and passwords can be configured under the `TodotrackerApplication.java` class.

Access to `/updatetask` path and `update task` functionality require authentication through the open `/login` endpoint. 


## Default Data

For demo purposes, when running the project some default tasks will be generated if no tasks are present in the database. Default tasks can be edited under the `TodotrackerApplication.java` class.





