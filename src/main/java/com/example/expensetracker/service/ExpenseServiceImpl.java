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
  @Cacheable(key = "#expense", value = "expense", unless =  "#result == null")
  public BaseResponse addExpense(ExpenseWebRequest expenseWebRequest) {
    if (Objects.nonNull(expenseWebRequest)) {
      Expense expense = new Expense();
      BeanUtils.copyProperties(expenseWebRequest, expense);
      expense.setCreatedBy(expenseWebRequest.getUsername());
      LocalDate currentDate = LocalDate.now();
      expense.setCreatedDate(currentDate.toString());
      log.info("Saving expense {} ", expense);
      expenseRepository.save(expense);
      return new BaseResponse(true, null, null);
    }
    return new BaseResponse(false, ExpenseErroMessage.EXPENSE_CANNOT_BE_NULL.getDesc(),
        ExpenseErroMessage.EXPENSE_CANNOT_BE_NULL.getCode());
  }
}
