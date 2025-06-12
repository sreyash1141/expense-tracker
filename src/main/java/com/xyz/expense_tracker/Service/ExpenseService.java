// src/main/java/com/xyz/expense_tracker/Service/ExpenseService.java
package com.xyz.expense_tracker.Service;

import com.xyz.expense_tracker.Entity.Expense;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface for the Expense Service layer for MongoDB.
 * Defines the business operations related to Expense management.
 */
public interface ExpenseService {

    List<Expense> getAllExpenses();
    Optional<Expense> getExpenseById(String id); // Changed ID type to String
    Expense createExpense(Expense expense);
    Optional<Expense> updateExpense(String id, Expense expenseDetails); // Changed ID type to String
    boolean deleteExpense(String id); // Changed ID type to String

    List<Expense> getExpensesByCategory(String category);
    List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate);
    BigDecimal getTotalExpensesByDateRange(LocalDate startDate, LocalDate endDate);
    BigDecimal getTotalExpensesByCategory(String category);

    List<Expense> getExpensesByCategoryAndDateRange(String category, LocalDate startDate, LocalDate endDate);
    List<Expense> getExpensesByMonthAndYear(int year, int month);
    Map<Integer, BigDecimal> getMonthlyExpenseSummary(int year);
}
