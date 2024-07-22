package com.br.soluctions.attos.quick_loc.controllers.users;

import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.soluctions.attos.quick_loc.controllers.dto.users.CreateUserDto;

import com.br.soluctions.attos.quick_loc.entities.users.User;
import com.br.soluctions.attos.quick_loc.services.user.UserService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin("http://localhost:3000")
    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto userDto) {

        userService.createNewUser(userDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.listAllUsers();
        return ResponseEntity.ok(users);
    }

}
