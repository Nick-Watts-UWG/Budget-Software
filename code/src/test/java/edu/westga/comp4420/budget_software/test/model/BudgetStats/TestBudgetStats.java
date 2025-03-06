package edu.westga.comp4420.budget_software.test.model.BudgetStats;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.comp4420.budget_software.model.BudgetStats;
import edu.westga.comp4420.budget_software.model.Expense;
import edu.westga.comp4420.budget_software.model.Category;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * JUnit tests for the BudgetStats class.
 * @author Nick
 * @version Spring 2025
 */
class TestBudgetStats {

    private BudgetStats stats;

    @BeforeEach
    void setUp() {
        this.stats = new BudgetStats();
    }


    @Test
    void testConstructorInitialValues() {

        String summary = this.stats.getSummary().get();
        assertTrue(summary.contains("Monthly Income: $0.0"));
        assertTrue(summary.contains("Total Monthly Expenses: $0.0"));
        assertTrue(summary.contains("Discretionary Income: $0.0"));
    }


    @Test
    void testBindMonthlyIncomeReflectsChange() {
        FloatProperty externalIncome = new SimpleFloatProperty(0.0f);
        this.stats.bindMonthlyIncome(externalIncome);

        assertTrue(this.stats.getSummary().get().contains("Monthly Income: $0.0"));

        externalIncome.set(2000.0f);
        String summary = this.stats.getSummary().get();
        assertTrue(summary.contains("Monthly Income: $2000.0"));
    }


    @Test
    void testBindExpensesWithNoExpenses() {
        ListProperty<Expense> externalExpenses = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.stats.bindExpenses(externalExpenses);

        String summary = this.stats.getSummary().get();
        assertTrue(summary.contains("Total Monthly Expenses: $0.0"));
    }

    @Test
    void testBindExpensesWithOneExpense() {
        ListProperty<Expense> externalExpenses = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.stats.bindExpenses(externalExpenses);

        externalExpenses.add(new Expense("Groceries", 150.0f, Category.FOOD));
        String summary = this.stats.getSummary().get();
        assertTrue(summary.contains("Total Monthly Expenses: $150.0"));
    }

    @Test
    void testBindExpensesWithMultipleExpenses() {
        ListProperty<Expense> externalExpenses = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.stats.bindExpenses(externalExpenses);

        externalExpenses.add(new Expense("Rent", 1200.0f, Category.HOUSING));
        externalExpenses.add(new Expense("Utilities", 200.0f, Category.UTILITIES));
        externalExpenses.add(new Expense("Gym Membership", 50.0f, Category.HEALTHCARE));

        String summary = this.stats.getSummary().get();
        assertTrue(summary.contains("Total Monthly Expenses: $1450.0"));
    }


    @Test
    void testDiscretionaryIncomeCalculation() {
        FloatProperty externalIncome = new SimpleFloatProperty(1000.0f);
        ListProperty<Expense> externalExpenses = new SimpleListProperty<>(FXCollections.observableArrayList());

        this.stats.bindMonthlyIncome(externalIncome);
        this.stats.bindExpenses(externalExpenses);

        externalExpenses.add(new Expense("Car Payment", 300.0f, Category.TRANSPORTATION));
        externalExpenses.add(new Expense("Insurance", 100.0f, Category.TRANSPORTATION));

        String summary = this.stats.getSummary().get();
        assertTrue(summary.contains("Monthly Income: $1000.0"));
        assertTrue(summary.contains("Total Monthly Expenses: $400.0"));
        assertTrue(summary.contains("Discretionary Income: $600.0"));

        externalIncome.set(2000.0f);
        summary = this.stats.getSummary().get();
        assertTrue(summary.contains("Monthly Income: $2000.0"));
        assertTrue(summary.contains("Discretionary Income: $1600.0"));
    }

    @Test
    void testDiscretionaryIncomeGoesNegative() {
        FloatProperty externalIncome = new SimpleFloatProperty(500.0f);
        ListProperty<Expense> externalExpenses = new SimpleListProperty<>(FXCollections.observableArrayList());

        this.stats.bindMonthlyIncome(externalIncome);
        this.stats.bindExpenses(externalExpenses);

        externalExpenses.add(new Expense("Fancy Restaurant", 600.0f, Category.FOOD));

        String summary = this.stats.getSummary().get();
        assertTrue(summary.contains("Monthly Income: $500.0"));
        assertTrue(summary.contains("Total Monthly Expenses: $600.0"));
        assertTrue(summary.contains("Discretionary Income: $-100.0"));
    }


    @Test
    void testSummaryUpdatesAutomatically() {
        FloatProperty externalIncome = new SimpleFloatProperty(0.0f);
        ListProperty<Expense> externalExpenses = new SimpleListProperty<>(FXCollections.observableArrayList());

        this.stats.bindMonthlyIncome(externalIncome);
        this.stats.bindExpenses(externalExpenses);

        assertTrue(this.stats.getSummary().get().contains("Monthly Income: $0.0"));

        externalIncome.set(1200.0f);
        assertTrue(this.stats.getSummary().get().contains("Monthly Income: $1200.0"));

        externalExpenses.add(new Expense("Internet", 60.0f, Category.UTILITIES));
        assertTrue(this.stats.getSummary().get().contains("Total Monthly Expenses: $60.0"));

        assertTrue(this.stats.getSummary().get().contains("Discretionary Income: $1140.0"));
    }
}