package edu.westga.comp4420.budget_software.view_model;

import edu.westga.comp4420.budget_software.model.Expenses;
import edu.westga.comp4420.budget_software.model.UserInfo;
import edu.westga.comp4420.budget_software.model.Category;
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
        this.populateDefualtExpenses();
        this.userInfo = new UserInfo();
    }

    private void populateDefualtExpenses() {
        Expense expense = new Expense("Real expense", 30, Category.BILL);
        this.expenses.addExpense(expense);
        Expense expenseTwo = new Expense("Fake expense", 30, Category.GROCERY);
        this.expenses.addExpense(expenseTwo);
    }

    public ListProperty<Expense> getExpenses() {
        return this.expenses.getExpenses();
    }

    public void setMonthlyIncome(float income) {
        this.userInfo.setMonthlyIncome(income);
    }

    public StringProperty getUserInfoSummary() {
        return this.userInfo.getUserInfoProperty();
    }

}