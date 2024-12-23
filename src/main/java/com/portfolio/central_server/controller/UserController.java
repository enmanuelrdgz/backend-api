package com.portfolio.central_server.controller;

import com.portfolio.central_server.DTO.UserDTO;
import com.portfolio.central_server.model.Survey;
import com.portfolio.central_server.model.User;
import com.portfolio.central_server.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

import static javax.crypto.Cipher.SECRET_KEY;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private SecretKey claveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    // Endpoint to get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if(user.isPresent()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setNickname(user.get().getNickname());
            userDTO.setId(user.get().getId());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint to create a new user
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        String jwt = Jwts.builder().setSubject(savedUser.getNickname()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 3600000)).signWith(SignatureAlgorithm.HS512, claveSecreta).compact();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "jwt=" + jwt + "; HttpOnly; Path=/; Max-Age=604800");

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
    }

    // Endpoint to delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        //chequear que se ha enviado el jwt
        if(authorizationHeader != null) {
            // Extraer el token JWT (eliminar el prefijo "Bearer ")
            String jwt = authorizationHeader.substring(7); // El prefijo "Bearer " tiene 7 caracteres
            // Verifica y extrae las claims del JWT usando la private key
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(claveSecreta)  // Aquí se pasa la clave secreta en bytes
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            String nickname = claims.getSubject();

            Optional<User> user = userService.findUserById(id);
            if (user.isPresent()) {
                if (user.get().getNickname().equals(nickname)) {
                    userService.deleteUserById(id);
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}