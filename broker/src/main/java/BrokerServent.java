import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;

import com.zeroc.Ice.Current;
import AppInterfaces.Broker;
import AppInterfaces.ClientPrx;
import AppInterfaces.Integral;
import AppInterfaces.ServerPrx;

import model.ServerLoadComparator;

public class BrokerServent implements Broker {

    private final int SERVERSPERCLIENT = 2;

    private PriorityBlockingQueue<ServerPrx> servers;
    private ConcurrentHashMap<Integer, Integral> requests;
    private ConcurrentHashMap<Integer, ClientPrx> clients;

    private ConcurrentHashMap<Integer, List<BigDecimal>> resPerRequest;
    private ExecutorService poolTasks;

    public BrokerServent(){
        servers = new PriorityBlockingQueue<>(10, new ServerLoadComparator());
        requests = new ConcurrentHashMap<>();
        clients = new ConcurrentHashMap<>();
        resPerRequest = new ConcurrentHashMap<>();

        poolTasks = Executors.newCachedThreadPool();
    }

    @Override
    public void addServer(ServerPrx server, Current current) {
        servers.add(server);
        server.printResponse("|| Servidor registrado correctamente");
    }

    @Override
    public void solveIntegral(ClientPrx clientProxy, Integral integral, Current current) {
        if(!servers.isEmpty()){

            int integralID = integral.hashCode();

            registerRequest(integralID, integral);
            registerClient(integralID, clientProxy);
            processRequest(integralID, integral);
            
            clientProxy.printResponse(integral.toString(), "Se resolvio la integral");
        } else{
            clientProxy.printResponse("", 
            "|| La integral no se pudo resolver\n"+
            "|| Razon: No hay servidores registrados"
            );
        }
    }

    @Override
    public void join(int requestID, String res, Current current) {
        
    }

    
    private void registerRequest(Integer requestID, Integral request){
        requests.put(requestID, request);    
    }

    private void registerClient(Integer requestID, ClientPrx client){
        clients.put(requestID, client);
    }

    private void processRequest(Integer requestID, Integral request){
        resPerRequest.put(requestID, new ArrayList<>());

        List<Integral> subIntegrals = fork(request);
        assignServers(requestID, subIntegrals);
    }


    private List<Integral> fork(Integral integral){

        double a = integral.lowerRange;
        double b = integral.upperRange;
        double div = (b-a)/SERVERSPERCLIENT;

        List<Integral> integrals = new ArrayList<>();

        for(int i = 0; i < SERVERSPERCLIENT; i++){
            integrals.add(new Integral(integral.functionnString, a + (i*div), a + (i+1*div)));
        }

        return integrals;
    }


    private void assignServers(Integer integralID, List<Integral> integrals){
        for(Integral integral: integrals){
            ServerPrx server = servers.poll();
            
            Runnable task = () -> {
                server.solveIntegral(integral);
            };

            poolTasks.submit(task);
            servers.add(server);
        }
    }
    
}

/*
    private void processRequest(Integer requestID, Integral request){
        responsesPerClient.put(requestID, BigDecimal.ZERO); // Se asigna zero a la respuesta
        counterServersPerClients.put(requestID, new AtomicInteger(SERVERSPERCLIENT)); // Se asignan servers al client

        List<Integral> subIntegrals = fork(request);

        assignServers(requestID, subIntegrals);


    }

    

    

    private synchronized void join(Integer integralID, String res){
        BigDecimal currentValue = responsesPerClient.get(integralID);
        BigDecimal partialRes = new BigDecimal(res);
        BigDecimal newValue = currentValue.add(partialRes);

        responsesPerClient.put(integralID, newValue);

        if(counterServersPerClients.get(integralID).decrementAndGet() == 0){
            ClientPrx client = clients.get(integralID);
            client.printResponse(res, res);
        }
    }

     */
