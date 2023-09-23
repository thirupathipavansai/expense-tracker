package com.example.expensetracker.entity.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "unique_category_name", columnNames = "category_name"))
@Data
public class ExpenseCategory {

  @Column
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long categoryId;

  @Column(name = "category_name")
  @Enumerated(EnumType.STRING)
  private CategoryName categoryName;

}
