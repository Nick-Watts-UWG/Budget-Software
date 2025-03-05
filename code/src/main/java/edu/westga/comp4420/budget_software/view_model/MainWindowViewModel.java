package edu.westga.comp4420.budget_software.view_model;

import edu.westga.comp4420.budget_software.model.Expenses;
import edu.westga.comp4420.budget_software.model.UserInfo;
import edu.westga.comp4420.budget_software.model.Expense;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;


/**
 * The view model for the main window.
 * @author Nick Watts
 * @version Spring 2025
 */
public class MainWindowViewModel {

    private Expenses expenses;
    private UserInfo userInfo;
    
    public MainWindowViewModel() {
        this.expenses = new Expenses();
        this.userInfo = new UserInfo();
    }

    public ListProperty<Expense> getExpenses() {
        return this.expenses.getExpenses();
    }

    public void setMonthlyIncome(float income) {
        this.userInfo.setMonthlyIncome(income);
    }

    public void addExpense(Expense expense) {
        this.expenses.addExpense(expense);
    }

    public void removeExpense(Expense expense) {
        this.expenses.removeExpense(expense);
    }

    public StringProperty getUserInfoSummary() {
        return this.userInfo.getUserInfoProperty();
    }

}