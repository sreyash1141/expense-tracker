// src/main/java/com/xyz/expense_tracker/Service/ExpenseService.java
package com.xyz.expense_tracker.Service;

import com.xyz.expense_tracker.Entity.Expense;
import java.math.BigDecimal; // For monetary values
import java.time.LocalDate;   // For date filtering
import java.util.List;
import java.util.Optional;

/**
 * Interface for the Expense Service layer.
 * Defines the business operations related to Expense management.
 * This abstraction allows for different implementations and easier testing.
 * Now includes methods for filtering and summary calculations.
 */
public interface ExpenseService {

    List<Expense> getAllExpenses();
    Optional<Expense> getExpenseById(Long id);
    Expense createExpense(Expense expense);
    Optional<Expense> updateExpense(Long id, Expense expenseDetails);
    boolean deleteExpense(Long id);

    /**
     * Retrieves expenses filtered by a specific category.
     * @param category The category name to filter by.
     * @return A list of Expense objects matching the given category.
     */
    List<Expense> getExpensesByCategory(String category);

    /**
     * Retrieves expenses within a specified date range.
     * @param startDate The start date (inclusive).
     * @param endDate The end date (inclusive).
     * @return A list of Expense objects within the specified date range.
     */
    List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * Calculates the total amount of expenses within a specified date range.
     * @param startDate The start date (inclusive).
     * @param endDate The end date (inclusive).
     * @return The total sum of expenses for the given date range.
     */
    BigDecimal getTotalExpensesByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * Calculates the total amount of expenses for a specific category.
     * @param category The category name.
     * @return The total sum of expenses for the given category.
     */
    BigDecimal getTotalExpensesByCategory(String category);
}
