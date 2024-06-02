package server;
import java.util.Random;

/*
    Definicion del generador de numeros aleatorios
    Nota: Esta clase esta definida solamente para ser usada en el monolito.
    Tan pronto se quiera distribuir, esta clase junto a la de MonteCarlo debe ir en el src del 'Server'
 */
public class RandomNumber {

    private final Random random = new Random();
    private double lowerLimit;
    private double upperLimit;

    public RandomNumber(double lowerLimit, double upperLimit){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public double get(){
        return lowerLimit + (upperLimit - lowerLimit) * random.nextDouble();
    }

}
