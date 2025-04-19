package edu.westga.comp4420.budget_software.view.codebehind;

import java.util.EnumMap;
import java.util.Map;

import edu.westga.comp4420.budget_software.model.Category;
import edu.westga.comp4420.budget_software.model.Expense;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

/**
 * The class that handles displaying the user's expenses as a graph.
 * Each category gets its own slice of the pie(chart).
 *
 * @author  Nick Watts
 * @version Spring 2025
 */
public class Graph {

    @FXML
    private PieChart pieBudget;
    
    /**
     * Pass the expenses in here.
     * @param expenses  The observable list of expenses
     */
    public void setExpenses(ObservableList<Expense> expenses) {
        this.rebuildChart(expenses);
        expenses.addListener((ListChangeListener<Expense>) change ->
            this.rebuildChart(change.getList())
        );
    }

    @FXML
    void initialize() {
        this.pieBudget.setLabelsVisible(true);
        this.pieBudget.setLegendVisible(true);
        this.pieBudget.setTitle("Expenses by Category");
    }

    private void rebuildChart(ObservableList<? extends Expense> expenses) {

        Map<Category, Double> sums = new EnumMap<>(Category.class);
        for (Expense exp : expenses) {
            sums.merge(exp.getCategory(), (double) exp.getAmount(), Double::sum);
        }

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(
            param -> new Observable[] { param.pieValueProperty() }
        );

        sums.forEach((category, total) ->
            chartData.add(new PieChart.Data(this.formatLabel(category, total), total))
        );

        this.pieBudget.setData(chartData);

        chartData.forEach(data -> {
            Tooltip tip = new Tooltip(String.format("%s : $%.2f",
                                                    data.getName(), data.getPieValue()));
            tip.setShowDelay(Duration.millis(100));
            Tooltip.install(data.getNode(), tip);
        });
    }

    private String formatLabel(Category category, double total) {
        return String.format("%s ($%.2f)", category, total);
    }
}
