// src/main/java/com/xyz/expense_tracker/Service/impl/ExpenseServiceImpl.java
package com.xyz.expense_tracker.Implementations; // <--- Corrected package

import com.xyz.expense_tracker.Entity.Expense;     // <--- Corrected import
import com.xyz.expense_tracker.Repository.ExpenseRepository; // <--- Corrected import
import com.xyz.expense_tracker.Service.ExpenseService;     // <--- Corrected import

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // <--- Ensure this is present
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ExpenseService interface.
 * Contains the business logic for managing expenses and interacts with the ExpenseRepository.
 * The @Service annotation marks this class as a Spring service component.
 * The @Transactional annotation (at class or method level) ensures that methods are executed
 * within a database transaction, which is crucial for data consistency.
 */
@Service // <--- THIS IS CRUCIAL FOR SPRING TO CREATE A BEAN OF THIS CLASS
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expense createExpense(Expense expense) {
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }
        return expenseRepository.save(expense);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
