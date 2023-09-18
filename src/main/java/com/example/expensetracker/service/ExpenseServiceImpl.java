package com.example.expensetracker.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.Month;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.request.CreateExpenseWebRequest;
import com.example.expensetracker.request.UpdateExpenseWebRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExpenseServiceImpl implements ExpenseService {

  @Autowired
  ExpenseRepository expenseRepository;

  @Override
  @Cacheable(value = "expenseCache", key = "'expense-' + #expenseWebRequest.username")
  public Expense addExpense(CreateExpenseWebRequest expenseWebRequest) {
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

  @Override
  public Expense updateExpense(UpdateExpenseWebRequest request) {
    if (Objects.nonNull(request)) {
      Optional<Expense> expense = expenseRepository.findById(request.getId());
      if (expense.isPresent()) {
        Date date = new Date();
        BeanUtils.copyProperties(request, expense.get());
        expense.get().setUpdatedBy(request.getUsername());
      }
      log.info("Updating expense {} ", expense);
      return expenseRepository.save(expense.get());
    }
    return null;
  }

  @Override
  public Page<Expense> getExpensesByMonth(String createdBy, Month month, Pageable pageable) {
    Page<Expense> expenses = expenseRepository.findBycreatedByAndMonth(createdBy, month, pageable);
    return new PageImpl<>(expenses.getContent(), pageable, expenses.getTotalElements());
  }


}
