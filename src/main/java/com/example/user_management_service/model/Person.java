package com.example.user_management_service.model;
import com.example.user_management_service.model.enums.GenderType;
import com.example.user_management_service.model.enums.MaritalStatusType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name="last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name="jmbg", nullable = false, unique = true)
    private String jmbg;

    @Column(name="birth_date")
    private LocalDateTime birthDate;

    @Column(name="image")
    private String image;

    @NotNull
    @Column(name="is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="gender", nullable = false)
    private GenderType gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="marital_status", nullable = false)
    private MaritalStatusType maritalStatus;

    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

}
