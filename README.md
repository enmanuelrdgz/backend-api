# Survey Tool 

A simple REST API for creating surveys. This project was developed as a hobby using Spring Boot and PostgreSQL as the database.

## Technologies Used
- **Spring Boot** - Backend framework
- **PostgreSQL** - Database
- **Hibernate** - ORM for database interactions
- **Spring Web** - For handling RESTful endpoints

## Installation 

### Prerequisites
- Java 17+
- PostgreSQL installed and running
- Maven installed

### Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/simple-survey-api.git
   cd simple-survey-api
   ```

2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/survey_db
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints
### Survey Management

- `POST /surveys/create` - Create a new survey
```json
{
  "title": "¿Cuál es tu lenguaje de programación favorito?",
  "options": [
    { "description": "Java" },
    { "description": "Python" }
  ]
}
```

### Voting
- `POST /votes/create` - Submit a vote
```json
{
  "survey_id": 15,
  "option_id": 60
}
```

## Future Improvements
- User authentication and authorization
- Web interface for survey management
- Export survey results to CSV

---
Feel free to contribute or suggest new features! 🤝💡🎯

