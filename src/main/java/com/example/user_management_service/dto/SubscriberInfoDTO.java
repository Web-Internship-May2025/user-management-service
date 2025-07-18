package com.example.user_management_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriberInfoDTO {
    private Long id;
    private String firstName;
    private String lastName;
}
