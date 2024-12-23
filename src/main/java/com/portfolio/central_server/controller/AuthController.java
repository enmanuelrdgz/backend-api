package com.portfolio.central_server.controller;

import com.portfolio.central_server.DTO.LoginRequestDTO;
import com.portfolio.central_server.model.User;
import com.portfolio.central_server.service.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final SecretKey jwtSecretKey;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
        this.jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // Endpoint to log a user into the system
    @GetMapping
    public ResponseEntity<Void> login(@RequestBody LoginRequestDTO body) {
        String token =authenticateUser(body);
        if(token != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", "jwt=" + token + "; HttpOnly; Path=/; Max-Age=604800");
            return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String authenticateUser(LoginRequestDTO loginRequest) {
        Optional<User> user = userService.findUserByNickname(loginRequest.getNickname());
        if(user.isPresent()) {
            if (user.get().getPassword().equals(loginRequest.getPassword())) {
                String secretKey = "";
                String token = Jwts.builder().setSubject(user.get().getNickname()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 3600000)).signWith(SignatureAlgorithm.HS512, secretKey).compact();
                return token;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
