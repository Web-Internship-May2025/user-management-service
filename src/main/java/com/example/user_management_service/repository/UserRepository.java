package com.example.user_management_service.repository;

import com.example.user_management_service.dto.UserDTO;
import com.example.user_management_service.model.User;
import com.example.user_management_service.model.enums.UserRoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User getUserByUsername(String username);

    Page<User> findAllByUserRole(UserRoleType role, Pageable pageable);
}
