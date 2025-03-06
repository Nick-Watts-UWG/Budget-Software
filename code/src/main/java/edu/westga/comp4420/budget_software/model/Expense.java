package edu.westga.comp4420.budget_software.model;

import java.io.Serializable;


/**
 * The class that holds expense information.
 * 
 * @author Nick
 * @version Spring 2025
 */
public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String EXPENSE_TOSTRING_DIVIDER = "    |    ";
    private static final String ARGUMENT_NAME_CANNOT_BE_BLANK = "Argument: name cannot be blank";
    private static final String ARGUMENT_AMOUNT_CANNOT_BE_BLANK = "Argument: amount cannot be blank";
    private static final String ARGUMENT_CATEGORY_INVALID = "Argument: category invalid.";

    private String name;
    private float amount;
    private Category category;

    /**
     *  Constructor for the expense class that takes
     * a name and amount. Sets the category to misc.
     * @param name The name of the expense
     * @param amount The amount the expense costs
     */
    public Expense(String name, float amount) {
        this.checkConstructorInput(name, amount);
        this.name = name;
        this.amount = amount;
        this.category = Category.MISC;
    }

    /**
     * The expense constructor that allows setting a category
     * Takes the name, amount, and category.
     * @param name name of the expense
     * @param amount the amount the expense costs
     * @param category the category of the expense
     */
    public Expense(String name, float amount, Category category) {
        this.checkConstructorInput(name, amount, category);
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    private void checkConstructorInput(String name, float amount) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(ARGUMENT_NAME_CANNOT_BE_BLANK);
        } else if (amount == 0) {
            throw new IllegalArgumentException(ARGUMENT_AMOUNT_CANNOT_BE_BLANK);
        }
    }

    private void checkConstructorInput(String name, float amount, Category category) {
        boolean valid_enum = false;

        for (Category cat : Category.values()) {
            if (category == cat) {
                valid_enum = true;
            }
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException(ARGUMENT_NAME_CANNOT_BE_BLANK);
        } else if (amount == 0) {
            throw new IllegalArgumentException(ARGUMENT_AMOUNT_CANNOT_BE_BLANK);
        } else if (!valid_enum) {
            throw new IllegalArgumentException(ARGUMENT_CATEGORY_INVALID);
        }
    }

    /**
     * The over-ride of toString. This is used to display
     * the expense's information in the listview in mainwindow.
     */
    @Override
    public String toString() {
        String toString = "";
        toString += this.name + EXPENSE_TOSTRING_DIVIDER;
        toString += "$" + this.amount + EXPENSE_TOSTRING_DIVIDER;
        toString += this.category;

        return toString;
    }

    public float getAmount() {
        return this.amount;
    }
}
