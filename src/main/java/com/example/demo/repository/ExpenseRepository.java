package com.example.demo.repository;

import com.example.demo.entity.Expense;
import com.example.demo.entity.ExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByStatus(ExpenseStatus status);
}