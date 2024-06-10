/*
    Los metodos llamados printResponse son callbacks que se usan para 
    llamar cuando este listo la respuesta del server y client
*/
module AppInterfaces {

    class Integral {
        string functionnString;
        double lowerRange;
        double upperRange;
    }

    interface Client {
        void printResponse(string integral, string res);
    }

    interface Server {
        void solveIntegral(Integral integral);
        void printResponse(string res);
        double getLoad();
    }

    interface Broker {
        void addServer(Server* server);
        void solveIntegral(Client* clientProxy, Integral integral);
        void join(int requestID, string res);
    }

}