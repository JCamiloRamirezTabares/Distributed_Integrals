import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.Integral;

/*
    Definicion del metodo monte carlo
    Nota: Esta clase esta definida solamente para ser usada en el monolito.
    Tan pronto se quiera distribuir, esta clase junto a la de RandomNumber debe ir en el src del 'Server'
 */
public class MonteCarlo {
    
    private final double N_POINTS = 10000;
    

    public double solve(Integral integral){
        double a = integral.getLowerRange();
        double b = integral.getUpperRange();

        RandomNumber random = new RandomNumber(a, b);

        double sum = 0;

        for(int i = 0; i < N_POINTS; i++){
            double randomNumber = random.get();
            sum += integral.getFunction().solve(randomNumber);
        }


        return (double) ((b-a)/N_POINTS)*sum;


    }

    public void saveResultsToFile(String filename, Integral integral, double result) {
        try {
            // Asegurarse de que el directorio 'docs' exista
            File directory = new File("docs");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Crear el archivo en el directorio 'docs'
            File file = new File(directory, filename);
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                writer.printf("%s, Resultado: %.6f%n",
                        integral.toString(), result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}