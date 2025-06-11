// src/main/java/com/xyz/expense_tracker/Service/impl/ExpenseServiceImpl.java
package com.xyz.expense_tracker.Implementations;

import com.xyz.expense_tracker.Entity.Expense;
import com.xyz.expense_tracker.Repository.ExpenseRepository;
import com.xyz.expense_tracker.Service.ExpenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ExpenseService interface.
 * Contains the business logic for managing expenses and interacts with the ExpenseRepository.
 * The @Service annotation marks this class as a Spring service component.
 * The @Transactional annotation (at class or method level) ensures that methods are executed
 * within a database transaction, which is crucial for data consistency.
 * Now includes implementations for filtering and summary calculations.
 */
@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Expense createExpense(Expense expense) {
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }
        return expenseRepository.save(expense);
    }

    @Override
    public Optional<Expense> updateExpense(Long id, Expense expenseDetails) {
        return expenseRepository.findById(id)
                .map(expense -> {
                    expense.setDescription(expenseDetails.getDescription());
                    expense.setAmount(expenseDetails.getAmount());
                    if (expenseDetails.getDate() != null) {
                        expense.setDate(expenseDetails.getDate());
                    }
                    expense.setCategory(expenseDetails.getCategory());
                    return expenseRepository.save(expense);
                });
    }

    @Override
    public boolean deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        // Basic validation for date range
        if (startDate == null || endDate == null) {
            // Or throw an IllegalArgumentException
            return List.of(); // Return empty list if dates are null
        }
        if (startDate.isAfter(endDate)) {
            // Or throw an IllegalArgumentException
            return List.of(); // Return empty list if start date is after end date
        }
        return expenseRepository.findByDateBetween(startDate, endDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getTotalExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return BigDecimal.ZERO;
        }
        return expenseRepository.sumAmountByDateBetween(startDate, endDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getTotalExpensesByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return expenseRepository.sumAmountByCategory(category);
    }
}