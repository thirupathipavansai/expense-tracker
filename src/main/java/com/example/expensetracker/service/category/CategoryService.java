package com.example.expensetracker.service.category;

import com.example.expensetracker.entity.category.ExpenseCategory;
import com.example.expensetracker.request.category.CreateCategoryWebRequest;

public interface CategoryService {
  ExpenseCategory addExpenseCategory(CreateCategoryWebRequest createCategoryWebRequest);
}
