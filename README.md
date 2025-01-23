# Generic Poll System
This Spring Boot application serves as the core of a Generic Poll System, housing its business logic and functioning as an API to enable interaction from the client.

## Technologies Used

- **Java**: The core language used for implementing the API.
- **Spring Boot**: Framework used to build the RESTful API.
- **Hibernate**: Used for database operations.
- **JWT (JSON Web Token)**: For authentication and authorization.
- **PostgreSQL**: Relational database used for storing user and survey data.

## Endpoints

### Survey Endpoints

- `POST api/survey` - Create a new survey.
- `GET api/survey` - Retrieve a list of all surveys.
- `GET api/survey/{id}` - Get details of a specific survey by ID.
- `DELETE api/survey/{id}` - Delete a survey by ID.

### User Endpoints

- `POST api/user` - Register a new user into the system.
- `GET api/user` - Retrieve a list of all users.
- `GET api/user/{id}` - Get details of a specific user by ID.
- `DELETE api/user/{id}` - Delete a user by ID.

### Authentication

- `POST /auth/` - Log in and receive a JWT token for authenticated access.

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/survey-api.git
