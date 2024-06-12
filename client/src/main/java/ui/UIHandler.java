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

    public String randomPoints(){
        String points = "";

        try {
            System.out.print("|| Numero de puntos aleatorios: ");
            points = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return points;
    }

    public String partitions(){
        String partitions = "";

        try {
            System.out.print("|| Numero de particiones: ");
            partitions = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return partitions;
    }

    public String solver(){
        String solver = "";

        try {
            System.out.println(
                "|| Metodo de Integracion: \n"+
                "|| ( 1 ) Monte Carlo\n"+
                "|| ( 2 ) Riemann"
            );
            System.out.print("|| Opcion: ");
            solver = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return solver;
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
