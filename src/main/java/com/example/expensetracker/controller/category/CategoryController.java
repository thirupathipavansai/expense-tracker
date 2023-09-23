package com.example.expensetracker.controller.category;

import static com.example.expensetracker.miscellaneous.CategoryErrorMessage.DUPLICATE_CATEGORY_NAME;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetracker.BaseResponse;
import com.example.expensetracker.annotaion.CreateCategoryValid;
import com.example.expensetracker.entity.category.ExpenseCategory;
import com.example.expensetracker.request.category.CreateCategoryWebRequest;
import com.example.expensetracker.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@Tag(name = "Category Controller")
@RequestMapping(value = CategoryApiPath.BASE_PATH)
@Slf4j
@Validated
public class CategoryController {

  @Autowired
  CategoryService categoryService;

  @RequestMapping(method = RequestMethod.POST, value = CategoryApiPath.ADD_CATEGORY)
  @Operation(summary = "Create Expense Category", description = "Creating Expense category")
  public BaseResponse addCategory(@RequestBody @Valid @CreateCategoryValid(message = "Invalid Category Name") CreateCategoryWebRequest createCategoryWebRequest) {
    log.info("Creating Category with request {} ", createCategoryWebRequest);
    ExpenseCategory expenseCategory = categoryService.addExpenseCategory(createCategoryWebRequest);
    if (Objects.nonNull(expenseCategory)) {
      return new BaseResponse(true, null, null);
    }
    return new BaseResponse(false, DUPLICATE_CATEGORY_NAME, null);
  }

}
