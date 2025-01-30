package dev.marvin.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public static ResponseEntity<?> handleDuplicateResourceException(DuplicateResourceException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public static ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // Create a map to store field error messages
        Map<String, String> errors = new HashMap<>();

        // Iterate through all validation errors and add them to the map
        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                // Add the field name and its error message to the map
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                // Add object error name and its message (for non-field validation)
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        });

        // Create an ErrorResponse containing validation errors
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), errors.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
