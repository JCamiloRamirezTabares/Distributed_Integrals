import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.Object;

import ui.UIHandler;

public class Broker {

    private static UIHandler uiHandler;

    public static void main(String[] args){
        initialize(args);
    }

    private static void initialize(String[] args){
        try(Communicator communicator = Util.initialize(args, "config.broker"))
        {
            uiHandler = new UIHandler();
            createAdapter(communicator);

            communicator.waitForShutdown();
        }
    }

    private static void createAdapter(Communicator communicator){
        ObjectAdapter adapter = communicator.createObjectAdapter("Broker");
        Object servent = new BrokerServent(); 

        adapter.add(servent, Util.stringToIdentity("Broker"));
        adapter.activate();
    }
}
