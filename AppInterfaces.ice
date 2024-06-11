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
        void printResponse(string integral, string res, string performance);
    }

    interface Server {
        void solveIntegral(int requestID, Integral integral);
        void testMode(int requestID, Integral integral, string option, string numberFormat);
        void printResponse(string res);
        double getLoad();
    }

    interface Broker {
        void addServer(Server* server);
        void solveIntegral(Client* clientProxy, Integral integral);
        void testMode(Client* clientProxy, Integral integral, string option, string numberFormat);
        void join(int requestID, string res);
    }

}