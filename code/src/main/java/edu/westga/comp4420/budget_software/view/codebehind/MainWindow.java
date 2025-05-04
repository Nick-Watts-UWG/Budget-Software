package edu.westga.comp4420.budget_software.view.codebehind;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.westga.comp4420.budget_software.Main;
import edu.westga.comp4420.budget_software.model.Expense;
import edu.westga.comp4420.budget_software.view_model.MainWindowViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;


/**
 * CodeBehind To Handle Processing for the MainWindow
 *
 * @author Nick
 * @version Spring 2024
 */
public class MainWindow {   

    @FXML
    private Rectangle boxBudgetStatus;

    @FXML
    private Button buttonAddExpense;

    @FXML
    private Button buttonGraph;

    @FXML
    private Button buttonRemoveExpense;

    @FXML
    private Button buttonPersonalInfo;

    @FXML
    private Button buttonSaveBudget;

    @FXML
    private Button buttonLoadBudget;

    @FXML
    private ListView<Expense> listviewExpenses;

    @FXML
    private TextArea textAreaStats;

    private MainWindowViewModel viewModel;

    @FXML
	void initialize() {
        this.listviewExpenses.itemsProperty().bind(this.viewModel.getExpenses());
        this.bindPersonalInfoButton();
        this.bindAddExpenseButton();
        this.bindRemoveExpenseButton();
        this.textAreaStats.textProperty().bind(this.viewModel.getSummary());
        this.bindSaveBudgetButton();
        this.bindLoadBudgetButton();
	}

    /**
     * The constructor for the main window.
     * Sets the view model.
     */
    public MainWindow() {
        this.viewModel = new MainWindowViewModel();
    }

    private void bindSaveBudgetButton() {
        this.buttonSaveBudget.setOnAction(e -> {
            try {
                this.viewModel.saveBudget();
            } catch (Exception exception) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Error saving file! Contact Develper.");
                alert.showAndWait();
            }
        });
    }

    private void bindLoadBudgetButton() {
        this.buttonLoadBudget.setOnAction(e -> {
            try {
                this.viewModel.loadBudget();
                this.setBudgetStatusBoxColor();
            } catch (Exception exception) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Error loading file! Contact Develper.");
                alert.showAndWait();
            }
        });
    }

    private void bindAddExpenseButton() {
        this.buttonAddExpense.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource(Main.ADD_EXPENSE_RESOURCE));
                loader.load();
                Parent parent = loader.getRoot();
                Scene scene = new Scene(parent);
                Stage addExpenseStage = new Stage();
                addExpenseStage.setTitle(Main.WINDOW_TITLE_ADD_EXPENSE);
                addExpenseStage.setScene(scene);
                Stage primaryStage = (Stage) this.buttonAddExpense.getScene().getWindow();
                addExpenseStage.initOwner(primaryStage);
                addExpenseStage.initModality(Modality.WINDOW_MODAL);
                AddExpenseWindow controller = (AddExpenseWindow) loader.getController();
                addExpenseStage.showAndWait();
                
                Expense newExpense = controller.getCreatedExpense();
                if (newExpense != null) {
                    this.viewModel.addExpense(newExpense);
                    this.setBudgetStatusBoxColor();
                }
            }  catch (FileNotFoundException exception) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("The FXML resource for the add expense screen was not found. Contact the developer");
                alert.showAndWait();
            } catch (IOException exception) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Failed to load the add expense window. Contact the developer.");
                alert.showAndWait();
                System.out.println(exception.getMessage());
            }
        });
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
                Stage primaryStage = (Stage) this.buttonAddExpense.getScene().getWindow();
                personalInfoStage.initOwner(primaryStage);
                personalInfoStage.initModality(Modality.WINDOW_MODAL);
                PersonalInfo controller = (PersonalInfo) loader.getController();
                controller.setCurrentIncome(this.viewModel.getCurrentMonthlyIncome());
                personalInfoStage.showAndWait();
                float income = controller.getMonthlyIncome();
                float savingsGoal = controller.getMonthlySavingsGoal();
                this.viewModel.setMonthlyIncome(income);
                this.viewModel.setMonthlySavingsGoal(savingsGoal);
                this.setBudgetStatusBoxColor();

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

    private void bindRemoveExpenseButton() {
        this.buttonRemoveExpense.setOnAction(e -> {
            if (this.listviewExpenses.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("You need to select an expense to remove.");
                alert.showAndWait();
            } else {
                this.viewModel.removeExpense(this.listviewExpenses.getSelectionModel().getSelectedItem());
                this.setBudgetStatusBoxColor();
            }
        });
    }

    private void setBudgetStatusBoxColor() {
        int color = this.viewModel.getBudgetStatus().getValue();
        if (color == 3) {
            this.boxBudgetStatus.setFill(Color.RED);
        } else if (color == 2) {
            this.boxBudgetStatus.setFill(Color.YELLOW);
        } else if (color == 1) {
            this.boxBudgetStatus.setFill(Color.GREEN);
        } else {
            this.boxBudgetStatus.setFill(Color.CYAN);
        }
    }
    
    @FXML
    void actionViewGraph(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(Main.GRAPH_RESOURCE));
            loader.load();
            Parent parent = loader.getRoot();
            Scene scene = new Scene(parent);
            Stage graphStage = new Stage();
            graphStage.setTitle(Main.WINDOW_TITLE_GRAPH);
            graphStage.setScene(scene);
            graphStage.initModality(Modality.NONE);
            Graph controller = (Graph) loader.getController();
            controller.setExpenses(this.viewModel.getExpenses());  
            graphStage.showAndWait();
        }  catch (FileNotFoundException exception) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("The FXML resource for the add expense screen was not found. Contact the developer");
            alert.showAndWait();
        } catch (IOException exception) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Failed to load the add expense window. Contact the developer.");
            alert.showAndWait();
            System.out.println(exception.getMessage());
        }
    }

}
