package com.example.expensetracker.controller.expense;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetracker.BaseResponse;
import com.example.expensetracker.entity.expense.Expense;
import com.example.expensetracker.entity.expense.Month;
import com.example.expensetracker.request.CreateExpenseWebRequest;
import com.example.expensetracker.request.UpdateExpenseWebRequest;
import com.example.expensetracker.service.expense.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "Expense API")
@RequestMapping(value = ExpenseControllerApiPath.BASE_PATH)
public class ExpenseController {

  @Autowired
  ExpenseService expenseService;

  @RequestMapping(value = ExpenseControllerApiPath.ADD_EXPENSE, method = RequestMethod.POST)
  @Operation(description = "create Expense", summary = "Creating Expense")
  public BaseResponse addExpense(@RequestBody @Valid CreateExpenseWebRequest expense) throws Exception {
    try {
      Expense expense1 = new Expense();
      expense1 = expenseService.addExpense(expense);
      if (Objects.nonNull(expense1)) {
        return new BaseResponse(true, "", "");
      }
    } catch (Exception e) {
      log.info("Error while adding Expense {} ", e.getMessage());
    }
    return new BaseResponse(false, "Creating expense failed", "");
  }

  @RequestMapping(value = ExpenseControllerApiPath.UPDATE, method = RequestMethod.PUT)
  @Operation(description = "Update Expense", summary = "Updating Expense")
  public BaseResponse updateExpense(@RequestBody @Valid UpdateExpenseWebRequest request) {
    try {
      Expense expense1 = expenseService.updateExpense(request);
      if (Objects.nonNull(expense1)) {
        return new BaseResponse(true, "", "");
      }
    } catch (Exception e) {
      log.info("Error while adding Expense {} ", e.getMessage());
    }
    return new BaseResponse(false, "Updating expense Failed", "");
  }

  @RequestMapping(value = ExpenseControllerApiPath.GET_EXPENSES_BY_MONTH, method = RequestMethod.GET)
  @Operation(description = "list of expenses in a month", summary = "list of expenses in a month")
  public Page<Expense> getExpensesByMonth(@RequestParam(value = "month") Month month,
      @RequestParam("createdBy") String createdBy,
      @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
    return expenseService.getExpensesByMonth(createdBy, month, pageable);
  }

}
