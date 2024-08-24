package com.br.soluctions.attos.quick_loc.controllers.dto;

import java.util.Set;

import com.br.soluctions.attos.quick_loc.entities.Role;

public record CreateUserDto(String firstName, String lastName, String username, String password, String email,
                Set<Role> roles, String document, String phoneNumber) {

}
