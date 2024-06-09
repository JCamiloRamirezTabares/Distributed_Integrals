import java.util.concurrent.PriorityBlockingQueue;

import com.zeroc.Ice.Current;
import AppInterfaces.Broker;
import AppInterfaces.ClientPrx;
import AppInterfaces.Integral;
import AppInterfaces.ServerPrx;
import model.ServerLoadComparator;

public class BrokerServent implements Broker{

    private PriorityBlockingQueue<ServerPrx> servers;

    public BrokerServent(){
        servers = new PriorityBlockingQueue<>(10, new ServerLoadComparator());
    }

    @Override
    public void addServer(ServerPrx server, Current current) {
        servers.add(server);
        server.printResponse("Servidor registrado correctamente");
    }

    @Override
    public void solveIntegral(ClientPrx clientProxy, Integral integral, Current current) {
        System.out.println("|| Me han enviado una integral :3 "+integral.functionnString);

        if(!servers.isEmpty()){
            ServerPrx server = servers.poll();
            //String res = server.solveIntegral(integral);
            servers.add(server);

            //clientProxy.printResponse(integral.toString(), res);
            clientProxy.printResponse(integral.toString(), "Se resolvio la integral");
        } else{
            clientProxy.printResponse("", 
            "|| La integral no se pudo resolver\n"+
            "|| Razon: No hay servidores registrados"
            );
        }
    }
    
}
