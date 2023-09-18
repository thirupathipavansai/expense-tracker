package com.example.expensetracker.entity;

public enum Month {
  JANUARY("JANUARY"), FEBRUARY("FEBRUARY"), MARCH("MARCH"), APRIL("APRIL"), MAY("MAY"), JUNE("JUNE"), JULY(
      "JULY"), AUGUST("AUGUST"), SEPTEMBER("SEPTEMBER"), OCTOBER("OCTOBER"), NOVEMBER("NOVEMBER"), DECEMBER("DECEMBER");

  String month;

  Month(String month1) {
    this.month = month1;
  }
}
