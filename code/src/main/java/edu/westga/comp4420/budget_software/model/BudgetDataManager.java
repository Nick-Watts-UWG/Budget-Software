package edu.westga.comp4420.budget_software.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that handles serialization and File IO for the
 * budget manager.
 * 
 * @author Nick
 * @version Spring 2025
 */
public class BudgetDataManager {

    private static class BudgetData implements Serializable {
        private static final long serialVersionUID = 1L;
        private List<Expense> expenseList;
        private float monthlyIncome;

        BudgetData(List<Expense> expenseList, float monthlyIncome) {
            this.expenseList = expenseList;
            this.monthlyIncome = monthlyIncome;
        }

        public List<Expense> getExpenseList() {
            return this.expenseList;
        }

        public float getMonthlyIncome() {
            return this.monthlyIncome;
        }
    }

    /**
     * Saves the expenses and monthly income to a file
     *
     * @param filePath the file path where the data will be saved
     * @param expenses the Expenses object containing the expenses list
     * @param userInfo the UserInfo object containing the monthly income
     */
    public static void saveBudgetData(String filePath, Expenses expenses, UserInfo userInfo) throws IOException {
        List<Expense> expenseList = new ArrayList<>(expenses.getExpenses().get());
        float monthlyIncome = userInfo.getMonthlyIncome().get();
        
        BudgetData data = new BudgetData(expenseList, monthlyIncome);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
        out.writeObject(data);
        
    }

    /**
     * Loads budget data from a file and sets the expenses and monthly income.
     *
     * @param filePath the file path from where the data will be loaded.
     * @param expenses the Expenses object to be updated.
     * @param userInfo the UserInfo object to be updated.
     */
    public static void loadBudgetData(String filePath, Expenses expenses, UserInfo userInfo) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
        BudgetData data = (BudgetData) in.readObject();
        expenses.getExpenses().clear();
        expenses.getExpenses().addAll(data.getExpenseList());
        userInfo.setMonthlyIncome(data.getMonthlyIncome());
    }
    
}
