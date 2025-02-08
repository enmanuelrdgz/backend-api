package com.github.enma11235.generic.poll.system.controller;

import com.github.enma11235.generic.poll.system.dto.model.UserData;
import com.github.enma11235.generic.poll.system.dto.request.EditUserRequestBody;
import com.github.enma11235.generic.poll.system.dto.request.GetUserRequestBody;
import com.github.enma11235.generic.poll.system.dto.response.EditUserResponseBody;
import com.github.enma11235.generic.poll.system.model.User;
import com.github.enma11235.generic.poll.system.service.AuthService;
import com.github.enma11235.generic.poll.system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private AuthController authController;
    private AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthController authController, AuthService authService) {
        this.userService = userService;
        this.authController = authController;
        this.authService = authService;
    }

    //EDIT USER
    @PostMapping("/edit")
    public ResponseEntity<EditUserResponseBody> editUser(@RequestBody @Valid EditUserRequestBody body) {
        User editedUser = userService.editUser(body.getNickname(), body.getPassword(), body.getImage(), body.getToken());
        String newToken = authService.signIn(editedUser.getNickname(), editedUser.getPassword());
        EditUserResponseBody responseBody = new EditUserResponseBody(newToken);
        return ResponseEntity.ok(responseBody);
    }
}

