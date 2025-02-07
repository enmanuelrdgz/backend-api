# quickpolls-core (2.0.0)
This is the backend of QuickPolls. It contains the business logic and serves as an API to enable interaction from the client.

## Technologies Used

- **Java**: The core language used for implementing the API.
- **Spring Boot**: Framework used to build the RESTful API.
- **Hibernate**: Used for database operations.
- **JWT (JSON Web Token)**: For authentication and authorization.
- **PostgreSQL**: Relational database used for storing user and survey data.

## Prerequisites

Before running the application, ensure you have the following installed:

- **JDK** 21
- **Maven** (v3.8.7)
- **Postgres** (v16.6)

> ⚠️ **Important** ⚠️  
> There must be a database called **quickpolls**  
> User: quickpolls  
> Password: quickpolls
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
> Now the application must be running and listening in port 8080

