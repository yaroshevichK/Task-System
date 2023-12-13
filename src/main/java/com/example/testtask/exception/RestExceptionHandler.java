package com.example.testtask.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.AccessException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.testtask.util.DataValid.ERROR_AUTH;
import static com.example.testtask.util.DataValid.ERROR_FORBIDDEN;

@RestControllerAdvice
public class RestExceptionHandler {
    //код - 500
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //ошибка авторизации
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsExceptions(BadCredentialsException e) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ERROR_AUTH);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    //доступ запрещен
    @ExceptionHandler(value = {AccessException.class})
    public ResponseEntity<Object> handleAccessExceptions(AccessException e) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ERROR_FORBIDDEN);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    //не уникальные записи
    @ExceptionHandler(value = {NotUniqueException.class})
    public ResponseEntity<Object> handleNotUniqueExceptions(NotUniqueException e) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    //не найдены пользователи
    @ExceptionHandler(value = {NotExistsInDBException.class})
    public ResponseEntity<Object> handleNotExistsInDBExceptions(NotExistsInDBException e) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.GONE);
    }

    //ошибки валидатора
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(
            MethodArgumentNotValidException e) {

        Map<String, String> mapErrors = new HashMap<>();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors()
                .forEach(error -> {
                    String field = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
                    String message = error.getDefaultMessage();
                    if (mapErrors.containsKey(field)) {
                        mapErrors.put(field, mapErrors.get(field) + "; " + message);
                    } else {
                        mapErrors.put(field, message);
                    }
                });

        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .timeStamp(new Date())
                .message(mapErrors.toString())
                .build();

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
