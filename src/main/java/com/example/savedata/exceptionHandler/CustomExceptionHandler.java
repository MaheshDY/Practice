package com.example.savedata.exceptionHandler;

import com.example.savedata.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(EmployeeNotFoundException.class)
  public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
