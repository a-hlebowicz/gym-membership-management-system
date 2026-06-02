package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //conflict = 409; when ConflictException go here
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String >> handleConflict(ConflictException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", exception.getMessage()));
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String >> handleNotFound(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", exception.getMessage()));
    }
}
