package Financial_Manager;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Map;

public class ChartManager {
    private PieChart pieChart;
    private BarChart<String, Number> barChart;

    public void createPieChart(Map<String, Double> data) {
        pieChart = new PieChart();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieChart.setTitle("Expense Distribution");
    }

    public void createBarGraph(Map<String, Double> data) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Expenses by Category");
        xAxis.setLabel("Category");
        yAxis.setLabel("Amount");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        barChart.getData().add(series);
    }

    public void displayPieChart(Stage stage) {
        Scene scene = new Scene(pieChart, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Pie Chart");
        stage.show();
    }

    public void displayBarGraph(Stage stage) {
        Scene scene = new Scene(barChart, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Bar Chart");
        stage.show();
    }

    public void displayBothCharts(Stage stage) {
        VBox vbox = new VBox(pieChart, barChart);
        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Pie + Bar Charts");
        stage.show();
    }
}
