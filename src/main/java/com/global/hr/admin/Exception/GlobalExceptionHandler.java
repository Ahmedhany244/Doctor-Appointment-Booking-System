package com.global.hr.admin.Exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;



@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(AlreadyUpdatedException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(AlreadyUpdatedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(DataIntegrityViolationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }





    //----------------------------------------------------------------------------------
     @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDoctorNotFound(DoctorNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

     @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAdminNotFound(AdminNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateEntry(DuplicateEntryException ex) {
        return buildErrorResponse("Duplicate entry: "+ ex.getMessage(), HttpStatus.CONFLICT);
    }



    // handles exceptions due to failed @Valid annotation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
           Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // handles excpetion to missing parameters in @RequestParam methods
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParameters(MissingServletRequestParameterException ex) {
        return buildErrorResponse("not sufficient json body sent", HttpStatus.BAD_REQUEST);
    }


    // handle incorrect api endpoint exception
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFound(NoHandlerFoundException ex) {
    return new ResponseEntity<>(
        "The requested endpoint does not exist: " + ex.getRequestURL(),
        HttpStatus.NOT_FOUND
    );
    }

    // Handles constraint violations for @Positive, @NotNull, and others
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
    return new ResponseEntity<>("Validation error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    
    // Handles incorrect type (e.g., sending string instead of number)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String >> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
          Map<String, String> error = new HashMap<>();
        // Case 1: Enum type (e.g., Gender)
        if (ex.getRequiredType().isEnum()) {
            String allowedValues = Arrays.toString(ex.getRequiredType().getEnumConstants());
            error.put(ex.getName(), 
                "Invalid value. Allowed values: " + allowedValues);
        } 
        else { //other invalid types
        error.put(ex.getName(), "Invalid type for parameter: " + ex.getRequiredType());
    }
    return ResponseEntity.badRequest().body(error);
}

    //handle invalid format for enums
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String >> handleInvalidFormat(HttpMessageNotReadableException ex) {
          Map<String, String> error = new HashMap<>();
        error.put("gender", "Invalid value. Must be 'M' or 'F'");
    
    return ResponseEntity.badRequest().body(error);
}

        // return buildErrorResponse("Invalid request format: " + ex.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
    

    // @ExceptionHandler(HttpMessageNotReadableException.class)
    // public ResponseEntity<Map<String, Object>> handleInvalidFormat(HttpMessageNotReadableException ex) {
    //     return buildErrorResponse("Invalid request format: " + ex.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
    // }

    // // Handles entity not found 
    // @ExceptionHandler(EntityNotFoundException.class)
    // public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
    //     return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    // }




     private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }
    
}
