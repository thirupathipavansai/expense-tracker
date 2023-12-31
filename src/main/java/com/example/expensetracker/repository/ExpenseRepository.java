package com.example.expensetracker.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.expensetracker.entity.expense.Expense;
import com.example.expensetracker.entity.expense.Month;

@Repository

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  Page<Expense> findByCreatedByAndMonth(String createdBy, Month month, Pageable pageable);

  Page<Expense> findByCreatedBy(String createdBy, Pageable pageable);

  List<Expense> findByCreatedByAndMonth(String createdBy, Month month);

  List<Expense> findByCreatedByAndIdIn(String createdBy, List<Long> ids);

  @Query(value = "SELECT month, SUM(amount) AS total_expenses " +
      "FROM expense " +
      "WHERE created_by = :createdBy AND EXTRACT(YEAR FROM TO_DATE(created_date, 'YYYY-MM-DD')) = :year " +
      "GROUP BY month " +
      "ORDER BY month", nativeQuery = true)
  List<Object> getTotalExpensesByUserAndYear(@Param("createdBy") String createdBy, @Param("year") Long year);



}
