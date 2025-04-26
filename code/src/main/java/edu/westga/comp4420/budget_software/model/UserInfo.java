package edu.westga.comp4420.budget_software.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Contains the personal information of the user.
 * @author Nick
 * @version Spring 2025
 */
public class UserInfo {

    private static String spacing = "\n\n";

    private FloatProperty monthlyIncome;
    private StringProperty userInfoSummary;
    private FloatProperty monthlySavingsGoal;

    /**
     * Creates the object that holds the user's personal info.
     */
    public UserInfo() {
        this.monthlyIncome = new SimpleFloatProperty(0);
        this.userInfoSummary = new SimpleStringProperty();
        this.monthlySavingsGoal = new SimpleFloatProperty(0);
        this.setUserInfoSummary();
    }


    public FloatProperty getMonthlyIncome() {
        return this.monthlyIncome;
    }

    public FloatProperty getMonthlySavingsGoal() {
        return this.monthlySavingsGoal;
    }

    public void setMonthlySavingsGoal(float goal) {
        this.monthlySavingsGoal.setValue(goal);
        this.setUserInfoSummary();
    }

    public StringProperty getUserInfoProperty() {
        return this.userInfoSummary;
    }

    public void setMonthlyIncome(float income) {
        this.monthlyIncome.setValue(income);
        this.setUserInfoSummary();
    }
    

    private void setUserInfoSummary() {
        String summary = "";

        summary += "Monthly Income: " + this.monthlyIncome.getValue() + spacing;
        summary += "Monthly Savings Goal: " + this.monthlySavingsGoal.getValue() + spacing;
        this.userInfoSummary.set(summary);
    }
}
