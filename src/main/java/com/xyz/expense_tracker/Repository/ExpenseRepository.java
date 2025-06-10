// src/main/java/com/xyz/expense_tracker/Repository/ExpenseRepository.java
package com.xyz.expense_tracker.Repository; // <--- Corrected package

import com.xyz.expense_tracker.Entity.Expense; // <--- Corrected import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
