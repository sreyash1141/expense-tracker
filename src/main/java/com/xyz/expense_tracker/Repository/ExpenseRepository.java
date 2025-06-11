// src/main/java/com/xyz/expense_tracker/Repository/ExpenseRepository.java
package com.xyz.expense_tracker.Repository;

import com.xyz.expense_tracker.Entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByCategory(String category);

    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

    //Calculates the sum of amounts for all expenses within a specific date range.
    //@return The total sum of expenses within the date range, or BigDecimal.ZERO if no expenses.
    @Query("SELECT COALESCE(SUM(e.amount), 0.00) FROM Expense e WHERE e.date BETWEEN ?1 AND ?2")
    BigDecimal sumAmountByDateBetween(LocalDate startDate, LocalDate endDate);


    //Calculates the sum of amounts for all expenses in a specific category.
    //@return The total sum of expenses for the given category, or BigDecimal.ZERO if no expenses.
    @Query("SELECT COALESCE(SUM(e.amount), 0.00) FROM Expense e WHERE e.category = ?1")
    BigDecimal sumAmountByCategory(String category);
}
