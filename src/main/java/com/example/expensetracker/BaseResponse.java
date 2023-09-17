package com.example.expensetracker;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class BaseResponse implements Serializable {
  private static final long serialVersionUID = -1081465993575144212L;
  private boolean success;
  private String errorMessage;
  private String errorCode;
}
