package com.br.soluctions.attos.quick_loc.services.user;

import java.util.*;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.br.soluctions.attos.quick_loc.controllers.dto.users.CreateUserDto;
import com.br.soluctions.attos.quick_loc.entities.roles.Role;
import com.br.soluctions.attos.quick_loc.entities.users.User;
import com.br.soluctions.attos.quick_loc.repositories.role.RoleRepository;
import com.br.soluctions.attos.quick_loc.repositories.user.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createNewUser(CreateUserDto createUserDto) {
        var basicRole = roleRepository.findByRoleName(Role.Values.BASIC.name());
        var userFromDb = userRepository.findByUsername(createUserDto.username()); // verifica se não existe no db
        var emailFromDb = userRepository.findByEmail(createUserDto.email());

        if (userFromDb.isPresent() || emailFromDb.isPresent()) {
            throw new DataIntegrityViolationException("User already exists"); 
        }

        var user = new User();
        user.setFirstName(createUserDto.firstName());
        user.setLastName(createUserDto.lastName());
        user.setUsername(createUserDto.username());
        user.setPassword(bCryptPasswordEncoder.encode(createUserDto.password()));
        user.setEmail(createUserDto.email());
        user.setRoles(Set.of(basicRole));

        return userRepository.save(user);
    }

    public List<User> listAllUsers() {
        List<User> users = new ArrayList<>();

        users = userRepository.findAll();

        return users;
    }
}
