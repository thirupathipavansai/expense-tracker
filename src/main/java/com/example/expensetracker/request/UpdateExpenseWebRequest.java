package com.example.expensetracker.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExpenseWebRequest extends CreateExpenseWebRequest {
  private Long id;
}
