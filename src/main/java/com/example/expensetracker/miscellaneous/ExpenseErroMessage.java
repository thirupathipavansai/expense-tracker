package com.example.expensetracker.miscellaneous;

public enum ExpenseErroMessage {

  EXPENSE_CANNOT_BE_NULL("ERR-ET-20001", 400, "Expense Cannot Be Null"), INVALID_CATEGORY("ERR-ET-20002", 400,
      "Invalid Error Category");

  private final String code;
  private final int httpStatus;
  private final String desc;

  ExpenseErroMessage(String code, int httpStatus, String desc) {
    this.code = code;
    this.httpStatus = httpStatus;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }

  public int getHttpStatus() {
    return httpStatus;
  }
}
