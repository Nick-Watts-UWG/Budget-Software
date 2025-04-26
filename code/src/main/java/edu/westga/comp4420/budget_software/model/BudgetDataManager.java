package edu.westga.comp4420.budget_software.model;

import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(new BudgetData(
                new ArrayList<>(expenses.getExpenses().get()),
                userInfo.getMonthlyIncome().get()
            ));
        }

        byte[] plain = bos.toByteArray();

        StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
        encryptor.setPassword("1337");
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        byte[] cipher = encryptor.encrypt(plain);
        Files.write(Paths.get(filePath), cipher);
    }

    /**
     * Loads budget data from a file and sets the expenses and monthly income.
     *
     * @param filePath the file path from where the data will be loaded.
     * @param expenses the Expenses object to be updated.
     * @param userInfo the UserInfo object to be updated.
     */
    public static void loadBudgetData(String filePath, Expenses expenses, UserInfo userInfo) throws IOException, ClassNotFoundException {
        byte[] cipher = Files.readAllBytes(Paths.get(filePath));
        StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
        encryptor.setPassword("1337");
        encryptor.setAlgorithm("PBEWithMD5AndDES");

        byte[] plain = encryptor.decrypt(cipher);

        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(plain))) {
            BudgetData data = (BudgetData) ois.readObject();
            expenses.getExpenses().clear();
            expenses.getExpenses().addAll(data.getExpenseList());
            userInfo.setMonthlyIncome(data.getMonthlyIncome());
        }
    }
    
}
