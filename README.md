# quickpolls-core (2.0.0)
This is a REST API built with **Java** and **Spring Boot**. It integrates with a local PostgreSQL database and provides essential functionality to the [web client](https://github.com/enmanuelrdgz/quickpolls-client.git). 

## Technologies Used

- **Java** 21
- **Spring Boot** (3.4.0)
- **Maven** (3.8.7)

## Prerequisites

Before running the application, ensure you have the following installed:

- **JDK** 21
- **Maven** (v3.8.7)
- **PostgreSQL** (v16.6)

> **About postgreSQL:**  
> * It must be running locally on port 5432  
> * There must be a user called **quickpolls** with password **quickpolls**
> * There must be a database called **quickpolls**, owned by the user **quickpolls**.

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/enmanuelrdgz/quickpolls-core.git
   cd quickpolls-core
   ```

2. Install dependencies:
   ```bash
   mvn install
   ```

3. Compile and package the application:
   ```bash
   mvn clean package
   ```

4. Run the application:
   ```bash
   java -jar ./target/quickpolls-2.0.0.jar
   ```
> Now the application should be running **locally** on port **8080**
