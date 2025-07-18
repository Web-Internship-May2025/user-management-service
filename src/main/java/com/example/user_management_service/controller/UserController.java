package com.example.user_management_service.controller;

import com.example.user_management_service.aspect.Monitored;
import com.example.user_management_service.dto.UserDTO;
import com.example.user_management_service.service.CountryService;
import com.example.user_management_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import com.example.user_management_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('SALES_AGENT')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO userDTO = userService.findById(id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'SALES_AGENT')")
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        UserDTO userDTO = userService.findByUsername(username);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

}
