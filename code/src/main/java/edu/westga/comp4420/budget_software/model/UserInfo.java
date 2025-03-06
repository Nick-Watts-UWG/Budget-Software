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
    private FloatProperty monthlyIncome;
    private StringProperty userInfoSummary;

    /**
     * Creates the object that holds the user's personal info.
     */
    public UserInfo() {
        this.monthlyIncome = new SimpleFloatProperty(0);
        this.userInfoSummary = new SimpleStringProperty();
        this.setUserInfoSummary();
    }


    public FloatProperty getMonthlyIncome() {
        return this.monthlyIncome;
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

        summary += "Monthly Income: " + this.monthlyIncome.getValue();
        this.userInfoSummary.set(summary);
    }
}
