package com.torchapp.demo.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class RestErrorMessage {
    private HttpStatus status;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public RestErrorMessage(HttpStatus status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public RestErrorMessage(HttpStatus status, String message, String path, Map<String, String> errors) {
        this(status, message, path);
        this.errors = errors;
    }
}
