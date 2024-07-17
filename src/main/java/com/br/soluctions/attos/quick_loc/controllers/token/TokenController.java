package com.br.soluctions.attos.quick_loc.controllers.token;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.soluctions.attos.quick_loc.controllers.dto.login.LoginRequest;
import com.br.soluctions.attos.quick_loc.controllers.dto.login.LoginResponse;
import com.br.soluctions.attos.quick_loc.repositories.user.UserRepository;
import com.br.soluctions.attos.quick_loc.services.Authentication.AuthenticationService;

@RestController
@RequestMapping("/api")
public class TokenController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenController(AuthenticationService authenticationService, UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var user = userRepository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("user or password is invalid");
        }

        var jwtValue = authenticationService.Authenticate(loginRequest);

        return ResponseEntity.ok(new LoginResponse(jwtValue));

    }

}
