package com.example.demo.service;


import com.example.demo.entity.Expense;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import com.example.demo.entity.ExpenseStatus;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
    public Expense approveExpense(Long id) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (expense.getStatus() != ExpenseStatus.PENDING) {
            throw new RuntimeException("Only pending expenses can be approved");
        }

        expense.setStatus(ExpenseStatus.APPROVED);

        return expenseRepository.save(expense);
    }

    public Expense rejectExpense(Long id, String reason) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (expense.getStatus() != ExpenseStatus.PENDING) {
            throw new RuntimeException("Only pending expenses can be rejected");
        }

        expense.setStatus(ExpenseStatus.REJECTED);
        expense.setRejectionReason(reason);

        return expenseRepository.save(expense);
    }
}

