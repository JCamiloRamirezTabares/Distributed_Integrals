import java.util.concurrent.*;
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

    private int SERVERSPERCLIENT;

    private PriorityBlockingQueue<ServerPrx> servers;
    private ConcurrentHashMap<Integer, Integral> requests;
    private ConcurrentHashMap<Integer, ClientPrx> clients;

    private ConcurrentHashMap<Integer, List<BigDecimal>> resPerRequest;
    private ConcurrentHashMap<Integer, List<Long>> latencyPerRequest;
    private ExecutorService poolTasks;

    public BrokerServent(){
        servers = new PriorityBlockingQueue<>(10, new ServerLoadComparator());
        requests = new ConcurrentHashMap<>();
        clients = new ConcurrentHashMap<>();
        resPerRequest = new ConcurrentHashMap<>();
        latencyPerRequest = new ConcurrentHashMap<>();

        poolTasks = Executors.newCachedThreadPool();
    }

    @Override
    public void addServer(ServerPrx server, Current current) {
        servers.add(server);
        server.printResponse("|| Servidor registrado correctamente");
    }

    @Override
    public void solveIntegral(ClientPrx clientProxy, Integral integral, Current current) {
        long startTimeBroker = System.nanoTime();
        int integralID = integral.hashCode();

        if(!servers.isEmpty()){
            registerRequest(integralID, integral);
            registerClient(integralID, clientProxy);
            processRequest(integralID, integral);
        } else{
            clientProxy.printResponse("", 
            "|| La integral no se pudo resolver\n"+
            "|| Razon: No hay servidores registrados",
            ""
            );
        }

        saveTimes(integralID, startTimeBroker);
    }

    @Override
    public void testMode(ClientPrx clientProxy, Integral integral, String option, String numberFormat, Current current) {
        long startTimeBroker = System.nanoTime();
        int integralID = integral.hashCode();

        if(!servers.isEmpty()){
            registerRequest(integralID, integral);
            registerClient(integralID, clientProxy);
            processRequestTestMode(integralID, integral, option, numberFormat);
        } else{
            clientProxy.printResponse("", 
            "|| La integral no se pudo resolver\n"+
            "|| Razon: No hay servidores registrados",
            ""
            );
        }
        
        saveTimes(integralID, startTimeBroker);
    }

    @Override
    public void join(int requestID, String res, Current current) {
        synchronized (this) {
            BigDecimal partialRes = new BigDecimal(res);
            List<BigDecimal> resClient = resPerRequest.get(requestID);

            if(resClient != null){
                resPerRequest.get(requestID).add(partialRes);

                if(resPerRequest.get(requestID).size() == SERVERSPERCLIENT){
                    BigDecimal sum = BigDecimal.ZERO;
                    
                    for(BigDecimal value: resPerRequest.get(requestID)){
                        sum = sum.add(value);
                    }

                    ClientPrx client = clients.get(requestID);
                    Integral integral = requests.get(requestID);

                    long endTimeBroker = System.nanoTime();

                    saveTimes(requestID, endTimeBroker);
                    //Se limpia lo que uso el client
                    requests.remove(requestID);
                    clients.remove(requestID);
                    resPerRequest.remove(requestID);

                    String perfomanceReport = buildPerformance(requestID);

                    client.printResponse(
                        integral.functionnString+" entre "+integral.lowerRange+" y "+integral.upperRange, 
                        ""+sum.doubleValue(),
                        perfomanceReport
                    );    
                }
            } else {
                resPerRequest.put(requestID, new ArrayList<>());
                join(requestID, res, current);
            }
        }
    }

    
    private void registerRequest(Integer requestID, Integral request){
        requests.put(requestID, request);
    }

    private void registerClient(Integer requestID, ClientPrx client){
        clients.put(requestID, client);
    }

    private void processRequest(Integer requestID, Integral request){
        resPerRequest.put(requestID, new ArrayList<>());
        SERVERSPERCLIENT = servers.size();

        List<Integral> subIntegrals = fork(request);
        assignServers(requestID, subIntegrals);
    }

    private void processRequestTestMode(Integer requestID, Integral request, String mode, String format){
        resPerRequest.put(requestID, new ArrayList<>());
        SERVERSPERCLIENT = servers.size();

        List<Integral> subIntegrals = fork(request);
        assignServersTest(requestID, subIntegrals, mode, format);
    }


    private List<Integral> fork(Integral integral){

        double a = integral.lowerRange;
        double b = integral.upperRange;
        double div = (b-a)/SERVERSPERCLIENT;

        List<Integral> integrals = new ArrayList<>();

        for(int i = 0; i < SERVERSPERCLIENT; i++){
            integrals.add(new Integral(integral.functionnString, a + (i*div), a + ((i+1)*div)));
        }

        return integrals;
    }


    private void assignServers(Integer integralID, List<Integral> integrals){
        for(Integral integral: integrals){
            ServerPrx server = servers.poll();
            
            Runnable task = () -> {
                server.solveIntegral(integralID, integral);
            };

            poolTasks.submit(task);
            servers.add(server);
        }
    }

    private void assignServersTest(Integer integralID, List<Integral> integrals, String mode, String format){
        for(Integral integral: integrals){
            ServerPrx server = servers.poll();
            Runnable task = () -> {
                server.testMode(integralID, integral, mode, format);
            };

            poolTasks.submit(task);
            servers.add(server);
        }
    }

    private double latency(long start, long end){
        long executionTime = end - start;
        double executionTimeInSeconds = executionTime / 1_000_000_000.0;
        return executionTimeInSeconds;
    }


    private void saveTimes(Integer requestID, long time){
        if(latencyPerRequest.get(requestID) != null){
            latencyPerRequest.get(requestID).add(time);
        } else {
            latencyPerRequest.put(requestID, new ArrayList<>());
            latencyPerRequest.get(requestID).add(time);
        }
    }

    private String buildPerformance(Integer requestID){
        List<Long> lat = latencyPerRequest.get(requestID);
        double latency = latency(lat.get(0), lat.get(1));

        latencyPerRequest.remove(requestID);

        return "|| Performance Analytics: \n"+
               "|| Response Latency: "+latency+" segundos\n"+
               "";
    }

    
    
}