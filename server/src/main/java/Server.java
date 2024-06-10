
 /*
    Implementar el monte carlo -> Build Random numbers gen, sum         ===> done
    Metricas por parte del cliente (Perfomance)
    Dejar persistencia de las respuestas (Dejar guardadas las respuestas por parte del servidor)
*/

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

import AppInterfaces.BrokerPrx;
import AppInterfaces.ServerPrx;

public class Server {

    private static BrokerPrx brokerPrx;
    private static ServerPrx serverPrx;

    public static void main(String[] args) {
        initialize(args);
    }

    private static void initialize(String[] args){
        try(Communicator communicator = Util.initialize(args, "config.server"))
        {
            createBrokerPrx(communicator);
            createServerPrx(communicator);

            brokerPrx.addServer(serverPrx);
            communicator.waitForShutdown();
        }
    }

    private static void createBrokerPrx(Communicator communicator){
        brokerPrx = BrokerPrx.checkedCast(
                communicator.propertyToProxy("Broker.Proxy"))
                            .ice_twoway()
                            .ice_secure(false);

        if(brokerPrx == null){
            throw new Error("Invalid proxy");
        }
    }

    private static void createServerPrx(Communicator communicator){

        ObjectAdapter adapter = communicator.createObjectAdapter("Server");
        Object servent = new ServerServant();
        adapter.add(servent, Util.stringToIdentity("Server"));
        adapter.activate();

        serverPrx = ServerPrx.checkedCast(
            adapter.createProxy(Util.stringToIdentity("Server"))
                    .ice_twoway()
                    .ice_secure(false));   
    }

}
