import com.zeroc.Ice.Current;

import AppInterfaces.Client;

public class ClientServent implements Client{

    @Override
    public void printResponse(String integral, String res, String performance, Current current) {
        if(!integral.equals("")){
            System.out.println("\n|| La solucion a la integral "+integral+" es aproximadamente "+res);
            System.out.println(performance);
            resume();
        } else{
            System.out.println(res);
        }
    }

    private void resume(){
        System.out.print(""
                +"||===================================================================================||\n"
                +"|| Ingrese la funcion que desea integrar (exit para salir): "
                +"");
    }


}