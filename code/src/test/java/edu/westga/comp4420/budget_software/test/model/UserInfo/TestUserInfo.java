package edu.westga.comp4420.budget_software.test.model.UserInfo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.comp4420.budget_software.model.UserInfo;

/**
 * Tests for user info
 */
class TestUserInfo {

    private UserInfo userInfo;

    @BeforeEach
    void setUp() {
        this.userInfo = new UserInfo();
    }

    @Test
    void constructorInitialisesPropertiesAndSummary() {
        assertEquals(0f, this.userInfo.getMonthlyIncome().get());
        assertEquals(0f, this.userInfo.getMonthlySavingsGoal().get());

        String summary = this.userInfo.getUserInfoProperty().get();
        assertAll(
            () -> assertTrue(summary.contains("Monthly Income: 0.0")),
            () -> assertTrue(summary.contains("Monthly Savings Goal: 0.0"))
        );
    }

    @Test
    void setMonthlyIncomeUpdatesPropertyAndSummary() {
        this.userInfo.setMonthlyIncome(4_250.75f);

        assertEquals(4_250.75f, this.userInfo.getMonthlyIncome().get());
        assertTrue(this.userInfo.getUserInfoProperty().get()
                   .contains("Monthly Income: 4250.75"));
    }

    @Test
    void setMonthlySavingsGoalUpdatesPropertyAndSummary() {
        this.userInfo.setMonthlySavingsGoal(800f);

        assertEquals(800f, this.userInfo.getMonthlySavingsGoal().get());
        assertTrue(this.userInfo.getUserInfoProperty().get()
                   .contains("Monthly Savings Goal: 800.0"));
    }

    @Test
    void combinedIncomeAndGoalAppearInSummary() {
        this.userInfo.setMonthlyIncome(3_000f);
        this.userInfo.setMonthlySavingsGoal(600f);

        String summary = this.userInfo.getUserInfoProperty().get();
        assertAll(
            () -> assertTrue(summary.contains("Monthly Income: 3000.0")),
            () -> assertTrue(summary.contains("Monthly Savings Goal: 600.0"))
        );
    }
}
