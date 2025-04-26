package edu.westga.comp4420.budget_software.model;

import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.FloatBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * The class that holds the calculations for budget statistics.
 * The stats have bindings that allow for calculations to be
 * bound so when a value updates, the calculation is automatically
 * updated.
 * 
 * @author Nick
 * @version Spring 2025
 */
public class BudgetStats {
    private FloatProperty monthlyIncome;
    private FloatBinding totalMonthlyExpenses;
    private FloatBinding discretionaryIncome;
    private ListProperty<Expense> expenses;
    private FloatBinding daysTillMillion;
    private FloatProperty monthlyGoal;
    private IntegerBinding budgetStatus;

    private StringBinding budgetStatsSummary;

    /**
     * Constructor for budget stats.
     * Sets up the bindings for calculations
     */
    public BudgetStats() {
        this.monthlyIncome = new SimpleFloatProperty();
        this.monthlyGoal = new SimpleFloatProperty();

        this.expenses = new SimpleListProperty<Expense>(FXCollections.observableArrayList(new ArrayList<Expense>()));
        this.createMonthlyExpenseBinding();
        this.createDiscretionaryIncomeBinding();
        this.createSummaryBinding();
        this.createMillionBinding();
        this.createBudgetStatusBinding();
    }

    private void createDiscretionaryIncomeBinding() {
        this.discretionaryIncome = Bindings.createFloatBinding(() -> {
            float discretionaryIncome;
            discretionaryIncome = this.monthlyIncome.getValue() - this.totalMonthlyExpenses.getValue();
            return discretionaryIncome;
        },
        this.monthlyIncome, this.totalMonthlyExpenses);
    }

    private void createMillionBinding() {
        this.daysTillMillion = Bindings.createFloatBinding(() -> {
            if (this.discretionaryIncome.getValue() == 0) {
                return 0f;
            }
            float million = 1000000;
            float averageDaysInMonth = 30.4f;
            float days = million / (this.discretionaryIncome.getValue() / averageDaysInMonth);
            return days;
        }, this.discretionaryIncome);
    }
    
    private void createSummaryBinding() {
        this.budgetStatsSummary = Bindings.createStringBinding(() -> {
            String summary = "";
            summary += "Monthly Income: $" + this.monthlyIncome.getValue() + "\n\n";
            summary += "Total Monthly Expenses: $" + this.totalMonthlyExpenses.getValue() + "\n\n";
            summary += "Monthly Savings Goal: $" + this.monthlyGoal.getValue() + "\n\n";
            summary += "Discretionary Income: $" + this.discretionaryIncome.getValue() + "\n\n";
            summary += "Number of days it would take to become a millionaire on this budget: " + this.daysTillMillion.intValue() + "\n\n";
            return summary;
        },
        this.totalMonthlyExpenses, this.monthlyIncome, this.discretionaryIncome, this.monthlyGoal);
    }

    private void createBudgetStatusBinding() {
        this.budgetStatus = Bindings.createIntegerBinding(() -> {
            if (this.discretionaryIncome.getValue() < 250) {
                return 3;
            } else if (this.discretionaryIncome.getValue() >= 250 && this.discretionaryIncome.getValue() <= 500) {
                return 2;
            } else {
                return 1;
            }
        },
        this.discretionaryIncome);
    }

    public IntegerBinding getBudgetStatus() {
        return this.budgetStatus;
    }

    private void createMonthlyExpenseBinding() {
        this.totalMonthlyExpenses = Bindings.createFloatBinding(() -> {
            float totalExpenses = 0;
            for (Expense expense : this.expenses) {
                totalExpenses += expense.getAmount();
            }
            return totalExpenses;
        },
        this.expenses);
    }

    /**
     * Allows the view model to bind the expenses list to the
     * expenses lists in this class.
     * @param expenses the expense list from the view model's Expenses class
     */
    public void bindExpenses(ListProperty<Expense> expenses) {
        this.expenses.bind(expenses);
    }

    /**
     * Allows the view model to bind the monthly income from
     * the view model's UserInfo class.
     * @param monthlyIncome the monthly income from the view model's UserInfo class.
     */
    public void bindMonthlyIncome(FloatProperty monthlyIncome) {
        this.monthlyIncome.bind(monthlyIncome);
    }

    public void bindMonthlyGoal(FloatProperty monthlyGoal) {
        this.monthlyGoal.bind(monthlyGoal);
    }

    /**
     * Gets the summary text for the user's budget.
     * Their monthly income and the current stats provided.
     * @return returns the summary of budget statistics.
     */
    public StringBinding getSummary() {
        return this.budgetStatsSummary;
    }


}
