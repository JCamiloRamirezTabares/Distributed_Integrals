/*

package server;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.jdbc.JDBCXYDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

public class XYGraphic extends JFrame{
    private final String PATH_GRAPHICS = "../../../docs";

    private String title;
    private XYSeries data;

    public void setTitle(String title){
        this.title = title;
        data = new XYSeries("ABC");
    }

    public void addPoint(double x, double y){
       data.add(x, y);
    }


    public void print(){
    
        JFreeChart chart = ChartFactory.createXYLineChart(



        )


        

        File directory = new File(PATH_GRAPHICS);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        save(new File(directory, title+".png"), chart);
    }

    private void save(File file, JFreeChart chart){
        try {
            ChartUtils.saveChartAsPNG(file, chart, 800, 600);
        } catch (IOException e) {
            System.err.println("Error al guardar el gráfico como PNG: " + e.getMessage());
        }
    }
}

 */