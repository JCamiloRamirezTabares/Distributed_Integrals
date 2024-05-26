package model;

/*
    Esta clase define lo que es una integral (No tiene en cuenta su solucion)
 */
public class Integral {
    
    private long lowerRange;             // Rango inferior de integracion
    private long upperRange;             // Rango inferior de integracion
    private MathFunction function;       // La funcion a integrar


    public Integral(MathFunction function, long lowerRange, long upperRange){
        this.function = function;
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
    }


    //Getters y Setters
    public long getLowerRange(){
        return lowerRange;
    }

    public long getUpperRange(){
        return upperRange;
    }

    public MathFunction getFunction(){
        return function;
    }

}
