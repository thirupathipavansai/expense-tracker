package com.example.expensetracker.service;

import com.example.expensetracker.BaseResponse;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.request.ExpenseWebRequest;

public interface ExpenseService {
  BaseResponse addExpense(ExpenseWebRequest expense);
}

