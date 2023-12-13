package com.example.testtask.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.testtask.util.DataValid.MAX_PASSWORD;
import static com.example.testtask.util.DataValid.MESSAGE_NOT_BLANK;
import static com.example.testtask.util.DataValid.MIN_PASSWORD;
import static com.example.testtask.util.DataValid.PASSWORD_LENGTH;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = MESSAGE_NOT_BLANK)
    @Email
    private String email;

    @NotBlank(message = MESSAGE_NOT_BLANK)
    @Size(min = MIN_PASSWORD, max = MAX_PASSWORD,
            message = PASSWORD_LENGTH)
    private String password;
}
