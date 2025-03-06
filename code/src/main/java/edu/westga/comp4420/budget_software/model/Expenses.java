package edu.westga.comp4420.budget_software.model;

import java.util.ArrayList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * The class that holds all of the user's expenses.
 * 
 * @author Nick
 * @version Spring 2025
 */
public class Expenses {

    private ListProperty<Expense> expenses;

    /**
     * The constructor for Expenses.
     * Sets up the observable array list so the expenses can be bound.
     */
    public Expenses() {
        this.expenses = new SimpleListProperty<Expense>(FXCollections.observableArrayList(new ArrayList<Expense>()));
    }

    /**
     * Add an expense to the expenses.
     * @param expense the expense to add
     */
    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }

    /**
     * Remove an expense from the expeneses
     * @param expense The expense to remove
     */
    public void removeExpense(Expense expense) {
        if (!this.expenses.contains(expense)) {
            return;
        }
        this.expenses.remove(expense);
    }


    public ListProperty<Expense> getExpenses() {
        return this.expenses;
    }
}
