import model.Integral;
import ui.UIHandler;

/*
    Tareas por hacer:
    Maneje las funciones: str -> Function                                   ==> done
    Guardar informacion (Fucncion, limites de integracion)                  ==> done
    Funcion que se encargue de pedir y guardar limites de integracion       ==> done
    Implementar metricas para el cliente
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

            if(!input.equalsIgnoreCase("exit")){
                lowerRange = handlerUI.lowerRangeMenu();
                upperRange = handlerUI.upperRangeMenu();


                Integral integral = buildIntegral(input, lowerRange, upperRange);

                // Desde aqui
                MonteCarlo monteCarlo = new MonteCarlo();

                double res = monteCarlo.solve(integral);
                System.out.println(""+
                    "|| La integral "+integral.toString()+" es aproximadamente: " + res
                +"");

                String filename = "resultados_integrales.txt";
                monteCarlo.saveResultsToFile(filename, integral, res);
                
                //Hasta aqui, es un set de codigo que debe ser cambiado para distribuido


                /* 
                // Probando como funciona el exp4j
                try{
                    Integral integral = buildIntegral(input, lowerRange, upperRange);
                    System.out.println(integral.getFunction().solve(6)); //
                } catch(UnknownFunctionOrVariableException e){
                    System.out.println("|| Ha ocurrido un error, porfavor intentelo de nuevo");
                    System.err.println("|| Asegurate de ingresar una funcion matematica valida");
                }
                */
            } else{
                sentinel = false;
                handlerUI.byebye();
            }
        }

    }

    /*
        Initializa lo necesario para que trabaje el cliente (Es como el constructor)
     */
    private static void initialize(){
        handlerUI = new UIHandler();
    }

    /*
        Se encarga de construir la integral
     */
    private static Integral buildIntegral(String funct, String lwrRange, String upprRange){

        String function = funct;
        Double lowerRange = Double.parseDouble(lwrRange);
        Double upperRange = Double.parseDouble(upprRange);


        return new Integral(function, lowerRange, upperRange);
    }
    
    



}
