import java.math.BigDecimal;
import java.math.BigInteger;

import model.Integral;
import server.MonteCarlo;
import server.Riemann;
import ui.UIHandler;

/*
    Tareas por hacer:
    Maneje las funciones: str -> Function                                   ==> done
    Guardar información (Función, límites de integración)                   ==> done
    Función que se encargue de pedir y guardar límites de integración       ==> done
    Implementar métricas para el cliente                                    ==> done
 */

public class Client {

    /*
        Esta es la controladora de la UI por consola
     */
    private static UIHandler handlerUI;

    public static void main(String[] args) {
        initialize();
        
        if(args.length == 0){
            start();
        } else{
            if(args[0].equalsIgnoreCase("test") && args.length == 6){
                testMode(args);
            } else{
                System.out.println("Otro modo");
            }   
        }
    }

    /*
        Inicializa lo necesario para que trabaje el cliente (Es como el constructor)
     */
    private static void initialize() {
        handlerUI = new UIHandler();
    }

    /*
        Inicializa modo consola normal (Es el modo para ser usado por el usuario final)
     */
    private static void start(){
        boolean sentinel = true;

        while (sentinel) {
            String input = handlerUI.functionMenu();
            String lowerRange = "";
            String upperRange = "";

            if (!input.equalsIgnoreCase("exit")) {
                boolean validRanges = false;

                while (!validRanges) {
                    lowerRange = handlerUI.lowerRangeMenu();
                    upperRange = handlerUI.upperRangeMenu();

                    try {
                        double lower = Double.parseDouble(lowerRange);
                        double upper = Double.parseDouble(upperRange);

                        if (lower >= upper) {
                            System.out.println("|| El limite inferior debe ser menor que el limite superior. Intentelo de nuevo.");
                        } else {
                            validRanges = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("|| Por favor, introduzca un número válido.");
                    }
                }

                Integral integral = buildIntegral(input, lowerRange, upperRange);

                // Desde aquí
                MonteCarlo monteCarlo = new MonteCarlo();

                long startTime = System.nanoTime();
                BigDecimal res = monteCarlo.solve(integral);
                long endTime = System.nanoTime();
                long executionTime = endTime - startTime;
                double executionTimeInSeconds = executionTime / 1_000_000_000.0;

                System.out.println(""+
                    "||\n"+
                    "|| La integral " + integral.toString() + " es aproximadamente: " + res
                  + "||\n"
                + "");
                System.out.println("|| Latencia: " + executionTimeInSeconds + " segundos");

                double throughput = 1.0 / executionTimeInSeconds;
                System.out.println("|| Throughput: " + throughput + " integrales por segundo");
                
                String filename = "resultados_integrales.txt";
                monteCarlo.saveResultsToFile(filename, integral, res);

                // Hasta aquí, es un set de código que debe ser cambiado para distribuido

            } else {
                sentinel = false;
                handlerUI.byebye();
            }
        }
    }

    /*
        Este es el modo para hacer pruebas directamente con los scripts
        Lee las instrucciones del script y ejecuta el proceso
     */
    private static void testMode(String[] args){
        handlerUI.testing();

        if(args[1].equals("1")){
            BigInteger points = new BigInteger(args[2]);
            MonteCarlo monteCarlo = new MonteCarlo(points);
            Integral integral = new Integral(
                args[3], 
                Double.parseDouble(args[4]), 
                Double.parseDouble(args[5])
            );

            long startTime = System.nanoTime();

            BigDecimal res = monteCarlo.solve(integral);
            
            long endTime = System.nanoTime();
            long executionTime = endTime - startTime;
            double executionTimeInSeconds = executionTime / 1_000_000_000.0;

            System.out.println("|| " + res.doubleValue());
            System.out.println("|| Latencia: " + executionTimeInSeconds + " segundos");

        } else{
            BigInteger partitions = new BigInteger(args[2]);
            Riemann riemannSum = new Riemann(partitions);
            Integral integral = new Integral(
                args[3], 
                Double.parseDouble(args[4]), 
                Double.parseDouble(args[5])
            );

            long startTime = System.nanoTime();

            BigDecimal res = riemannSum.solve(integral);

            long endTime = System.nanoTime();
            long executionTime = endTime - startTime;
            double executionTimeInSeconds = executionTime / 1_000_000_000.0;
            
            System.out.println("|| " + res.doubleValue());
            System.out.println("|| Latencia: " + executionTimeInSeconds + " segundos");
        }

        handlerUI.testFinished();
    }

    /*
        Se encarga de construir la integral
     */
    private static Integral buildIntegral(String funct, String lwrRange, String upprRange) {
        String function = funct;
        Double lowerRange = Double.parseDouble(lwrRange);
        Double upperRange = Double.parseDouble(upprRange);

        return new Integral(function, lowerRange, upperRange);
    }
}
