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
import com.br.soluctions.attos.quick_loc.services.Utils.RemoveJsonParameters;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private RemoveJsonParameters removeJsonParameters;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder, RemoveJsonParameters removeJsonParameters) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.removeJsonParameters = removeJsonParameters;

    }

    public User createNewUser(CreateUserDto createUserDto) {
        var userRole = roleRepository.findByRoleName(Role.Values.USER.name());
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
        user.setDocument(createUserDto.document() == null ? "-" : createUserDto.document());
        user.setPhoneNumber(createUserDto.phoneNumber() == null ? "-" : createUserDto.phoneNumber());
        user.setRoles(Set.of(userRole));

        return userRepository.save(user);
    }

    public boolean verifyEmail(String email) {

        email = removeJsonParameters.removeParameters(email, "email");

        var emailFromDb = userRepository.findByEmail(email);

        if (emailFromDb.isPresent()) {
            return true;
        } else {
            System.err.println(emailFromDb);
            throw new DataIntegrityViolationException("e-mail not exists in db");
        }
    }


    public List<User> listAllUsers() {
        List<User> users = new ArrayList<>();

        users = userRepository.findAll();

        return users;
    }
}
