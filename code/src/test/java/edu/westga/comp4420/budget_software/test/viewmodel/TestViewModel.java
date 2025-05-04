package edu.westga.comp4420.budget_software.test.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.comp4420.budget_software.model.Category;
import edu.westga.comp4420.budget_software.model.Expense;
import edu.westga.comp4420.budget_software.view_model.MainWindowViewModel;

/**
 * Tests for main view model
 */
class TestMainWindowViewModel {

    private MainWindowViewModel vm;
    private static final File BUDGET_FILE = new File("budget.dat");

    @BeforeEach
    void setUp() {
        this.vm = new MainWindowViewModel();
        BUDGET_FILE.delete();
    }

    @AfterEach
    void tearDown() {
        BUDGET_FILE.delete();
    }


    @Test
    void initialStateIsEmpty() {
        assertEquals(0f, vm.getCurrentMonthlyIncome());
        assertTrue(vm.getExpenses().isEmpty());
    }

    @Test
    void settingMonthlyIncomeUpdatesProperty() {
        vm.setMonthlyIncome(2_500f);
        assertEquals(2_500f, vm.getCurrentMonthlyIncome());
    }


    @Test
    void addAndRemoveExpenseUpdatesList() {
        Expense exp = new Expense("Test", 100f, Category.FOOD);

        vm.addExpense(exp);
        assertEquals(1, vm.getExpenses().size());

        vm.removeExpense(exp);
        assertTrue(vm.getExpenses().isEmpty());
    }


    @Test
    void saveAndLoadBudgetRestoresState() throws Exception {
        vm.setMonthlyIncome(3_000f);
        Expense rent = new Expense("Rent", 1_200f, Category.HOUSING);
        vm.addExpense(rent);

        vm.saveBudget();
        assertTrue(BUDGET_FILE.exists());

        vm.setMonthlyIncome(0f);
        vm.removeExpense(rent);

        vm.loadBudget();

        assertAll(
            () -> assertEquals(3_000f, vm.getCurrentMonthlyIncome()),
            () -> assertEquals(1, vm.getExpenses().size())
        );
    }

    @Test
    void setMonthlySavingsGoalUpdatesSummary() {
        String before = vm.getSummary().get();
    
        vm.setMonthlySavingsGoal(750f);
    
        String after = vm.getSummary().get();
        assertNotEquals(before, after);
        assertTrue(after.contains("Monthly Savings Goal: $750.0"));
    }    


    @Test
    void summaryBindingUpdatesWhenIncomeChanges() {
        String before = vm.getSummary().get();

        vm.setMonthlyIncome(5_000f);

        assertNotEquals(before, vm.getSummary().get());
    }


    @Test
    void budgetStatusReflectsUnderlyingStats() {
        vm.setMonthlyIncome(1_000f);
        vm.addExpense(new Expense("Overspend", 950f, Category.MISC));
        assertEquals(3, vm.getBudgetStatus().get());

        vm.addExpense(new Expense("Bonus", -300f, Category.MISC));
        assertEquals(2, vm.getBudgetStatus().get());

        vm.setMonthlyIncome(5_000f);
        assertEquals(1, vm.getBudgetStatus().get());
    }
}
