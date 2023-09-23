package com.example.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expensetracker.entity.category.ExpenseCategory;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory,String> {
}
