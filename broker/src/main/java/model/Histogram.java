package server;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

/*
    Esta clase es para graficar un histograma de las areas que se van calculando
 */
public class Histogram {
    private final String PATH_GRAPHICS = "../../../docs";

    private String title;
    private double[] data;

    public void setTitle(String title){
        this.title = title;
    }

    public void setData(List<Double> data){
        this.data = data.stream()
                        .mapToDouble(Double::doubleValue)
                        .toArray();;
    }

    public void setData(double[] data){
        this.data = data;
    }


    public void print(){
        HistogramDataset dataset = new HistogramDataset();

        dataset.addSeries("Histograma", data, 10);
        
        JFreeChart chart = ChartFactory.createHistogram(
                "Histograma de "+title, // Título del gráfico
                "Valores", // Etiqueta del eje X
                "Frecuencia", // Etiqueta del eje Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                true, 
                true,
                false
        );

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
