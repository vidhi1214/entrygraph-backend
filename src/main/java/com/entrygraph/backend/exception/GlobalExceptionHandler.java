package com.entrygraph.backend.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Validation failed");
        response.put("errors", errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(DestinationNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDestinationNotFound(
            DestinationNotFoundException ex) {

        return errorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage());
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTagNotFound(
            TagNotFoundException ex) {

        return errorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage());
    }

    @ExceptionHandler(DuplicateTagException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateTag(
            DuplicateTagException ex) {

        return errorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        return errorResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid request parameter");
    }

    private ResponseEntity<Map<String, Object>> errorResponse(
            HttpStatus status,
            String message) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
