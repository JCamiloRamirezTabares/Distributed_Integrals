package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import model.ModelIntegral;

/*
    Definicion del metodo monte carlo
    Nota: Esta clase esta definida solamente para ser usada en el monolito.
    Tan pronto se quiera distribuir, esta clase junto a la de RandomNumber debe ir en el src del 'Server'
 */
public class MonteCarlo {
    
    private BigInteger N_POINTS;

    public MonteCarlo(){
        N_POINTS =  new BigInteger("100000");
    }

    public MonteCarlo(BigInteger points){
        N_POINTS = points;
    }
    

    public BigDecimal solve(ModelIntegral integral){
        double a = integral.getLowerRange();
        double b = integral.getUpperRange();

        RandomNumber random = new RandomNumber(a, b);

        BigDecimal sum = BigDecimal.ZERO;

        BigInteger index = BigInteger.ZERO;

        while(N_POINTS.compareTo(index) == 1){
            double randomNumber = random.get();
            sum = sum.add(BigDecimal.valueOf(integral.getFunction().solve(randomNumber)));


            index = index.add(BigInteger.ONE);
        }

        return (BigDecimal.valueOf(b-a).divide(new BigDecimal(N_POINTS))).multiply(sum);
    }

    public void saveResultsToFile(String filename, ModelIntegral integral, BigDecimal result) {
        try {
            // Asegurarse de que el directorio 'docs' exista
            File directory = new File("docs");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Crear el archivo en el directorio 'docs'
            File file = new File(directory, filename);
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                writer.printf("%s, es aproximadamente: %s%n",
                        integral.toString(), result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}