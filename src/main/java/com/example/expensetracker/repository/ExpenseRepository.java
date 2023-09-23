package com.example.expensetracker.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.expensetracker.entity.expense.Expense;
import com.example.expensetracker.entity.expense.Month;

@Repository

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  Page<Expense> findBycreatedByAndMonth(String createdBy, Month month, Pageable pageable);


}
