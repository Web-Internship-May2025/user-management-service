package com.example.user_management_service.model;
import com.example.user_management_service.model.enums.UserRoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorColumn(name = "user_role", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
public class User  extends Person {

    @NotNull
    @Column(name= "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name="username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(name="password", nullable = false)
    private String password;

    // The 'isEnabled' field indicates whether the user has completed the verification process successfully.
    // By default, this field is set to 'false' upon registration to restrict access until verification,
    // and is updated to 'true' once the user's account is verified, enabling full access to the system features.
    @NotNull
    @Column(name="is_enabled", nullable = false, columnDefinition = "boolean default false")
    private Boolean isEnabled = false;

    // The 'isActive' field represents the current status of the user's account as managed by administrators or system managers.
    // Setting this field to 'false' effectively suspends or bans the user, preventing access to the system,
    // while setting it to 'true' restores their access and privileges.
    @NotNull
    @Column(name = "is_active",nullable = false, columnDefinition = "boolean default false")
    private Boolean isActive = false;

    @Column
    private String icon;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role",nullable = false)
    private UserRoleType userRole;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
