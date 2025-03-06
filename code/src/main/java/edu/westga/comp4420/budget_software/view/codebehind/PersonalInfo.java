package edu.westga.comp4420.budget_software.view.codebehind;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * CodeBehind To Handle Processing for the PersonalInfo window.
 *
 * @author	Nick Watts
 * @version Spring 2024
 */
public class PersonalInfo {   

    @FXML
    private Button buttonSubmitPersonalInfo;

    @FXML
    private TextField textFieldMonthlyIncome;

    private float monthlyIncome = 0.0f;

    @FXML
	void initialize() {
        this.setInputSanitation();
	}

    public void setCurrentIncome(float currentIncome) {
        this.monthlyIncome = currentIncome;
    }




    @FXML
    void actionSubmitPersonalInfo(ActionEvent event) {
        String incomeText = this.textFieldMonthlyIncome.getText().trim();
        
        try {
            this.monthlyIncome = Float.parseFloat(incomeText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid input. Please enter a numeric value.");
            alert.showAndWait();
            return;
        }

        Stage stage = (Stage) this.buttonSubmitPersonalInfo.getScene().getWindow();
        stage.close();
    }

    private void setInputSanitation() {
        this.textFieldMonthlyIncome.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                    if (!newValue.matches("\\d*(\\.\\d*)?")) {
                        PersonalInfo.this.textFieldMonthlyIncome.setText(oldValue);
                }
            }
        });
    }

    public float getMonthlyIncome() {
        return this.monthlyIncome;
    }
}