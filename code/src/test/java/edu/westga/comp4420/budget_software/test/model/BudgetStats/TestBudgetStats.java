package edu.westga.comp4420.budget_software.test.model.BudgetStats;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.comp4420.budget_software.model.BudgetStats;
import edu.westga.comp4420.budget_software.model.Category;
import edu.westga.comp4420.budget_software.model.Expense;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * Tests for budget stats
 */
class TestBudgetStats {

    private BudgetStats stats;
    private FloatProperty income;
    private ListProperty<Expense> expenses;

    @BeforeEach
    void setUp() {
        this.stats    = new BudgetStats();
        this.income   = new SimpleFloatProperty();
        this.expenses = new SimpleListProperty<>(FXCollections.observableArrayList());

        this.stats.bindMonthlyIncome(this.income);
        this.stats.bindExpenses(this.expenses);
    }


    @Test
    void constructorSummaryShowsZeroes() {
        String summary = this.stats.getSummary().get();
        assertAll(
            () -> assertTrue(summary.contains("Monthly Income: $0.0")),
            () -> assertTrue(summary.contains("Total Monthly Expenses: $0.0")),
            () -> assertTrue(summary.contains("Discretionary Income: $0.0"))
        );
    }


    @Test
    void monthlyIncomeBindingReflectsExternalChanges() {
        income.set(2_000f);
        assertTrue(this.stats.getSummary().get().contains("Monthly Income: $2000.0"));
    }


    @Test
    void addingExpensesUpdatesTotalsAndDiscretionaryIncome() {
        income.set(1_000f);
        expenses.add(new Expense("Car", 300f, Category.TRANSPORTATION));
        expenses.add(new Expense("Ins", 100f, Category.TRANSPORTATION));

        String summary = this.stats.getSummary().get();
        assertAll(
            () -> assertTrue(summary.contains("Total Monthly Expenses: $400.0")),
            () -> assertTrue(summary.contains("Discretionary Income: $600.0"))
        );
    }

    @Test
    void discretionaryIncomeCanGoNegative() {
        income.set(500f);
        expenses.add(new Expense("Overspend", 600f, Category.MISC));

        assertTrue(this.stats.getSummary().get().contains("Discretionary Income: $-100.0"));
    }


    @Test
    void budgetStatusAcrossThresholds() {
        income.set(2_000f);
        assertEquals(1, this.stats.getBudgetStatus().get());

        expenses.add(new Expense("Rent", 1_700f, Category.HOUSING));
        assertEquals(2, this.stats.getBudgetStatus().get());

        expenses.add(new Expense("Food", 200f, Category.FOOD));
        assertEquals(3, this.stats.getBudgetStatus().get());
    }


    @Test
    void daysTillMillionaireCalculatedCorrectly() {
        income.set(3_000f);
        expenses.add(new Expense("Necessities", 1_000f, Category.MISC));

        double expected = 1_000_000d / (2_000d / 30.4d);
        int actual = extractDays(this.stats.getSummary().get());
        assertEquals((int) expected, actual, 1);    }

    @Test
    void millionaireTimerIsZeroWhenNoDiscretionaryIncome() {
        income.set(1_000f);
        expenses.add(new Expense("All", 1_000f, Category.MISC));

        assertEquals(0, extractDays(this.stats.getSummary().get()));
    }


    private static int extractDays(String summary) {
        Pattern p = Pattern.compile("millionaire on this budget: (\\d+)");
        Matcher m = p.matcher(summary);
        assertTrue(m.find(), "Day count not found in summary");
        return Integer.parseInt(m.group(1));
    }
}