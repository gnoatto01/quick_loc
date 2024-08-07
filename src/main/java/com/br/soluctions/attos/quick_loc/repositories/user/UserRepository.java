package com.br.soluctions.attos.quick_loc.repositories.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.soluctions.attos.quick_loc.entities.users.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE tb_users u SET u.first_name = :firstName, u.last_name = :lastName, u.username = :username, u.email = :email, u.document = :document, u.phone_number = :phoneNumber WHERE u.user_id = :userId", nativeQuery = true)
    User updateUser(
        @Param("firstName") String firstName,
        @Param("lastName") String lastName,
        @Param("username") String username,
        @Param("email") String email,
        @Param("document") String document,
        @Param("phoneNumber") String phoneNumber,
        @Param("userId") UUID userId
    );

}
