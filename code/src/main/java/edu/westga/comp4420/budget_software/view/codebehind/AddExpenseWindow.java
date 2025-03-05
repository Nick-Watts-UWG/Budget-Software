package edu.westga.comp4420.budget_software.view.codebehind;

import edu.westga.comp4420.budget_software.model.Category;
import edu.westga.comp4420.budget_software.model.Expense;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


/**
 * CodeBehind To Handle Adding an Expense
 *
 * @author	Nick Watts
 * @version Spring 2025
 */
public class AddExpenseWindow {   


    @FXML
    private Button buttonAddExpense;

    @FXML
    private ComboBox<Category> comboBoxCategory;

    @FXML
    private TextField textFieldExpenseAmount;

    @FXML
    private TextField textFieldExpenseName;

    private Expense expense;

    @FXML
    void actionAddExpense(ActionEvent event) {

        try {
            Category category;
            String expenseName;
            float expenseAmount;

            if (this.comboBoxCategory.getValue() == null) {
                category = Category.MISC;
            } else {
                category = this.comboBoxCategory.getValue();
            }

            expenseName = this.textFieldExpenseName.getText().trim();
            expenseAmount = Float.parseFloat(this.textFieldExpenseAmount.getText().trim());

            this.expense = new Expense(expenseName, expenseAmount, category);

            Stage stage = (Stage) this.buttonAddExpense.getScene().getWindow();
            stage.close();
        } catch (Exception exception) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("You are entering invalid input.");
            alert.showAndWait();
        }
    }

    public Expense getCreatedExpense() {
        return this.expense;
    }


    @FXML
	void initialize() {
        this.comboBoxCategory.setItems(FXCollections.observableArrayList(Category.values()));
        this.setInputSanitation();
        this.setDisableProperty();
    }

    private void setDisableProperty() {
        this.buttonAddExpense.disableProperty().bind(
            this.textFieldExpenseName.textProperty().isEmpty()
            .or(this.textFieldExpenseAmount.textProperty().isEmpty())
        );
    }

    private void setInputSanitation() {
        this.textFieldExpenseAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                    if (!newValue.matches("\\d*(\\.\\d*)?")) {
                        AddExpenseWindow.this.textFieldExpenseAmount.setText(oldValue);
                }
            }
        });
    }
}
