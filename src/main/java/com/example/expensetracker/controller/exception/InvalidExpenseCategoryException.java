package com.example.expensetracker.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidExpenseCategoryException extends RuntimeException {
  String message;

}
