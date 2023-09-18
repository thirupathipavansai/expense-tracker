package com.example.expensetracker.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.Month;

@Repository

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  Page<Expense> findBycreatedByAndMonth(String createdBy, Month month, Pageable pageable);


}
