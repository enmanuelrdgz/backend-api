package com.github.enma11235.generic.poll.system.controller;

import com.github.enma11235.generic.poll.system.dto.request.AuthRequestBody;
import com.github.enma11235.generic.poll.system.dto.response.AuthResponseBody;
import com.github.enma11235.generic.poll.system.service.AuthService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // SIGN IN
    @PostMapping("/signin")
    public ResponseEntity<AuthResponseBody> signIn(@RequestBody @Valid AuthRequestBody body) {
        String token = authService.signIn(body.getNickname(), body.getPassword());
        AuthResponseBody responseBody = new AuthResponseBody("Signed in successfully",  token);
        return ResponseEntity.ok(responseBody);
    }

    //SIGN UP
    @PostMapping("/signup")
    public ResponseEntity<AuthResponseBody> signUp(@RequestBody @Valid AuthRequestBody body) {
        String token = authService.signUp(body.getNickname(), body.getPassword());
        AuthResponseBody responseBody = new AuthResponseBody("Signed up successfully",  token);
        return ResponseEntity.ok(responseBody);
    }

}
