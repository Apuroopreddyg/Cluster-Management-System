package com.hms.cluster_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(
            RuntimeException ex) {

        Map<String, String> error = new LinkedHashMap<>();

        error.put("message", ex.getMessage());

        HttpStatus status;

        if ("Hospital not found".equals(ex.getMessage())
                || "Cluster not found".equals(ex.getMessage())) {

            status = HttpStatus.NOT_FOUND;

        } else {

            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        Map<String, String> error = new LinkedHashMap<>();
        error.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(error);
    }
}