package edu.westga.comp4420.budget_software.test.model.BudgetData;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.comp4420.budget_software.model.BudgetDataManager;
import edu.westga.comp4420.budget_software.model.Expenses;
import edu.westga.comp4420.budget_software.model.Expense;
import edu.westga.comp4420.budget_software.model.UserInfo;
import edu.westga.comp4420.budget_software.model.Category;

/**
 * JUnit tests for the BudgetDataManager class.
 * @author Nick
 * @version Spring 2025
 */
class TestBudgetDataManager {

    private static final String TEST_FILE_PATH = "test_budget_data.dat";
    private Expenses expenses;
    private UserInfo userInfo;

    @BeforeEach
    void setUp() {
        this.expenses = new Expenses();
        this.userInfo = new UserInfo();
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }


    @Test
    void testSaveAndLoadBudgetDataWithMultipleExpenses() {
        Expense expense1 = new Expense("Groceries", 100.0f, Category.FOOD);
        Expense expense2 = new Expense("Rent", 1200.0f, Category.HOUSING);
        this.expenses.addExpense(expense1);
        this.expenses.addExpense(expense2);
        this.userInfo.setMonthlyIncome(3000.0f);

        assertDoesNotThrow(() -> BudgetDataManager.saveBudgetData(TEST_FILE_PATH, this.expenses, this.userInfo));
        this.expenses.getExpenses().clear();
        this.userInfo.setMonthlyIncome(0);
        assertDoesNotThrow(() -> BudgetDataManager.loadBudgetData(TEST_FILE_PATH, this.expenses, this.userInfo));

        assertEquals(2, this.expenses.getExpenses().size());
        assertEquals(3000.0f, this.userInfo.getMonthlyIncome().get());
        assertTrue(this.expenses.getExpenses().get(0).getAmount() == expense1.getAmount());
        assertTrue(this.expenses.getExpenses().get(1).getAmount() == expense2.getAmount());
    }

    @Test
    void testSaveAndLoadBudgetDataWithNoExpenses() {
        this.userInfo.setMonthlyIncome(4500.0f);

        assertDoesNotThrow(() -> BudgetDataManager.saveBudgetData(TEST_FILE_PATH, this.expenses, this.userInfo));
        this.userInfo.setMonthlyIncome(0);
        assertDoesNotThrow(() -> BudgetDataManager.loadBudgetData(TEST_FILE_PATH, this.expenses, this.userInfo));

        assertEquals(0, this.expenses.getExpenses().size());
        assertEquals(4500.0f, this.userInfo.getMonthlyIncome().get());
    }

    @Test
    void testSaveAndLoadBudgetDataWithZeroIncome() {
        this.expenses.addExpense(new Expense("Phone Bill", 50.0f, Category.UTILITIES));
        this.userInfo.setMonthlyIncome(0.0f);

        assertDoesNotThrow(() -> BudgetDataManager.saveBudgetData(TEST_FILE_PATH, this.expenses, this.userInfo));
        this.expenses.getExpenses().clear();
        assertDoesNotThrow(() -> BudgetDataManager.loadBudgetData(TEST_FILE_PATH, this.expenses, this.userInfo));

        assertEquals(1, this.expenses.getExpenses().size());
        assertEquals(0.0f, this.userInfo.getMonthlyIncome().get());
    }

    @Test
    void testSaveBudgetDataDoesNotThrowException() {
        this.userInfo.setMonthlyIncome(1000.0f);
        this.expenses.addExpense(new Expense("Water Bill", 40.0f, Category.UTILITIES));

        assertDoesNotThrow(() -> BudgetDataManager.saveBudgetData(TEST_FILE_PATH, this.expenses, this.userInfo));
    }

    
}