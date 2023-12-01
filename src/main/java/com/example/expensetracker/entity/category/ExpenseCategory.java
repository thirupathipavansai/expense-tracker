package com.example.expensetracker.entity.category;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class ExpenseCategory implements  Serializable {

  private static final long serialVersionUID = 820649618149840021L;
  @Column
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryId;

  @Column(nullable = false, unique = true, name = "category_name")
  @Enumerated(EnumType.STRING)
  private CategoryName categoryName;
//
//  @OneToMany(mappedBy = "category")
//  private List<Expense> expenses;

}
