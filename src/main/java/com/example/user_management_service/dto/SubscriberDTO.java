package com.example.user_management_service.dto;

import com.example.user_management_service.model.enums.GenderType;
import com.example.user_management_service.model.enums.MaritalStatusType;
import com.example.user_management_service.model.enums.UserRoleType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberDTO {

    @NotNull(message = "firstName ne sme biti null")
    private String firstName;

    @NotNull(message = "lastName ne sme biti null")
    private String lastName;

    @NotNull(message = "jmbg ne sme biti null")
    private String jmbg;

    private LocalDateTime birthDate;
    private GenderType gender;
    private MaritalStatusType maritalStatus;

    @NotNull(message = "email ne sme biti null")
    private String email;

    @NotNull(message = "username ne sme biti null")
    private String username;

    @NotNull(message = "password ne sme biti null")
    private String password;

    @NotNull(message = "userRoleType ne sme biti null")
    private UserRoleType userRoleType;

    @NotNull(message = "subscriberRoleId ne sme biti null")
    private Long subscriberRoleId;

//    TODO: add address
}