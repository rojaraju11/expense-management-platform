package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Expense;
import com.example.demo.entity.ExpenseStatus;
import com.example.demo.exception.ExpenseNotFoundException;
import com.example.demo.exception.InvalidExpenseStateException;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpense(Expense expense) {
        expense.setStatus(ExpenseStatus.PENDING);
        return expenseRepository.save(expense);
    }

    public Page<Expense> getAllExpenses(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    public List<Expense> getExpensesByStatus(ExpenseStatus status) {
        return expenseRepository.findByStatus(status);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException("Expense not found"));
    }

    @Transactional
    public Expense approveExpense(Long id) {

        Expense expense = getExpenseById(id);

        if (expense.getStatus() != ExpenseStatus.PENDING) {
            throw new InvalidExpenseStateException(
                    "Only pending expenses can be approved");
        }

        expense.setStatus(ExpenseStatus.APPROVED);

        return expenseRepository.save(expense);
    }

    @Transactional
    public Expense rejectExpense(Long id, String reason) {

        Expense expense = getExpenseById(id);

        if (expense.getStatus() != ExpenseStatus.PENDING) {
            throw new InvalidExpenseStateException(
                    "Only pending expenses can be rejected");
        }

        expense.setStatus(ExpenseStatus.REJECTED);
        expense.setRejectionReason(reason);

        return expenseRepository.save(expense);
    }

    @Transactional
    public Expense reimburseExpense(Long id) {

        Expense expense = getExpenseById(id);

        if (expense.getStatus() != ExpenseStatus.APPROVED) {
            throw new InvalidExpenseStateException(
                    "Only approved expenses can be reimbursed");
        }

        expense.setStatus(ExpenseStatus.REIMBURSED);

        return expenseRepository.save(expense);
    }
}