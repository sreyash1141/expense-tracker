// src/main/java/com/xyz/expense_tracker/Entity/Expense.java
package com.xyz.expense_tracker.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.math.BigDecimal;
import java.time.LocalDate;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

/**
 * Represents an Expense document in MongoDB.
 * This class maps to a collection named 'expenses' (by default, as it's the class name lowercase + plural).
 * Includes validation annotations for data integrity.
 */
@Document(collection = "expenses") // Marks this class as a MongoDB document, specifying the collection name
public class Expense {


    @Id
    private String id; // MongoDB uses String IDs (ObjectId) by default

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date; // Stored as ISODate in MongoDB

    @NotBlank(message = "Category cannot be empty")
    @Size(max = 100, message = "Category cannot exceed 100 characters")
    private String category;

    // Default constructor (recommended for Spring Data MongoDB)
    public Expense() {
    }

    // Constructor with fields for convenient object creation
    public Expense(String description, BigDecimal amount, LocalDate date, String category) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    // --- Getters and Setters for all fields ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", category='" + category + '\'' +
                '}';
    }
}
