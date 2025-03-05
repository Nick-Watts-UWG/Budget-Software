package edu.westga.comp4420.budget_software.view.codebehind;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.westga.comp4420.budget_software.Main;
import edu.westga.comp4420.budget_software.model.Expense;
import edu.westga.comp4420.budget_software.view_model.MainWindowViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


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
    private Button buttonRemoveExpense;

    @FXML
    private Button buttonPersonalInfo;

    @FXML
    private Button buttonSaveBudget;

    @FXML
    private ListView<Expense> listviewExpenses;

    @FXML
    private TextArea textAreaStats;

    private MainWindowViewModel viewModel;

    @FXML
	void initialize() {
        this.listviewExpenses.itemsProperty().bind(this.viewModel.getExpenses());
        this.bindPersonalInfoButton();
        this.textAreaStats.textProperty().bind(this.viewModel.getUserInfoSummary());
	}

    public MainWindow() {
        this.viewModel = new MainWindowViewModel();
    }

    private void bindPersonalInfoButton() {
        this.buttonPersonalInfo.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource(Main.PERSONAL_INFO_RESOURCE));
                loader.load();
                Parent parent = loader.getRoot();
                Scene scene = new Scene(parent);
                Stage personalInfoStage = new Stage();
                personalInfoStage.setTitle(Main.WINDOW_TITLE_PERSONAL_INFO);
                personalInfoStage.setScene(scene);
                personalInfoStage.initModality(Modality.WINDOW_MODAL);
                PersonalInfo controller = (PersonalInfo) loader.getController();
                personalInfoStage.showAndWait();
                
                float income = controller.getMonthlyIncome();
                this.viewModel.setMonthlyIncome(income);

            }  catch (FileNotFoundException exception) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("The FXML resource for the personal info screen was not found. Contact the developer");
                alert.showAndWait();
            } catch (IOException exception) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Failed to load the personal info window. Contact the developer.");
                alert.showAndWait();
                System.out.println(exception.getMessage());
            }
        });
    }

}
