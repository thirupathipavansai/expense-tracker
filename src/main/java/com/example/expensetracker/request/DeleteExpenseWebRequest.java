package com.example.expensetracker.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeleteExpenseWebRequest implements Serializable {
  private static final long serialVersionUID = -4677996057068754501L;
  @NotEmpty(message = "Created By Cannot By Empty")
  private String createdBy;

  @NotEmpty(message = "Expenses to be deleted cannot be Empty")
  private List<Long> ids;
}
