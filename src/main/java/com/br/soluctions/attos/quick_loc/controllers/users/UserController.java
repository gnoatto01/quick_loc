package com.br.soluctions.attos.quick_loc.controllers.users;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.br.soluctions.attos.quick_loc.controllers.dto.users.CreateUserDto;

import com.br.soluctions.attos.quick_loc.entities.roles.Role;
import com.br.soluctions.attos.quick_loc.entities.users.User;
import com.br.soluctions.attos.quick_loc.repositories.role.RoleRepository;
import com.br.soluctions.attos.quick_loc.repositories.user.UserRepository;

import jakarta.transaction.Transactional;

@RestController
public class UserController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto userDto) {

        var basicRole = roleRepository.findByRoleName(Role.Values.BASIC.name());

        var userFromDb = userRepository.findByUsername(userDto.username()); // verifica se já existe esse user

        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setUsername(userDto.username());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.password()));
        user.setEmail(userDto.email());
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> listUsers() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

}
