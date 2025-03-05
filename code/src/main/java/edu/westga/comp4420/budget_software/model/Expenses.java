package edu.westga.comp4420.budget_software.model;

import java.util.ArrayList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class Expenses {

    private ListProperty<Expense> expenses;

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

    /**
     * Gets the expeneses list property
     * @return the list property of expenses.
     */
    public ListProperty<Expense> getExpenses() {
        return this.expenses;
    }
}
