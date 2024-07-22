package com.br.soluctions.attos.quick_loc.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExecpetionConfig {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> conflictError() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Data conflict");
    }
}
