package com.br.soluctions.attos.quick_loc.repositories.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.soluctions.attos.quick_loc.entities.roles.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
