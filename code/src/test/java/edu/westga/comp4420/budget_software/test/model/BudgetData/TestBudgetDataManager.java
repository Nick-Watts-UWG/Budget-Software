package edu.westga.comp4420.budget_software.test.model.BudgetData;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.westga.comp4420.budget_software.model.BudgetDataManager;
import edu.westga.comp4420.budget_software.model.Category;
import edu.westga.comp4420.budget_software.model.Expense;
import edu.westga.comp4420.budget_software.model.Expenses;
import edu.westga.comp4420.budget_software.model.UserInfo;

/**
 * Tests for budget data manager
 */
class TestBudgetDataManager {


    @Test
    void saveAndLoadRoundTrip(@TempDir Path dir) throws Exception {
        Path file = dir.resolve("budget.dat");

        Expenses expenses = new Expenses();
        UserInfo info     = new UserInfo();
        expenses.addExpense(new Expense("Groceries", 100f, Category.FOOD));
        expenses.addExpense(new Expense("Rent", 1_200f, Category.HOUSING));
        info.setMonthlyIncome(3_000f);

        BudgetDataManager.saveBudgetData(file.toString(), expenses, info);

        expenses.getExpenses().clear();
        info.setMonthlyIncome(0f);

        BudgetDataManager.loadBudgetData(file.toString(), expenses, info);

        assertAll(
            () -> assertEquals(2, expenses.getExpenses().size()),
            () -> assertEquals(3_000f, info.getMonthlyIncome().get()),
            () -> assertEquals(List.of("Groceries", "Rent"),
                               expenses.getExpenses().stream()
                                       .map(Expense::toString)
                                       .map(s -> s.split("\\|")[0].trim())
                                       .toList())
        );
    }

    @Test
    void saveAndLoadHandlesZeroIncomeAndNoExpenses(@TempDir Path dir) throws Exception {
        Path file = dir.resolve("budget.dat");

        Expenses expenses = new Expenses();
        UserInfo  info    = new UserInfo();
        info.setMonthlyIncome(0f);

        BudgetDataManager.saveBudgetData(file.toString(), expenses, info);

        info.setMonthlyIncome(9_999f);
        BudgetDataManager.loadBudgetData(file.toString(), expenses, info);

        assertAll(
            () -> assertTrue(expenses.getExpenses().isEmpty()),
            () -> assertEquals(0f, info.getMonthlyIncome().get())
        );
    }


    @Test
    void persistedFileIsEncrypted(@TempDir Path dir) throws Exception {
        Path file = dir.resolve("cipher.dat");

        Expenses expenses = new Expenses();
        UserInfo info     = new UserInfo();
        expenses.addExpense(new Expense("SecretName", 42f, Category.MISC));
        info.setMonthlyIncome(1_234f);

        BudgetDataManager.saveBudgetData(file.toString(), expenses, info);

        String raw = Files.readString(file, StandardCharsets.ISO_8859_1);

        assertAll(
            () -> assertFalse(raw.contains("SecretName")),
            () -> assertFalse(raw.contains("1234")),
            () -> assertTrue(Files.size(file) > 0)
        );
    }
}
