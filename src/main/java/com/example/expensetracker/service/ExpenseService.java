package com.example.expensetracker.service;

import javax.persistence.ManyToMany;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.Month;
import com.example.expensetracker.request.CreateExpenseWebRequest;
import com.example.expensetracker.request.UpdateExpenseWebRequest;

public interface ExpenseService {
  /**
   * create an expense
   *
   * @param expense
   * @return
   */
  Expense addExpense(CreateExpenseWebRequest expense);

  /**
   * update existing expense
   *
   * @param request
   * @return
   */
  Expense updateExpense(UpdateExpenseWebRequest request);

  /**
   * @param createdBy
   * @param month
   * @param pageable
   * @return
   */
  Page<Expense> getExpensesByMonth(String createdBy, Month month, Pageable pageable);
}

