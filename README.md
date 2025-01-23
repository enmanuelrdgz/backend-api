# Generic Poll System

This Spring Boot application serves as the core of a Generic Poll System, housing its business logic and functioning as an API to enable interaction from the client.

## Technologies Used

- **Java**: The core language used for implementing the API.
- **Spring Boot**: Framework used to build the RESTful API.
- **Hibernate**: Used for database operations.
- **JWT (JSON Web Token)**: For authentication and authorization.
- **PostgreSQL**: Relational database used for storing user and poll data.

## Endpoints

### Survey Endpoints

- `POST api/poll` - Create a new poll.
- `GET api/poll` - Retrieve a list of all polls.
- `GET api/poll/{id}` - Get details of a specific poll by ID.
- `PUT api/poll/{id}` - Update a poll by ID.
- `DELETE api/poll/{id}` - Delete a poll by ID.

### User Endpoints

- `POST api/user` - Register a new user into the system.
- `GET api/user` - Retrieve all a list of all users.
- `GET api/user/{id}` - Get details of a specific user by ID.
- `PUT api/user/{id}` - Update a user by ID.
- `DELETE api/user/{id}` - Delete a user by ID.

### Authentication

- `POST /auth/login` - Log in and receive a JWT token for authenticated access.

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/poll-api.git
