package edu.westga.comp4420.budget_software.model;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.FloatBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class BudgetStats {
    private FloatProperty monthlyIncome;
    private FloatBinding totalMonthlyExpenses;
    private FloatBinding discretionaryIncome;
    private ListProperty<Expense> expenses;

    private StringBinding budgetStatsSummary;

    public BudgetStats() {
        this.monthlyIncome = new SimpleFloatProperty();
        
        this.expenses = new SimpleListProperty<Expense>(FXCollections.observableArrayList(new ArrayList<Expense>()));
        this.createMonthlyExpenseBinding();
        this.createSummaryBinding();
    }

    private void createDiscretionaryIncomeBinding() {
        this.discretionaryIncome = Bindings.createFloatBinding(() -> {
            float discretionaryIncome 
        },
        this.monthlyIncome, this.totalMonthlyExpenses);
    }

    private void createSummaryBinding() {
        this.budgetStatsSummary = Bindings.createStringBinding(() -> {
            String summary = "";
            summary += "Monthly Income: " + this.monthlyIncome.getValue() + "\n";
            summary += "Total Monthly Expenses: " + this.totalMonthlyExpenses.getValue() + "\n";
            //summary += "Discretionary Income" + this.discretionaryIncome.getValue() + "\n";
            return summary;
        },
        this.totalMonthlyExpenses, this.monthlyIncome);
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

    public void bindExpenses(ListProperty<Expense> expenses) {
        this.expenses.bind(expenses);
    }

    public void bindMonthlyIncome(FloatProperty monthlyIncome) {
        this.monthlyIncome.bind(monthlyIncome);
    }

    public StringBinding getSummary() {
        return this.budgetStatsSummary;
    }


}
