import java.math.BigInteger;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

import AppInterfaces.BrokerPrx;
import AppInterfaces.ClientPrx;
import AppInterfaces.Integral;
import ui.UIHandler;

/*
    Tareas por hacer:
    Maneje las funciones: str -> Function                                   ==> done
    Guardar información (Función, límites de integración)                   ==> done
    Función que se encargue de pedir y guardar límites de integración       ==> done
    Implementar métricas para el cliente                                    ==> done
 */

public class Client {

    /*
        Esta es la controladora de la UI por consola
     */
    private static UIHandler handlerUI;

    private static BrokerPrx brokerPrx;
    private static ClientPrx clientPrx;

    public static void main(String[] args) {
        initialize(args);
    }

    /*
        Inicializa lo necesario para que trabaje el cliente (Es como el constructor)
     */
    private static void initialize(String[] args){
        try(Communicator communicator = Util.initialize(args, "config.client"))
        {
            handlerUI = new UIHandler();

            createBrokerPrx(communicator);
            createClientPrx(communicator);

            if(args.length == 0){
                start();
            } else{
                if(args[0].equalsIgnoreCase("test") && args.length == 6){
                    testMode(args);
                } else if(args[0].equalsIgnoreCase("test") && args.length == 1){
                    test();
                } else{
                    System.out.println("Otro modo");
                }   
            }
        }
    }

    /*
        Se crea un proxy para comunicarse con el broker (La idea es mandar las peticiones al broker)
        La comunicacion es bidireccional, es decir el cliente espera respuesta del broker
     */
    private static void createBrokerPrx(Communicator communicator){
        brokerPrx = BrokerPrx.checkedCast(
                communicator.propertyToProxy("Broker.Proxy"))
                            .ice_twoway()
                            .ice_secure(false);

        if(brokerPrx == null){
            throw new Error("Invalid proxy");
        }
    }

    // Se crea el objeto proxy para comunicaciones remotas con el client (Callbacks)
    private static void createClientPrx(Communicator communicator){
        ObjectAdapter adapter = communicator.createObjectAdapter("Client");
        Object servent = new ClientServent(); 

        adapter.add(servent, Util.stringToIdentity("Client"));
        adapter.activate();

        clientPrx = ClientPrx.checkedCast(
            adapter.createProxy(Util.stringToIdentity("Client"))
                    .ice_twoway()
                    .ice_secure(false));
    }

    /*
        Inicializa modo consola normal (Es el modo para ser usado por el usuario final)
     */
    private static void start(){
        boolean sentinel = true;

        while (sentinel) {
            String input = handlerUI.functionMenu();
            double lowerRange = 0;
            double upperRange = 0;

            if (!input.equalsIgnoreCase("exit")) {
                boolean validRanges = false;

                while (!validRanges) {
                    String lower = handlerUI.lowerRangeMenu();
                    String upper = handlerUI.upperRangeMenu();

                    try {
                        lowerRange = Double.parseDouble(lower);
                        upperRange = Double.parseDouble(upper);

                        if (lowerRange >= upperRange) {
                            System.out.println("|| El limite inferior debe ser menor que el limite superior. Intentelo de nuevo.");
                        } else {
                            validRanges = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("|| Por favor, introduzca un número válido.");
                    }
                }

                // Se crea la integral como objeto de ICE
                Integral integral = new Integral(input, lowerRange, upperRange);

                // Se envia la solicitud al broker
                brokerPrx.solveIntegral(clientPrx, integral);
                
                
            } else {
                sentinel = false;
                handlerUI.byebye();
            }
        }
    }


   
    /*
        Este es el modo para hacer pruebas directamente con los scripts
        Lee las instrucciones del script y ejecuta el proceso
     */
    private static void testMode(String[] args){
        handlerUI.testing();

        if(args[1].equals("1")){
            // Se crea la integral como objeto de ICE
            String points = args[2];
            handlerUI.MonteCarlo(points);
            Integral integral = new Integral(
                args[3],
                Double.parseDouble(args[4]), 
                Double.parseDouble(args[5])
            );

            // Se envia la solicitud al broker
            brokerPrx.testMode(clientPrx, integral, "1", points);
        } else{

            String partitions = args[2];
            handlerUI.Riemann(partitions);
           // Se crea la integral como objeto de ICE
            Integral integral = new Integral(
                args[3],
                Double.parseDouble(args[4]), 
                Double.parseDouble(args[5])
            );

            // Se envia la solicitud al broker
            brokerPrx.testMode(clientPrx, integral, "1", partitions);
        }
    }


    private static void test(){
        boolean sentinel = true;

        while (sentinel) {
            String input = handlerUI.functionMenu();
            double lowerRange = 0;
            double upperRange = 0;

            String solver = handlerUI.solver();

            if (!input.equalsIgnoreCase("exit")) {
                boolean validRanges = false;

                while (!validRanges) {
                    String lower = handlerUI.lowerRangeMenu();
                    String upper = handlerUI.upperRangeMenu();

                    try {
                        lowerRange = Double.parseDouble(lower);
                        upperRange = Double.parseDouble(upper);

                        if (lowerRange >= upperRange) {
                            System.out.println("|| El limite inferior debe ser menor que el limite superior. Intentelo de nuevo.");
                        } else {
                            validRanges = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("|| Por favor, introduzca un número válido.");
                    }
                }

                String numberFormat = "";

                if(solver.equals("1")){
                    numberFormat = handlerUI.randomPoints();
                } else {
                    numberFormat = handlerUI.partitions();
                }

                // Se crea la integral como objeto de ICE
                Integral integral = new Integral(input, lowerRange, upperRange);

                // Se envia la solicitud al broker
                brokerPrx.testMode(clientPrx, integral, solver, numberFormat);   
            } else {
                sentinel = false;
                handlerUI.byebye();
            }
        }
    }
}
