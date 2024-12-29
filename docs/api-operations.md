# CREATE USER

**Description**:  
Create a new user. It doesn't need to be logged in.

---

### REQUEST

**Method**:  
`POST`  

**Url**:  
`/api/user`

**Url Parameters**:  
No

**Body**:  
```json
  {
    "nickname": "example_nickname",
    "password": "12356789"
  }
```

**Requires to be logged**:  
No

---

### RESPONSE

**Status Code**:  
`201 CREATED`  

**Body**:  
No

**Cookies**:  
+ jwt

# GET USER

**Description**:  
Get a user by ID. It doesn't need to be logged in.

---

### REQUEST

**Method**:  
`GET`  

**Url**:  
`/api/user/{id}`

**Url Parameters**:  
+ **id**: id of the user to be retrived.

**Body**:  
No

**Requires to be logged**:  
No

---

### RESPONSE

**Status Code**:  
`200 OK`  

**Body**:  
```json
  {
    "id": 1,
    "nickname": "user1"
  }
```

**Cookies**:  
No

# GET ALL USERS

**Description**:  
Get all users. It doesn't need to be logged in.

---

### REQUEST

**Method**:  
`GET`  

**Url**:  
`/api/user`

**Url Parameters**:  
no

**Body**:  
No

**Requires to be logged**:  
No

---

### RESPONSE

**Status Code**:  
`200 OK`  

**Body**:  
```json
[
  {
    "id": 1,
    "nickname": "user1"
  },
  {
    "id": 2,
    "nickname": "user2"
  }
]
```

**Cookies**:  
No

# CREATE SURVEY

**Description**:  
Create a new survey. You must be loged in to create a survey.

---

### REQUEST

**Method**:  
`POST`  

**Url**:  
`/api/survey`

**Url Parameters**:  
No

**Body**:  
```json
{
  "title": "example title",
  "options": [
    "option 1",
    "option 2"
  ]
}
```

**Requires to be logged**: Yes

---

### RESPONSE

**Status Code**:  
`201 CREATED`  

**Body**:  
No  

**Cookies**:  
No

# GET SURVEY

**Description**:  
Get a survey by its ID. It does not require authentication, everyone can see every survey

---

### REQUEST

**Method**:  
`GET`  

**Url**:  
`/api/survey/{id}`

**Url Parameters**:  
+ **id**: id of the survey that is going to be retrived.

**Body**:  
No

**Requires Authentication**: No

---

### RESPONSE

**Status Code**:  
`200 OK`  

**Body**:  
```json
{
  "id": 1,
  "user": "jhon_doe",
  "title": "example title",
  "options": [
    {
      "name": "option 1",
      "votes": 25
    },
    {
      "name": "option 2",
      "votes": 30
    }
  ]
}
```

**Cookies**:  
No

# DELETE SURVEY

**Description**:  
Delete a survey by its id. Only the user that created the survey is authorized to delete it.

---

### REQUEST

**Method**:  
`DELETE`  

**Url**:  
`/api/survey/{id}`

**Url Parameters**:  
+ **id**: id of the survey that is going to be deleted.

**Body**:  
No

**Requires Authentication**: Yes

---

### RESPONSE

**Status Code**:  
`NO CONTENT`  

**Body**:  
No  

**Cookies**:  
No

# LOG IN

**Description**:  
Log in into the system.

---

### REQUEST

**Method**:  
`POST`  

**Url**:  
`/api/auth`

**Url Parameters**:  
No

**Body**:  
```json
{
  "nickname": "example_nickname",
  "password": "123456789"
}
```

**Requires Authentication**: No

---

### RESPONSE

**Status Code**:  
`200 OK`  

**Body**:  
No  

**Cookies**:  
+ jwt