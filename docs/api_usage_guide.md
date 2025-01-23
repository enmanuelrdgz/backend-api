# OPERATION: CREATE USER

**Endpoint**:  
`POST /api/user`

**Description**:  
This endpoint allows creating a new user in the system. Upon receiving the required data, a user will be registered and information about the created resource will be returned.

## Requirements

**Headers**
+ `Content-Type: application/json`

**Body**:  
The body of the request must be sent in JSON format with the following fields:  

|field   |type  |required|description  |
|--------|------|--------|-------------|
|nickname|string|yes     |user nickname|
|password|string|yes     |user password|

**Example Request Body**
```json
{
  "nickname": "john_doe",
  "password": "securepassword123"
}
```

## Responses

**Success (201 Created)**  
When the user is successfully created, the server returns:  
+ Status Code: `201 Created`
+ Headers: 
  + `Location`: URI of the created resource (e.g., /api/user/123)
+ Body: User information in JSON format.  

**Example Response Body**  

```json
{
  "id": 123,
  "nickname": "john_doe",
  "createdAt": "2024-12-29T12:00:00Z"
}
```

**Errors**  
|Status Code              |Description                                                 |
|-------------------------|------------------------------------------------------------|
|400 Bad Request          |Required fields are missing or the request format is invalid|
|409 Conflict             |A user with the same email already exists                   |
|500 Internal Server Error|An unexpected error occurred on the server                  |

**Example Error Response Body (409 Conflict)**  
```json
{
  "error": "Email is already in use."
}
```  
  

# OPERATION: GET USER

**Endpoint**:  
`GET /api/user/{id}`  

**Description**:  
Retrieves detailed information about a specific user based on their unique identifier (`id`).

## Requirements

**Path parameters**:  
+ `id`: The unique identifier of the user to retrieve.  

**Headers**:  
+ `Authorization`: The endpoint requires an authorization token to ensure secure access.  
+ `Content-Type: application/json`


**Example Request**
```
GET /api/user/123 HTTP/1.1
Authorization: Bearer {token}
```

## Responses

**Success (200 OK)**  
Returns the details of the user in JSON format.  

**Example Response Body**  
```json
{
  "id": 123,
  "nickname": "john_doe",
  "createdAt": "2024-12-29T12:00:00Z",
}
```

**Errors**  
|Status Code              |Description                                                 |
|-------------------------|------------------------------------------------------------|
|400 Bad Request          |Returned if the id is not a valid format|
|404 Not Found             |Returned if a user with the specified id does not exist                   |

# OPERATION: CREATE SURVEY

**Endpoint**:  
`POST /api/poll`

**Description**:  
This endpoint allows authenticated users to create a new poll.

## Requirements

**Headers**
+ `Content-Type: application/json`  
+ `Authorization`: The endpoint requires an authorization token to ensure the user is logged in.

**Body**:  
The body of the request must be sent in JSON format with the following fields:  

|field   |type  |required|description  |
|--------|------|--------|-------------|
|title|string|yes     |poll title|
|options|array|yes     |an array of options for the poll|
|options[].name|string|yes     |the name of a poll option|

**Example Request Body**
```json
  {
  "title": "Favorite Programming Language",
  "options": [
    { "name": "Python" },
    { "name": "JavaScript" },
    { "name": "C++" }
  ]
}
```

## Responses

**Success (201 Created)**  
When the poll is successfully created, the server returns:  
+ Status Code: `201 Created`
+ Headers: 
  + `Location`: URI of the created resource (e.g., /api/poll/123)
+ Body: Survey information in JSON format.  

**Example Response Body**  

```json
{
  "id": 123,
  "title": "Favorite Programming Language",
  "creator": {
    "id": 1,
    "nickname": "john_doe"
  },
  "options": [
    { "id": 1, "name": "Python", "votes": 0 },
    { "id": 2, "name": "JavaScript", "votes": 0 },
    { "id": 3, "name": "C++", "votes": 0 }
  ],
  "created_at": "2024-12-29T12:00:00Z"
}
```

**Errors**  
|Status Code              |Description                                                 |
|-------------------------|------------------------------------------------------------|
|400 Bad Request          |Required fields are missing or the request format is invalid|
|401 Unathorized             |Authentication token is missing or invalid.                   |
|500 Internal Server Error|An unexpected error occurred on the server                  |

# OPERATION: GET SURVEY

**Endpoint**:  
`GET /api/poll/{id}`  

**Description**:  
Retrieves detailed information about a specific poll based on their unique identifier (`id`).

## Requirements

**Path parameters**:  
+ `id`: The unique identifier of the poll to retrieve.  

**Headers**:  
+ `Authorization`: The endpoint requires an authorization token to ensure secure access.  
+ `Content-Type: application/json`


**Example Request**
```
GET /api/poll/123 HTTP/1.1
Authorization: Bearer {token}
```

## Responses

**Success (200 OK)**  
Returns the details of the poll in JSON format.  

**Example Response Body**  
```json
{
  "id": 123,
  "title": "Favorite Programming Language",
  "creator": {
    "id": 1,
    "nickname": "john_doe"
  },
  "options": [
    { "id": 1, "name": "Python", "votes": 5 },
    { "id": 2, "name": "JavaScript", "votes": 3 }
  ],
  "created_at": "2024-12-29T12:00:00Z"
}

```

**Errors**  
|Status Code              |Description                                                 |
|-------------------------|------------------------------------------------------------|
|400 Bad Request          |Returned if the id is not a valid format|
|404 Not Found             |Returned if a poll with the specified id does not exist                   |

# OPERATION: LOG IN

**Endpoint**:  
`POST /api/auth`

**Description**:  
This endpoint allows users to log in by providing their nickname and password. Upon successful authentication, the server returns an access token that can be used for subsequent API requests requiring authentication.

## Requirements

**Headers**
+ `Content-Type: application/json`

**Body**:  
The body of the request must be sent in JSON format with the following fields:  

|field   |type  |required|description  |
|--------|------|--------|-------------|
|nickname|string|yes     |user nickname|
|password|string|yes     |user password|

**Example Request Body**
```json
{
  "nickname": "john_doe",
  "password": "securepassword123"
}
```

## Responses

**Success (200 OK)**  
The user was successfully authenticated.
+ Status Code: `200 OK`
+ Headers: 
  + `Set-Cookie`: A cookie containing the JWT
  
**JWT Cookie**:  
+ Name: `auth_token`  
+ Path: `/`  
+ HttpOnly: `true`  
+ SameSite: `Strict`  

**Errors**  
|Status Code              |Description                                                 |
|-------------------------|------------------------------------------------------------|
|401 Unauthorized          |Invalid nickname or password.|
|400 Bad Request           |Missing or Invalid Input                   |
|500 Internal Server Error|An unexpected error occurred on the server                  |
 
