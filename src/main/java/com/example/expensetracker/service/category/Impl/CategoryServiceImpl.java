package com.example.expensetracker.service.category.Impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expensetracker.entity.category.CategoryName;
import com.example.expensetracker.entity.category.ExpenseCategory;
import com.example.expensetracker.repository.ExpenseCategoryRepository;
import com.example.expensetracker.request.category.CreateCategoryWebRequest;
import com.example.expensetracker.service.category.CategoryService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  ExpenseCategoryRepository expenseCategoryRepository;

  @Override
  public ExpenseCategory addExpenseCategory(CreateCategoryWebRequest createCategoryWebRequest) {
    if (Objects.nonNull(createCategoryWebRequest)) {
      ExpenseCategory expenseCategory = new ExpenseCategory();
      expenseCategory.setCategoryName(Enum.valueOf(CategoryName.class, createCategoryWebRequest.getCategoryName()));
      try {
        expenseCategoryRepository.save(expenseCategory);
      } catch (Exception e) {
        log.info("Error while saving entity  category {} ", expenseCategory);
        return null;
      }
      return expenseCategory;
    }
    return null;
  }
}
