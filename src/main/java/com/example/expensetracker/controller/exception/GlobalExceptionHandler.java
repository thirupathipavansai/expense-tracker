package com.example.expensetracker.controller.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.expensetracker.BaseResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Exception exceptionHandler(MethodArgumentNotValidException e) {
    System.out.println(e);
    return e;
  }


}
