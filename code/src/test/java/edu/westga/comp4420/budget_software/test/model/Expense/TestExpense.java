package edu.westga.comp4420.budget_software.test.model.Expense;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.budget_software.model.Category;
import edu.westga.comp4420.budget_software.model.Expense;

/**
 * JUnit tests for the Expense class.
 * @author Nick
 * @version Spring 2025
 */
class TestExpense {


    @Test
    void testConstructorWithValidNameAndAmount() {
        Expense expense = new Expense("Groceries", 50.0f);
        assertEquals("Groceries", expense.toString().split("\\|")[0].trim());
        assertEquals("$50.0", expense.toString().split("\\|")[1].trim());
        assertEquals(Category.MISC.toString(), expense.toString().split("\\|")[2].trim());
    }

    @Test
    void testConstructorWithValidNameAmountAndCategory() {
        Expense expense = new Expense("Rent", 1000.0f, Category.HOUSING);
        assertEquals("Rent", expense.toString().split("\\|")[0].trim());
        assertEquals("$1000.0", expense.toString().split("\\|")[1].trim());
        assertEquals(Category.HOUSING.toString(), expense.toString().split("\\|")[2].trim());
    }

    @Test
    void testConstructorWithBlankNameThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Expense("", 50.0f);
        });
        assertEquals("Argument: name cannot be blank", thrown.getMessage());
    }

    @Test
    void testConstructorWithZeroAmountThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Expense("Movie Ticket", 0.0f);
        });
        assertEquals("Argument: amount cannot be blank", thrown.getMessage());
    }

    @Test
    void testConstructorWithBlankNameAndCategoryThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Expense("", 25.0f, Category.ENTERTAINMENT);
        });
        assertEquals("Argument: name cannot be blank", thrown.getMessage());
    }

    @Test
    void testConstructorWithZeroAmountAndCategoryThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Expense("Concert", 0.0f, Category.ENTERTAINMENT);
        });
        assertEquals("Argument: amount cannot be blank", thrown.getMessage());
    }

    @Test
    void testConstructorWithValidCategory() {
        Expense expense = new Expense("Gym Membership", 30.0f, Category.HEALTHCARE);
        assertEquals("Gym Membership", expense.toString().split("\\|")[0].trim());
        assertEquals("$30.0", expense.toString().split("\\|")[1].trim());
        assertEquals(Category.HEALTHCARE.toString(), expense.toString().split("\\|")[2].trim());
    }


    @Test
    void testGetAmountReturnsCorrectValue() {
        Expense expense = new Expense("Phone Bill", 60.5f);
        assertEquals(60.5f, expense.getAmount());
    }


    @Test
    void testToStringFormatsCorrectly() {
        Expense expense = new Expense("Netflix Subscription", 15.99f, Category.ENTERTAINMENT);
        String expected = "Netflix Subscription    |    $15.99    |    ENTERTAINMENT";
        assertEquals(expected, expense.toString());
    }

    @Test
    void testToStringFormatsCorrectlyForMiscCategory() {
        Expense expense = new Expense("Coffee", 5.0f);
        String expected = "Coffee    |    $5.0    |    MISC";
        assertEquals(expected, expense.toString());
    }
}
