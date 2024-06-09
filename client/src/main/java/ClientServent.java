import com.zeroc.Ice.Current;

import AppInterfaces.Client;

public class ClientServent implements Client{

    @Override
    public void printResponse(String integral, String res, Current current) {
        if(!integral.equals("")){
            System.out.println("|| La solucion a la integral "+integral+" es aproximadamente "+res);
        } else{
            System.out.println(res);
        }
    }


}