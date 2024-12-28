# API Operations

## CREATE USER

**METHOD** : POST <br>
**URL** : /api/survey <br>
**EXAMPLE BODY** : <br>
```
{
    "nickname": "enna11235"
    "password": "1123581321"
}
```
**EXAMPLE RESPONSES**: <br>
**If nickname is not available**: <br>
+ **STATUS CODE**: 409 CONFLICT <br>

**If nickname is available**: <br>
+ **STATUS CODE**: 201 CREATED <br>
+ **HEADERS**: <br>
    + **Set-Cookie**: jwt=***token***; HttpOnly; Path=/; Max-Age=604800 <br>

## GET ALL USERS

