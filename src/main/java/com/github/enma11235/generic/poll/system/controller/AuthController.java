package com.github.enma11235.generic.poll.system.controller;

import com.github.enma11235.generic.poll.system.dto.request.CreateUserRequestBody;
import com.github.enma11235.generic.poll.system.dto.request.LoginRequestBody;
import com.github.enma11235.generic.poll.system.dto.response.AuthResponseBody;
import com.github.enma11235.generic.poll.system.dto.response.CreateUserResponseBody;
import com.github.enma11235.generic.poll.system.dto.response.LoginResponseBody;
import com.github.enma11235.generic.poll.system.service.AuthService;
import com.github.enma11235.generic.poll.system.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;


    @Autowired
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    // SIGN IN
    @PostMapping("/signin")
    public ResponseEntity<AuthResponseBody> login(@RequestBody @Valid LoginRequestBody body) {
        String token = authService.authenticate(body.getNickname(), body.getPassword());
        AuthResponseBody responseBody = new AuthResponseBody("Logged in successfully",  token);
        return ResponseEntity.ok(responseBody);
    }

    //SIGN UP
    @PostMapping("/signup")
    public ResponseEntity<AuthResponseBody> signup(@RequestBody @Valid CreateUserRequestBody body) {
        String token = authService.signup(body.getNickname(), body.getPassword());
        AuthResponseBody responseBody = new AuthResponseBody("Signed up successfully",  token);
        return ResponseEntity.ok(responseBody);
    }

}
