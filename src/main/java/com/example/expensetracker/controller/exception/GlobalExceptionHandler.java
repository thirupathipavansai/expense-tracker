package com.example.expensetracker.controller.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.expensetracker.BaseResponse;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler implements ErrorController {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {ConstraintViolationException.class})
  public BaseResponse constraintViolationException(ConstraintViolationException e) {
    log.error(e.getMessage(), e);
    BaseResponse response = new BaseResponse();
    response.setErrorMessage(e.getConstraintViolations().iterator().next().getMessageTemplate());
    response.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
    return response;
  }

}
