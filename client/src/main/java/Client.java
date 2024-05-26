import model.Integral;
import model.MathFunction;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
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

        MathFunction function = buildFunction(funct);
        Long lowerRange = Long.parseLong(lwrRange);
        Long upperRange = Long.parseLong(upprRange);


        return new Integral(function, lowerRange, upperRange);
    }
    
    /*
        Construye el cuerpo de la integral (La funcion como tal, sin rangos de integracion)
     */
   private static MathFunction buildFunction(String function){
        return (double x) -> {
          Expression f = new ExpressionBuilder(function)
                .variables("x")
                .build()
                .setVariable("x", x);

            return f.evaluate();
        };
   }



}
