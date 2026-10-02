package com.example.demo.controller;

import com.example.demo.entity.Expense;
import com.example.demo.entity.ExpenseStatus;
import com.example.demo.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense createExpense(@Valid @RequestBody Expense expense) {
        return expenseService.createExpense(expense);
    }

    @GetMapping
    public Page<Expense> getAllExpenses(Pageable pageable) {
        return expenseService.getAllExpenses(pageable);
    }

    @GetMapping("/status/{status}")
    public Object getExpensesByStatus(@PathVariable ExpenseStatus status) {
        return expenseService.getExpensesByStatus(status);
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @PatchMapping("/{id}/approve")
    public Expense approveExpense(@PathVariable Long id) {
        return expenseService.approveExpense(id);
    }

    @PatchMapping("/{id}/reject")
    public Expense rejectExpense(
            @PathVariable Long id,
            @RequestParam String reason) {

        return expenseService.rejectExpense(id, reason);
    }

    @PatchMapping("/{id}/reimburse")
    public Expense reimburseExpense(@PathVariable Long id) {
        return expenseService.reimburseExpense(id);
    }
}