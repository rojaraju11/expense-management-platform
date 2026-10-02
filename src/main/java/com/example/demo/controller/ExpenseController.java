package com.example.demo.controller;

import com.example.demo.entity.Expense;
import com.example.demo.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseService.createExpense(expense);
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
}
