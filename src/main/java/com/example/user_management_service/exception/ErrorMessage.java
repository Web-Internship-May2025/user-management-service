package com.example.user_management_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private String errorMessage;
    private int code;
    private HttpStatus httpStatus;
}
