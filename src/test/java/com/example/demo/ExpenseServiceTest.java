package com.example.demo.service;

import com.example.demo.entity.Expense;
import com.example.demo.entity.ExpenseStatus;
import com.example.demo.exception.ExpenseNotFoundException;
import com.example.demo.exception.InvalidExpenseStateException;
import com.example.demo.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void approvePendingExpense() {

        Expense expense = new Expense();
        expense.setId(1L);
        expense.setAmount(500);
        expense.setDescription("Travel");
        expense.setStatus(ExpenseStatus.PENDING);

        when(expenseRepository.findById(1L))
                .thenReturn(Optional.of(expense));

        when(expenseRepository.save(expense))
                .thenReturn(expense);

        Expense result = expenseService.approveExpense(1L);

        assertEquals(ExpenseStatus.APPROVED, result.getStatus());
        verify(expenseRepository).save(expense);
    }

    @Test
    void shouldRejectApprovalForRejectedExpense() {

        Expense expense = new Expense();
        expense.setId(1L);
        expense.setStatus(ExpenseStatus.REJECTED);

        when(expenseRepository.findById(1L))
                .thenReturn(Optional.of(expense));

        assertThrows(
                InvalidExpenseStateException.class,
                () -> expenseService.approveExpense(1L)
        );

        verify(expenseRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenExpenseDoesNotExist() {

        when(expenseRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ExpenseNotFoundException.class,
                () -> expenseService.getExpenseById(99L)
        );
    }

    @Test
    void reimburseApprovedExpense() {

        Expense expense = new Expense();
        expense.setId(1L);
        expense.setStatus(ExpenseStatus.APPROVED);

        when(expenseRepository.findById(1L))
                .thenReturn(Optional.of(expense));

        when(expenseRepository.save(expense))
                .thenReturn(expense);

        Expense result = expenseService.reimburseExpense(1L);

        assertEquals(ExpenseStatus.REIMBURSED, result.getStatus());
        verify(expenseRepository).save(expense);
    }
}