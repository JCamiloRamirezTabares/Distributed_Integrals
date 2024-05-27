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

}
