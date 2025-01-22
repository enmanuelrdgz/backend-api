package com.github.enma11235.surveysystemapi.controller;

import com.github.enma11235.surveysystemapi.dto.request.CreateUserRequestBody;
import com.github.enma11235.surveysystemapi.dto.request.LoginRequestBody;
import com.github.enma11235.surveysystemapi.dto.request.RegisterRequestBody;
import com.github.enma11235.surveysystemapi.dto.response.CreateUserResponseBody;
import com.github.enma11235.surveysystemapi.model.User;
import com.github.enma11235.surveysystemapi.service.AuthService;
import com.github.enma11235.surveysystemapi.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;


    @Autowired
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    // LOG IN
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginRequestBody body) {
        String token = authService.authenticate(body.getNickname(), body.getPassword());
        Long id = userService.getUserId(token);
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("token", token);
        response.put("id", id);
        // Devolver la respuesta con los headers
        return ResponseEntity.ok(response);
    }

    //REGISTER
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody @Valid CreateUserRequestBody body) {
        CreateUserResponseBody responseBody = userService.createUser(body.getNickname(), body.getPassword());
        Optional<User> user = userService.findUserByNickname(responseBody.getNickname());
        String token = authService.authenticate(user.get().getNickname(), user.get().getPassword());
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("token", token);
        // Devolver la respuesta con los headers
        return ResponseEntity.ok(response);
    }

}
