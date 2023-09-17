package com.example.expensetracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.expensetracker.entity.Expense;

@Repository

public interface ExpenseRepository extends JpaRepository<Expense,Long> {


}
