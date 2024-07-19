package com.br.soluctions.attos.quick_loc.services.Authentication;

import org.springframework.stereotype.Service;

import com.br.soluctions.attos.quick_loc.controllers.dto.login.LoginRequest;
import com.br.soluctions.attos.quick_loc.services.JWT.JwtService;

@Service
public class AuthenticationService {
    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String Authenticate(LoginRequest loginRequest) {
        return jwtService.generateToken(loginRequest);
    }

    public String removeJsonParameters(String accessToken) {

        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        accessToken = accessToken.replaceAll("[{}]", "");
        accessToken = accessToken.replaceAll("accessToken", "");
        accessToken = accessToken.replaceAll("\"", "");
        accessToken = accessToken.replaceAll(":", "");

        return accessToken;
    }

    public boolean ValidateToken(String accessToken) {
        return jwtService.validateToken(accessToken);
    }

}
