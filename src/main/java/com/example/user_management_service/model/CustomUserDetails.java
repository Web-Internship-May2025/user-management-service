package com.example.user_management_service.model;

import com.example.user_management_service.model.enums.UserRoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        UserRoleType roleType = user.getUserRole();

        if (roleType != null) {
            String roleName = "ROLE_" + roleType.name();
            return Collections.singletonList(new SimpleGrantedAuthority(roleName));
        }
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnabled() != null && user.getIsEnabled();
    }

    public User getUser() {
        return user;
    }
}