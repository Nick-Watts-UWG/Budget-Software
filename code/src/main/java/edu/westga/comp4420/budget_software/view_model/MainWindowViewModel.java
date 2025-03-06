package edu.westga.comp4420.budget_software.view_model;

import edu.westga.comp4420.budget_software.model.Expenses;
import edu.westga.comp4420.budget_software.model.UserInfo;
import edu.westga.comp4420.budget_software.model.BudgetStats;
import edu.westga.comp4420.budget_software.model.Expense;

import java.io.IOException;

import edu.westga.comp4420.budget_software.model.BudgetDataManager;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ListProperty;


/**
 * The view model for the main window.
 * @author Nick Watts
 * @version Spring 2025
 */
public class MainWindowViewModel {

    private Expenses expenses;
    private UserInfo userInfo;
    private BudgetStats budgetStats;
    
    /**
     * Constructor for the main window view model.
     * controls the expenses, user info, and budget stats.
     */
    public MainWindowViewModel() {
        this.expenses = new Expenses();
        this.userInfo = new UserInfo();
        this.budgetStats = new BudgetStats();

        this.bindMonthlyIncomes();
        this.bindExpenses();
    }

    /**
     * Calling this saves the current expenses and monthly income
     * in a file called budget.dat.
     */
    public void saveBudget() throws IOException {
        BudgetDataManager.saveBudgetData("budget.dat", this.expenses, this.userInfo);
    }

    /**
     * Calling this loads the budget from the
     * "budget.dat" file if it exists. If it does not, the
     * user will be notified.
     */
    public void loadBudget() throws IOException, ClassNotFoundException {
        BudgetDataManager.loadBudgetData("budget.dat", this.expenses, this.userInfo);
    }

    private void bindMonthlyIncomes() {
        this.budgetStats.bindMonthlyIncome(this.userInfo.getMonthlyIncome());
    }

    private void bindExpenses() {
        this.budgetStats.bindExpenses(this.expenses.getExpenses());
    }

    public ListProperty<Expense> getExpenses() {
        return this.expenses.getExpenses();
    }

    public void setMonthlyIncome(float income) {
        this.userInfo.setMonthlyIncome(income);
    }

    public float getCurrentMonthlyIncome() {
        return this.userInfo.getMonthlyIncome().getValue();
    }

    /**
     * Allows the caller to add an expense to the 
     * observable list of expenses.
     * @param expense the new expense to add.
     */
    public void addExpense(Expense expense) {
        this.expenses.addExpense(expense);
    }

    /**
     * Allows the caller to remove an expense,
     * if it exists, from the observable expense list
     * @param expense the expense to remvoe
     */
    public void removeExpense(Expense expense) {
        this.expenses.removeExpense(expense);
    }

    public StringBinding getSummary() {
        return this.budgetStats.getSummary();
    }

}