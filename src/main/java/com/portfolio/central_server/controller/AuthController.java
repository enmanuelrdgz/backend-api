package com.portfolio.central_server.controller;

import com.portfolio.central_server.DTO.LoginRequestDTO;
import com.portfolio.central_server.model.User;
import com.portfolio.central_server.service.*;
import io.jsonwebtoken.Claims;
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
    private final SecretKey secretKey;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // Endpoint to log a user into the system
    @GetMapping
    public ResponseEntity<Void> login(@RequestBody LoginRequestDTO body) {
        Optional<User> user = userService.findUserByNickname(body.getNickname());
        if (user.isPresent()) {
            if (user.get().getPassword().equals(body.getPassword())) {
                String token = Jwts.builder().setSubject(user.get().getNickname()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 3600000)).signWith(SignatureAlgorithm.HS512, secretKey).compact();
                HttpHeaders headers = new HttpHeaders();
                headers.add("Set-Cookie", "jwt=" + token + "; HttpOnly; Path=/; Max-Age=604800");
                return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public boolean isAuthenticated(User user, String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String nickname = claims.getSubject();
        if (user.getNickname().equals(nickname)) {
            return true;
        } else {
            return false;
        }
    }

    public String authenticate(User user) {
        return Jwts.builder().setSubject(user.getNickname()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 3600000)).signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }
}
