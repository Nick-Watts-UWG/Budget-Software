package edu.westga.comp4420.budget_software.view_model;

import edu.westga.comp4420.budget_software.model.Expenses;
import edu.westga.comp4420.budget_software.model.UserInfo;
import edu.westga.comp4420.budget_software.model.BudgetStats;
import edu.westga.comp4420.budget_software.model.Expense;
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
    
    public MainWindowViewModel() {
        this.expenses = new Expenses();
        this.userInfo = new UserInfo();
        this.budgetStats = new BudgetStats();

        this.bindMonthlyIncomes();
        this.bindExpenses();
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

    public void addExpense(Expense expense) {
        this.expenses.addExpense(expense);
    }

    public void removeExpense(Expense expense) {
        this.expenses.removeExpense(expense);
    }

    public StringBinding getSummary() {
        return this.budgetStats.getSummary();
    }

}