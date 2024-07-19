package com.br.soluctions.attos.quick_loc.services.JWT;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.br.soluctions.attos.quick_loc.controllers.dto.login.LoginRequest;
import com.br.soluctions.attos.quick_loc.entities.roles.Role;
import com.br.soluctions.attos.quick_loc.repositories.user.UserRepository;

@Service
public class JwtService {

    private final JwtEncoder encoder;
    private final UserRepository userRepository;

    public JwtService(JwtEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public String generateToken(LoginRequest loginRequest) {
        var user = userRepository.findByUsername(loginRequest.username());
        Instant now = Instant.now();
        long expiresIn = 1800L;

        var scopes = user.get().getRoles() // pega a role do usuario
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("quick-loc")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .subject(user.get().getUserId().toString())
                .claim("scope", scopes)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    

}
