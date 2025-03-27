package com.github.enma11235.generic.poll.system.controller;

import com.github.enma11235.generic.poll.system.dto.request.AuthRequestBody;
import com.github.enma11235.generic.poll.system.model.User;

import com.github.enma11235.generic.poll.system.service.UserService;
import com.github.enma11235.generic.poll.system.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequestBody body) {
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(body.getNickname(), body.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationRequest);
            String token = jwtUtils.generateToken((User) authentication.getPrincipal());
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return new ResponseEntity<>("Login Successful", headers, HttpStatus.OK);
        } catch (AuthenticationException ex) {
            //arreglar esto
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/status")

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AuthRequestBody body) {
        userService.createUser(body.getNickname(), body.getPassword());
        return new ResponseEntity<>("Register Successful", HttpStatus.OK);
    }

    //Implementar el endpoint "logout"

    //implementar el endpoint "status"
}
