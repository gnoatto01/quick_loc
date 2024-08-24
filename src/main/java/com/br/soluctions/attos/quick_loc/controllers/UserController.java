package com.br.soluctions.attos.quick_loc.controllers;

import java.io.IOException;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.soluctions.attos.quick_loc.controllers.dto.CreateUserDto;
import com.br.soluctions.attos.quick_loc.entities.User;
import com.br.soluctions.attos.quick_loc.services.UserService;
import com.br.soluctions.attos.quick_loc.services.Utils.RemoveJsonParameters;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private RemoveJsonParameters removeJsonParameters;

    public UserController(UserService userService, RemoveJsonParameters removeJsonParameters) {
        this.userService = userService;
        this.removeJsonParameters = removeJsonParameters;
    }

    @CrossOrigin("http://localhost:3000")
    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto userDto) {

        userService.createNewUser(userDto);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin("http://localhost:3000")
    @Transactional
    @PutMapping("/users")
    public ResponseEntity<Void> updateUser(@RequestBody CreateUserDto userDto,
            @RequestHeader("Authorization") String accessToken)
            throws Exception {

        accessToken = removeJsonParameters.removeParameters(accessToken, "accessToken");

        userService.updateUser(userDto, accessToken);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin("http://localhost:3000")
    @Transactional
    @PostMapping("/users/change-avatar")
    public ResponseEntity<Void> updateAvatarUser(@RequestHeader("Authorization") String accessToken,
            @RequestParam("avatar") MultipartFile avatar) throws IOException {

        accessToken = removeJsonParameters.removeParameters(accessToken, "accessToken");

        if (avatar.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            userService.updateUserAvatar(accessToken, avatar);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new IOException("error in edit avatar image");
        }

    }

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/users/verify-email")
    public ResponseEntity<Void> verifyUserEmail(@RequestBody String email) {
        userService.verifyEmail(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.listAllUsers();
        return ResponseEntity.ok(users);
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping("/users/get-user")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String accessToken) {

        accessToken = removeJsonParameters.removeParameters(accessToken, "accessToken");

        var user = userService.getUserByTokenId(accessToken);

        return ResponseEntity.ok(user);
    }

}
