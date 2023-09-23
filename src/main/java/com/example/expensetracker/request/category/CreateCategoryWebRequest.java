package com.example.expensetracker.request.category;

import com.example.expensetracker.entity.category.CategoryName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryWebRequest {
  private String categoryName;
}
