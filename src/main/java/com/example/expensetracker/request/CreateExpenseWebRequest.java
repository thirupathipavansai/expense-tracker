package com.example.expensetracker.request;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.example.expensetracker.entity.expense.Month;
import com.example.expensetracker.entity.expense.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class CreateExpenseWebRequest {



  @NotNull
  private PaymentType paymentType;

  @NotEmpty
  private String Description;

  @NotNull(message = "Month Cannot be Empty")
  private Month month;

  @Min(value = 1, message = "Minimum Amount is 1")
  private Double amount;

  @NotNull(message = "Username cannot be empty")
  private String username;
}
