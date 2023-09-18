package com.example.expensetracker.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetracker.BaseResponse;
import com.example.expensetracker.request.ExpenseWebRequest;
import com.example.expensetracker.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = ExpenseControllerApiPath.BASE_PATH)
public class ExpenseController {

  @Autowired
  ExpenseService expenseService;

  @RequestMapping(value = ExpenseControllerApiPath.ADD_EXPENSE)
  public BaseResponse addExpense(@RequestBody @Valid  ExpenseWebRequest expense) throws Exception {
    try {
      return expenseService.addExpense(expense);
    } catch (Exception e) {
      log.info("Error while adding Expense {} ", e.getMessage());
    }
    return new BaseResponse(false, "", "");
  }
}
