// src/main/java/com/xyz/expense_tracker/Service/ExpenseService.java
package com.xyz.expense_tracker.Service; // <--- Corrected package

import com.xyz.expense_tracker.Entity.Expense; // <--- Corrected import
import java.util.List;
import java.util.Optional;

/**
 * Interface for the Expense Service layer.
 * Defines the business operations related to Expense management.
 * This abstraction allows for different implementations and easier testing.
 */
public interface ExpenseService {

    /**
     * Retrieves all expenses.
     * @return A list of all Expense objects.
     */
    List<Expense> getAllExpenses();

    /**
     * Retrieves an expense by its ID.
     * @param id The ID of the expense.
     * @return An Optional containing the Expense if found, or empty if not found.
     */
    Optional<Expense> getExpenseById(Long id);

    /**
     * Creates a new expense.
     * @param expense The Expense object to be created.
     * @return The created Expense object, including its generated ID.
     */
    Expense createExpense(Expense expense);

    /**
     * Updates an existing expense.
     * @param id The ID of the expense to update.
     * @param expenseDetails The Expense object with updated details.
     * @return An Optional containing the updated Expense if the original expense was found,
     * or empty if the expense with the given ID does not exist.
     */
    Optional<Expense> updateExpense(Long id, Expense expenseDetails);

    /**
     * Deletes an expense by its ID.
     * @param id The ID of the expense to delete.
     * @return true if the expense was deleted, false if it was not found.
     */
    boolean deleteExpense(Long id);
}
