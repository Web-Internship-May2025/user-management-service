package com.example.user_management_service.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="home_phone", unique = true)
    private String homePhone;

    @Column(name="mobile_phone", unique = true)
    private String mobilePhone;

    @Column(name="email", unique = true)
    private String email;

    @NotNull
    @Column(name="is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted = false;



}
