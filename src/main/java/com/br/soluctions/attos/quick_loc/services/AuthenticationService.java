package com.br.soluctions.attos.quick_loc.services;

import org.springframework.stereotype.Service;

import com.br.soluctions.attos.quick_loc.controllers.dto.LoginRequest;

@Service
public class AuthenticationService {
    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String Authenticate(LoginRequest loginRequest) {
        return jwtService.generateToken(loginRequest);
    }


    public boolean ValidateToken(String accessToken) {
        return jwtService.validateToken(accessToken);
    }

}
