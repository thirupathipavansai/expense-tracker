package com.example.expensetracker.entity.category;

public enum CategoryName {
  TRAVEL("TRAVEL"), GROCERIES("GROCERIES"), BILLS("BILLS"), INSURANCE("INSURANCE"), ENTERTAINMENT(
      "ENTERTAINMENT"), EDUCATION("EDUCATION"), RENT("RENT"), DEBT("DEBT"), MEDICINE("MEDICINE"), DONATION(
      "DONATION"), BOOK("BOOK");

  String categoryName;

  CategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

}
