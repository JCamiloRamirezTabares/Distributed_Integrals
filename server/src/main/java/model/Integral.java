package model;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/*
    Esta clase define lo que es una integral (No tiene en cuenta su solucion)
 */
public class Integral {
    
    private String functionString;         // Representacion en texto de la funcion
    private double lowerRange;              // Rango inferior de integracion
    private double upperRange;              // Rango inferior de integracion
    private MathFunction function;          // La funcion a integrar


    public Integral(String functionnString, double lowerRange, double upperRange){
        this.functionString = functionnString;
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;

        this.function = buildFunction(functionnString);
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


    //Getters y Setters
    public double getLowerRange(){
        return lowerRange;
    }

    public double getUpperRange(){
        return upperRange;
    }

    public MathFunction getFunction(){
        return function;
    }

    @Override
    public String toString(){
        return "| "+functionString + " | entre " + lowerRange + " y " + upperRange;
    }

}
