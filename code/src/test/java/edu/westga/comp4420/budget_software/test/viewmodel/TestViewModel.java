package edu.westga.comp4420.budget_software.test.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.comp4420.budget_software.view_model.MainWindowViewModel;
import edu.westga.comp4420.budget_software.model.Expense;
import edu.westga.comp4420.budget_software.model.Category;

/**
 * JUnit tests for the MainWindowViewModel class.
 * @author Nick
 * @version Spring 2025
 */
class TestMainWindowViewModel {

    private MainWindowViewModel viewModel;
    private static final String BUDGET_FILE = "budget.dat";
    
    @BeforeEach
    void setUp() {
        viewModel = new MainWindowViewModel();
        File file = new File(BUDGET_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @AfterEach
    void tearDown() {
        File file = new File(BUDGET_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Test
    void testInitialState() {
        assertEquals(0.0f, viewModel.getCurrentMonthlyIncome(), "Initial monthly income should be 0.");
        assertTrue(viewModel.getExpenses().isEmpty(), "Initial expenses list should be empty.");
        
        assertNotNull(viewModel.getSummary(), "Summary binding should not be null.");
        assertNotNull(viewModel.getSummary().get(), "Summary string should not be null.");
    }
    
    @Test
    void testSetMonthlyIncome() {
        viewModel.setMonthlyIncome(2500.0f);
        assertEquals(2500.0f, viewModel.getCurrentMonthlyIncome(), "Monthly income should be updated to 2500.");
    }
    
    @Test
    void testAddAndRemoveExpense() {
        Expense expense = new Expense("Test Expense", 100.0f, Category.FOOD);
        viewModel.addExpense(expense);
        assertEquals(1, viewModel.getExpenses().size(), "Expense list should have one expense after adding.");
        
        viewModel.removeExpense(expense);
        assertTrue(viewModel.getExpenses().isEmpty(), "Expense list should be empty after removal");
    }
    
    @Test
    void testSaveAndLoadBudget() {
        viewModel.setMonthlyIncome(3000.0f);
        Expense expense = new Expense("Rent", 1200.0f, Category.HOUSING);
        viewModel.addExpense(expense);
        
        try {
            viewModel.saveBudget();
        } catch (IOException e) {
            fail("Saving budget should not throw an exception: " + e.getMessage());
        }
        
        viewModel.setMonthlyIncome(0.0f);
        viewModel.removeExpense(expense);
        assertEquals(0.0f, viewModel.getCurrentMonthlyIncome(), "Monthly income should be reset to 0.");
        assertTrue(viewModel.getExpenses().isEmpty(), "Expense list should be empty after removal.");
        
        try {
            viewModel.loadBudget();
        } catch (IOException | ClassNotFoundException e) {
            fail("Loading budget should not throw an exception: " + e.getMessage());
        }
        
        assertEquals(3000.0f, viewModel.getCurrentMonthlyIncome(), "Monthly income should be restored to 3000.");
        assertEquals(1, viewModel.getExpenses().size(), "Expense list should have one expense after loading.");
        Expense loadedExpense = viewModel.getExpenses().get(0);
        assertEquals(1200.0f, loadedExpense.getAmount(), "Expense amount should match.");
    }
    
    @Test
    void testSummaryBindingUpdates() {
        viewModel.setMonthlyIncome(4000.0f);
        String summaryBefore = viewModel.getSummary().get();
        
        viewModel.setMonthlyIncome(5000.0f);
        String summaryAfter = viewModel.getSummary().get();
        assertNotEquals(summaryBefore, summaryAfter, "Summary should update when monthly income changes.");
    }
}