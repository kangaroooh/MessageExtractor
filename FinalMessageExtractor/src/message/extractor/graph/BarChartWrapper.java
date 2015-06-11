/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.extractor.graph;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class BarChartWrapper {

    public static void display(ObservableList<String> selectedKeys, ArrayList<String> selectedCode, String title, GraphData graphData) {

        ArrayList<XYChart.Series> allSeries = new ArrayList<>();

        // Create x and y axis
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final BarChart barChart = new BarChart(xAxis, yAxis);

        xAxis.setLabel("Person");
        yAxis.setLabel("Value");

        // Setup x y axis and barChart
        barChart.setTitle(title);
        barChart.setCategoryGap(50);

        // Setup Series
        for (String currentCode : selectedCode) {
            XYChart.Series series = new XYChart.Series();
            series.setName(currentCode);

            for (String currentPerson : selectedKeys) {
                XYChart.Data data = new XYChart.Data(currentPerson, graphData.getValueFromKeyAndCode(currentPerson, currentCode));

                // Add prepared data into series
                series.getData().add(data);
            }

            // Add each series to allSeries
            allSeries.add(series);
        }

        // Add data into chart
        barChart.getData().addAll(allSeries);

        Stage stage = new Stage();
        Scene scene = new Scene(barChart, 800, 480);

        stage.setScene(scene);
        stage.show();

    }

}
