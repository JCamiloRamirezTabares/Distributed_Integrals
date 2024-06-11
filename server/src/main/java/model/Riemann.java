package model;

/*
  Definicion del metodo sumas de Riemann
  Nota: Esta clase esta definida solamente para ser usada en el monolito.
  Tan pronto se quiera distribuir, esta clase junto a la de RandomNumber debe ir en el src del 'Server'
 */

import java.math.BigDecimal;
import java.math.BigInteger;

public class Riemann implements IntegralSolver{

    private BigInteger partitions;

    public Riemann(){
        partitions = new BigInteger("1000000");
    }

    public Riemann(BigInteger partitions){
        this.partitions = partitions;
    }


    @Override
    public BigDecimal solve(Integral integral){

        double a = integral.getLowerRange();
        double b = integral.getUpperRange();
        BigDecimal deltaX = BigDecimal.valueOf(b-a).divide(new BigDecimal(partitions));

        BigInteger index = BigInteger.ONE;
        BigDecimal sum = BigDecimal.ZERO;

        while(partitions.compareTo(index) >= 0){

            
            BigDecimal fxi = BigDecimal.valueOf(integral.getFunction().solve(a + index.doubleValue()*deltaX.doubleValue()));

            sum = sum.add(fxi.multiply(deltaX));
            index = index.add(BigInteger.ONE);
        }

        return sum;
    }

}
