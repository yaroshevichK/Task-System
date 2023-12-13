package com.example.testtask.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.testtask.util.DataOpenApi.CODE_CREATE;
import static com.example.testtask.util.DataOpenApi.CODE_NOT_AUTH;
import static com.example.testtask.util.DataOpenApi.CODE_NOT_VALID;
import static com.example.testtask.util.DataOpenApi.CODE_OK;
import static com.example.testtask.util.DataOpenApi.CODE_UNIQ;
import static com.example.testtask.util.DataOpenApi.MESSAGE_CREATE_USER;
import static com.example.testtask.util.DataOpenApi.MESSAGE_EMAIL_NOT_UNIQ;
import static com.example.testtask.util.DataOpenApi.MESSAGE_NOT_VALID;
import static com.example.testtask.util.DataOpenApi.MESSAGE_USER_AUTH;
import static com.example.testtask.util.DataOpenApi.MESSAGE_USER_NOT_AUTH;
import static com.example.testtask.util.DataOpenApi.SUMMARY_AUTH;
import static com.example.testtask.util.DataOpenApi.SUMMARY_REG;
import static com.example.testtask.util.DataOpenApi.TAG_AUTH_DESCRIPTION;
import static com.example.testtask.util.DataOpenApi.TAG_MAIN_AUTH;
import static com.example.testtask.util.DataOpenApi.URL_AUTH;
import static com.example.testtask.util.DataOpenApi.URL_MAIN_AUTH;
import static com.example.testtask.util.DataOpenApi.URL_REG;

@RequiredArgsConstructor
@Tag(name = TAG_MAIN_AUTH, description = TAG_AUTH_DESCRIPTION)
@RestController
@RequestMapping(URL_MAIN_AUTH)
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = SUMMARY_REG, tags = TAG_MAIN_AUTH)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_CREATE,
                    description = MESSAGE_CREATE_USER
            ),
            @ApiResponse(
                    responseCode = CODE_UNIQ,
                    description = MESSAGE_EMAIL_NOT_UNIQ
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @PostMapping(
            value = URL_REG
    )
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return new ResponseEntity<>(
                authenticationService.register(request),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = SUMMARY_AUTH, tags = TAG_MAIN_AUTH)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_USER_AUTH
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_AUTH,
                    description = MESSAGE_USER_NOT_AUTH
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @PostMapping(value = URL_AUTH)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return new ResponseEntity<>(
                authenticationService.authenticate(request),
                HttpStatus.OK
        );
    }
}
