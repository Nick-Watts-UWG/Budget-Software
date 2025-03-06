package edu.westga.comp4420.budget_software.test.model.Expenses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.comp4420.budget_software.model.Expenses;
import edu.westga.comp4420.budget_software.model.Expense;
import edu.westga.comp4420.budget_software.model.Category;
import javafx.collections.ObservableList;

/**
 * JUnit tests for the Expenses class.
 * @author Nick
 * @version Spring 2025
 */
class TestExpenses {

    private Expenses expenses;

    @BeforeEach
    void setUp() {
        this.expenses = new Expenses();
    }


    @Test
    void testConstructorInitializesEmptyList() {
        assertNotNull(this.expenses.getExpenses());
        assertTrue(this.expenses.getExpenses().isEmpty());
    }


    @Test
    void testAddExpenseAddsToList() {
        Expense expense = new Expense("Groceries", 50.0f);
        this.expenses.addExpense(expense);
        assertEquals(1, this.expenses.getExpenses().size());
        assertTrue(this.expenses.getExpenses().contains(expense));
    }

    @Test
    void testAddMultipleExpenses() {
        Expense expense1 = new Expense("Rent", 1000.0f, Category.HOUSING);
        Expense expense2 = new Expense("Internet", 60.0f, Category.UTILITIES);

        this.expenses.addExpense(expense1);
        this.expenses.addExpense(expense2);

        assertEquals(2, this.expenses.getExpenses().size());
        assertTrue(this.expenses.getExpenses().contains(expense1));
        assertTrue(this.expenses.getExpenses().contains(expense2));
    }


    @Test
    void testRemoveExpenseRemovesFromList() {
        Expense expense = new Expense("Netflix", 15.99f, Category.ENTERTAINMENT);
        this.expenses.addExpense(expense);
        this.expenses.removeExpense(expense);

        assertFalse(this.expenses.getExpenses().contains(expense));
        assertEquals(0, this.expenses.getExpenses().size());
    }

    @Test
    void testRemoveNonExistentExpenseDoesNotAffectList() {
        Expense expense1 = new Expense("Groceries", 75.0f);
        Expense expense2 = new Expense("Gym", 25.0f, Category.HEALTHCARE);

        this.expenses.addExpense(expense1);
        this.expenses.removeExpense(expense2);

        assertEquals(1, this.expenses.getExpenses().size());
        assertTrue(this.expenses.getExpenses().contains(expense1));
    }


    @Test
    void testGetExpensesReturnsCorrectObservableList() {
        ObservableList<Expense> expenseList = this.expenses.getExpenses();
        assertNotNull(expenseList);
        assertEquals(0, expenseList.size());

        Expense expense = new Expense("Car Insurance", 120.0f, Category.TRANSPORTATION);
        this.expenses.addExpense(expense);

        assertEquals(1, expenseList.size());
        assertTrue(expenseList.contains(expense));
    }
}
