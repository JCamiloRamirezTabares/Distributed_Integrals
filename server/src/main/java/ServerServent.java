import com.zeroc.Ice.Current;

import AppInterfaces.BrokerPrx;
import AppInterfaces.Integral;
import AppInterfaces.Server;
import model.MonteCarlo;

//Porfa toda la logica que sea aca
//Fork Join
public class ServerServent implements Server {

    //Creas un ThreadPool (10 hilos)
    private double load;
    private BrokerPrx brokerPrx;

    public ServerServent(BrokerPrx brokerPrx){
        load = 0;
        this.brokerPrx = brokerPrx;
    }
    

    @Override
    public void solveIntegral(Integral integral, Current current) {
        //Convertir integral de ice a Integral del modelo
        //Crear 10 nuevas integrales (del modelo) a partir de la que se recibe
        //Pasar al metodo monte carlo o Rieman la integral
        // Join de todas las areas
        //Convertir el BigDecimal en String y retornarlo
    }

    @Override
    public void printResponse(String res, Current current) {
        System.out.println(res);
    }

    @Override
    public double getLoad(Current current) {
        return load;
    }
    
}
