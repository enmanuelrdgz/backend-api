package com.github.enma11235.surveysystemapi.controller;

import com.github.enma11235.surveysystemapi.dto.request.LoginRequestBody;
import com.github.enma11235.surveysystemapi.service.AuthService;
import com.github.enma11235.surveysystemapi.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    @PostMapping
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequestBody body) {
        String token = authService.authenticate(body.getNickname(), body.getPassword());
        String cookie = "tokkken=" + token + "; HttpOnly; Secure; Path=/; SameSite=Strict";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie);

        // Devolver la respuesta con los headers
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

}
