package com.example.expensetracker.service.category;

import java.util.List;

import com.example.expensetracker.entity.category.ExpenseCategory;
import com.example.expensetracker.request.category.CreateCategoryWebRequest;

public interface CategoryService {
  /**
   * Creating Expense Category
   * @param createCategoryWebRequest
   * @return
   */
  ExpenseCategory addExpenseCategory(CreateCategoryWebRequest createCategoryWebRequest);

  /**
   * Get All  Expense Categories
   * @return
   */
  List<String> getAllCategories();
}
