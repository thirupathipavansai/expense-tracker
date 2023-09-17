package com.example.expensetracker.entity;

public enum PaymentType {
  UPI("UPI"), CREDIT_CARD("CreditCard"), ONLINE("Online");

  String paymentType;

  PaymentType(String Type) {
    this.paymentType = Type;
  }
  }
