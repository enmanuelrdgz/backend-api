package com.github.enma11235.surveysystemapi.controller;

import com.github.enma11235.surveysystemapi.dto.model.UserDTO;
import com.github.enma11235.surveysystemapi.dto.request.CreateUserRequestBody;
import com.github.enma11235.surveysystemapi.dto.response.GetUserByIdResponseBody;
import com.github.enma11235.surveysystemapi.dto.response.CreateUserResponseBody;
import com.github.enma11235.surveysystemapi.model.User;
import com.github.enma11235.surveysystemapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private AuthController authController;

    @Autowired
    public UserController(UserService userService, AuthController authController) {
        this.userService = userService;
        this.authController = authController;
    }

    // Endpoint to get all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserDTO> usersListDTO = new ArrayList<UserDTO>();
        for(User u : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(u.getId());
            userDTO.setNickname(u.getNickname());
            usersListDTO.add(userDTO);
        }
        return ResponseEntity.ok(usersListDTO);
    }

    // GET USER
    @GetMapping("/{id}")
    public ResponseEntity<GetUserByIdResponseBody> getUserById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        //obtenemos el token
        String token = authorizationHeader.substring(7);
        UserDTO user = userService.getUserById(id, token);
        GetUserByIdResponseBody responseBody = new GetUserByIdResponseBody(user.getId(), user.getNickname(), user.getCreatedAt());
        return ResponseEntity.ok(responseBody);
    }

    // CREATE USER
    @PostMapping
    public ResponseEntity<CreateUserResponseBody> createUser(@RequestBody @Valid CreateUserRequestBody body) {
        CreateUserResponseBody response = userService.createUser(body.getNickname(), body.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint to delete a user by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
//        if(authorizationHeader != null) {
//            // Extraer el token
//            String token = authorizationHeader.substring(7);
//            Optional<User> user = userService.findUserById(id);
//            if (user.isPresent()) {
//                try {
//                    String nickname = authController.getNicknameFromToken(token);
//                    if(user.get().getNickname().equals(nickname)) {
//                        userService.deleteUserById(id);
//                        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//                    } else {
//                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//                    }
//                } catch (Exception e) {
//                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//                }
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
}