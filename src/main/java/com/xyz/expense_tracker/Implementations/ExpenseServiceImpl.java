// src/main/java/com/xyz/expense_tracker/Service/impl/ExpenseServiceImpl.java
package com.xyz.expense_tracker.Service.impl;

import com.xyz.expense_tracker.Entity.Expense;
import com.xyz.expense_tracker.Repository.ExpenseRepository;
import com.xyz.expense_tracker.Service.ExpenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Still useful for general service-level logic, though MongoDB doesn't have native transactions like SQL DBs.

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * Implementation of the ExpenseService interface for MongoDB.
 * Contains the business logic for managing expenses and interacts with the ExpenseRepository.
 * The @Service annotation marks this class as a Spring service component.
 * @Transactional is less critical for single-document MongoDB operations but good for multi-op logic.
 */
@Service
// Note: @Transactional in Spring Data MongoDB provides transactionality for operations
// on a single replica set if MongoDB 4.0+ is used, or across replica sets/shards with 4.2+.
// For simpler cases, it might not be strictly necessary for every method.
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
    public Optional<Expense> getExpenseById(String id) { // Changed ID type to String
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
    public Optional<Expense> updateExpense(String id, Expense expenseDetails) { // Changed ID type to String
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
    public boolean deleteExpense(String id) { // Changed ID type to String
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }

    @Override
    public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return List.of();
        }
        return expenseRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public BigDecimal getTotalExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return BigDecimal.ZERO;
        }
        // For MongoDB, we fetch the relevant documents and sum them up programmatically
        List<Expense> expenses = expenseRepository.findAmountsByDateBetween(startDate, endDate);
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalExpensesByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        // For MongoDB, we fetch the relevant documents and sum them up programmatically
        List<Expense> expenses = expenseRepository.findAmountsByCategory(category);
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Expense> getExpensesByCategoryAndDateRange(String category, LocalDate startDate, LocalDate endDate) {
        if (category == null || category.trim().isEmpty() || startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return List.of();
        }
        return expenseRepository.findByCategoryAndDateBetween(category, startDate, endDate);
    }

    @Override
    public List<Expense> getExpensesByMonthAndYear(int year, int month) {
        if (year <= 0 || month < 1 || month > 12) {
            return List.of();
        }
        return expenseRepository.findByMonthAndYear(year, month);
    }

    @Override
    public Map<Integer, BigDecimal> getMonthlyExpenseSummary(int year) {
        Map<Integer, BigDecimal> monthlySummary = new LinkedHashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthlySummary.put(i, BigDecimal.ZERO);
        }

        // Fetch all relevant expenses for the year and aggregate in memory
        List<Expense> expenses = expenseRepository.findExpensesForMonthlySummary(year);

        expenses.forEach(expense -> {
            int month = expense.getDate().getMonthValue();
            BigDecimal currentTotal = monthlySummary.getOrDefault(month, BigDecimal.ZERO);
            monthlySummary.put(month, currentTotal.add(expense.getAmount()));
        });

        return monthlySummary;
    }
}
