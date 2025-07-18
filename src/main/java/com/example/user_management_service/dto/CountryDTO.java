package com.example.user_management_service.dto;

import com.example.user_management_service.util.DateTimeUtil;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "Abbreviation cannot be null")
    @Size(min = 1, max = 3, message = "Abbreviation must be between 1 and 3 characters")
    @Pattern(regexp = "[A-Z]{1,3}", message = "Abbreviation must be uppercase letters")
    private String abbreviation;

    @Pattern(regexp = DateTimeUtil.DATE_FORMAT_REGEX, message = "createdAt must match pattern: " + DateTimeUtil.DATE_FORMAT_PATTERN)
    private String createdAt;

    @Pattern(regexp = DateTimeUtil.DATE_FORMAT_REGEX, message = "updatedAt must match pattern: " + DateTimeUtil.DATE_FORMAT_PATTERN)
    private String updatedAt;

    private String icon;

    private Boolean isDeleted;


}
