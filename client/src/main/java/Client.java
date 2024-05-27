import model.Integral;
import ui.UIHandler;

/*
    Tareas por hacer:
    Maneje las funciones: str -> Function                                   ==> done
    Guardar información (Función, límites de integración)                  ==> done
    Función que se encargue de pedir y guardar límites de integración       ==> done
    Implementar métricas para el cliente
 */

public class Client {

    /*
        Esta es la controladora de la UI por consola
     */
    private static UIHandler handlerUI;

    public static void main(String[] args) {
        initialize();
        
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
                            System.out.println("El límite inferior debe ser menor que el límite superior. Inténtelo de nuevo.");
                        } else {
                            validRanges = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, introduzca un número válido.");
                    }
                }

                Integral integral = buildIntegral(input, lowerRange, upperRange);

                // Desde aquí
                MonteCarlo monteCarlo = new MonteCarlo();

                double res = monteCarlo.solve(integral);
                System.out.println(""+
                    "|| La integral " + integral.toString() + " es aproximadamente: " + res
                + "");
                
                // Guardar el resultado en un archivo .txt en la carpeta docs
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
        Inicializa lo necesario para que trabaje el cliente (Es como el constructor)
     */
    private static void initialize() {
        handlerUI = new UIHandler();
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
