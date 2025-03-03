package edu.westga.comp4420.budget_software.view.codebehind;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;


/**
 * CodeBehind To Handle Processing for the MainWindow
 *
 * @author	Comp 4420
 * @version Spring 2024
 */
public class MainWindow {   
    @FXML
    private Button buttonAddExpense;

    @FXML
    private Button buttonPersonalInfo;

    @FXML
    private Button buttonSaveBudget;

    @FXML
    private ListView<?> listviewExpenses;

    @FXML
    private TextArea textAreaStats;
}
