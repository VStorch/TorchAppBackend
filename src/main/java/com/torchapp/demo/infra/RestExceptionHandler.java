package com.torchapp.demo.infra;

import com.torchapp.demo.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<RestErrorMessage> resourceNotFoundHandler (ResourceNotFoundException e, HttpServletRequest request) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<RestErrorMessage> handleBadRequest(BadRequestException e, HttpServletRequest request) {
        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(VerificationCodeException.class)
    private ResponseEntity<RestErrorMessage> handleCodeExpired(
            VerificationCodeException e,
            HttpServletRequest request
    ) {
        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(MaxAttemptsExceededException.class)
    private ResponseEntity<RestErrorMessage> handleMaxAttempts(
            MaxAttemptsExceededException e,
            HttpServletRequest request
    ) {
        Map<String, Object> data = new HashMap<>();
        data.put("maxAttempts", e.getMaxAttempts());

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.TOO_MANY_REQUESTS,
                e.getMessage(),
                request.getRequestURI(),
                data
        );
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(errorMessage);
    }

    @ExceptionHandler(InvalidVerificationCodeException.class)
    private ResponseEntity<RestErrorMessage> handleInvalidCode(
            InvalidVerificationCodeException e,
            HttpServletRequest request
    ) {
        Map<String, Object> data = new HashMap<>();
        data.put("remainingAttempts", e.getRemainingAttempts());

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                request.getRequestURI(),
                data
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(EmailSendException.class)
    private ResponseEntity<RestErrorMessage> handleEmailSendError(
            EmailSendException e,
            HttpServletRequest request
    ) {
        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro ao enviar o email. Tente novamente mais tarde.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    private ResponseEntity<RestErrorMessage> handleIllegalExceptions(RuntimeException e, HttpServletRequest request) {
        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<RestErrorMessage> handleValidationExceptions(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                "Erro de validação nos campos",
                request.getRequestURI(),
                errors,
                true
        );
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<RestErrorMessage> globalExceptionHandler(Exception e, HttpServletRequest request) {
        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno no servidor: "+ e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
