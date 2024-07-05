package com.br.soluctions.attos.quick_loc.controllers.dto.users;

import java.util.Set;

import com.br.soluctions.attos.quick_loc.entities.roles.Role;

public record CreateUserDto(String username, String password, String email, Set<Role> roles) {

}
