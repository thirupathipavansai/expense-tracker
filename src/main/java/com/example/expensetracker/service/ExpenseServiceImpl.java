package com.example.expensetracker.service;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.expensetracker.BaseResponse;
import com.example.expensetracker.miscellaneous.ExpenseErroMessage;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.request.ExpenseWebRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExpenseServiceImpl implements ExpenseService {

  @Autowired
  ExpenseRepository expenseRepository;

  @Override
  @Cacheable(value = "expenseCache", key = "'expense-' + #expenseWebRequest.username")
  public Expense addExpense(ExpenseWebRequest expenseWebRequest) {
    if (Objects.nonNull(expenseWebRequest)) {
      Expense expense = new Expense();
      BeanUtils.copyProperties(expenseWebRequest, expense);
      expense.setCreatedBy(expenseWebRequest.getUsername());
      LocalDate currentDate = LocalDate.now();
      expense.setCreatedDate(currentDate.toString());
      log.info("Saving expense {} ", expense);
      expenseRepository.save(expense);
      return expense; // Return the saved Expense object.
    }
    return null; // Return null when expenseWebRequest is null.
  }

}
