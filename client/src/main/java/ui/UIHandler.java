package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UIHandler {
    
    private final BufferedReader reader;

    public UIHandler(){
        reader = new BufferedReader(new InputStreamReader(System.in));


        welcome();
    }

    public String functionMenu(){
        String function = "";

        try {
            System.out.print(""
                +"||===================================================================================||\n"
                +"|| Ingrese la funcion que desea integrar (exit para salir): "
                +"");
        
            function = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return function;
    }

    public String lowerRangeMenu(){
        String lowRange = "";

        try {
            System.out.print("|| Rango de integracion inferior: ");
            lowRange = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lowRange;
    }

    public String upperRangeMenu(){
        String uppRange = "";

        try {
            System.out.print("|| Rango de integracion superior: ");
            uppRange = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uppRange;
    }

    private void welcome(){
        System.out.println(""
        +"||===================================================================================||\n"
        +"||                 Bienvenido al sistema de Integracion de funciones                 ||\n"
        +"||===================================================================================||\n"
        +"                                        ||   ||                                          "
        +"");
    }

    public void byebye(){
        System.out.println("|| Hasta luego, vuelve pronto :D");
    }

    public void testing(){
        System.out.println(""
            +"||===================================================================================||\n"
            +"|| Iniciando pruebas...                                                              ||"
            );
    }

    public void MonteCarlo(String format){
        System.out.println(""
            +"|| Iniciando MonteCarlo con "+format+" puntos...                                     ||"
            +"||===================================================================================||\n"
        );
    }

    public void Riemann(String format){
        System.out.println(""
            +"|| Iniciando Riemann con "+format+" particiones...                                   ||"
            +"||===================================================================================||\n"
        );
    }

    public void testFinished(){
        System.out.println(""
            +"||===================================================================================||\n"
            +"|| Pruebas finalizadas, revisa la carpeta docs para mas informacion                  ||\n"
            +"||===================================================================================||"
            );
    } 

}
