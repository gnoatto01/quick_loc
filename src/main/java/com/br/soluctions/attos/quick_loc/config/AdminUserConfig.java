package com.br.soluctions.attos.quick_loc.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.br.soluctions.attos.quick_loc.entities.users.User;
import com.br.soluctions.attos.quick_loc.repositories.role.RoleRepository;
import com.br.soluctions.attos.quick_loc.repositories.user.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository
                .findByRoleName(com.br.soluctions.attos.quick_loc.entities.roles.Role.Values.ADMIN.name());

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin exists");
                },
                () -> {
                    var user = new User();
                    user.setFirstName("Quick");
                    user.setLastName("Loc");
                    user.setUsername("admin");
                    user.setPassword(bCryptPasswordEncoder.encode("QuickL@c!@#"));
                    user.setEmail("contatognoatto01@gmail.com");
                    user.setRoles(Set.of(roleAdmin));
                    userRepository.save(user);
                });
    }

}
