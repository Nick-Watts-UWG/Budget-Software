package edu.westga.comp4420.budget_software.test.model.UserInfo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.comp4420.budget_software.model.UserInfo;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.StringProperty;

/**
 * JUnit tests for the UserInfo class.
 * @author Nick
 * @version Spring 2025
 */
class TestUserInfo {

    private UserInfo userInfo;

    @BeforeEach
    void setUp() {
        this.userInfo = new UserInfo();
    }


    @Test
    void testConstructorInitializesWithDefaultValues() {
        assertNotNull(this.userInfo.getMonthlyIncome());
        assertNotNull(this.userInfo.getUserInfoProperty());
        assertEquals(0.0f, this.userInfo.getMonthlyIncome().get());
        assertEquals("Monthly Income: 0.0", this.userInfo.getUserInfoProperty().get());
    }


    @Test
    void testGetMonthlyIncomeReturnsCorrectFloatProperty() {
        FloatProperty monthlyIncome = this.userInfo.getMonthlyIncome();
        assertNotNull(monthlyIncome);
        assertEquals(0.0f, monthlyIncome.get());
    }

    @Test
    void testGetUserInfoPropertyReturnsCorrectStringProperty() {
        StringProperty userInfoSummary = this.userInfo.getUserInfoProperty();
        assertNotNull(userInfoSummary);
        assertEquals("Monthly Income: 0.0", userInfoSummary.get());
    }


    @Test
    void testSetMonthlyIncomeUpdatesIncomeCorrectly() {
        this.userInfo.setMonthlyIncome(5000.0f);
        assertEquals(5000.0f, this.userInfo.getMonthlyIncome().get());
    }

    @Test
    void testSetMonthlyIncomeUpdatesUserInfoSummary() {
        this.userInfo.setMonthlyIncome(3200.5f);
        assertEquals("Monthly Income: 3200.5", this.userInfo.getUserInfoProperty().get());
    }

    @Test
    void testSetMonthlyIncomeWithZeroUpdatesCorrectly() {
        this.userInfo.setMonthlyIncome(0.0f);
        assertEquals("Monthly Income: 0.0", this.userInfo.getUserInfoProperty().get());
    }

    @Test
    void testSetMonthlyIncomeWithNegativeValueUpdatesCorrectly() {
        this.userInfo.setMonthlyIncome(-100.0f);
        assertEquals("Monthly Income: -100.0", this.userInfo.getUserInfoProperty().get());
    }
}