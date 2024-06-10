package model;

import java.util.Comparator;

import AppInterfaces.ServerPrx;


public class ServerLoadComparator implements Comparator<ServerPrx>{

    @Override
    public int compare(ServerPrx o1, ServerPrx o2) {
        return Double.compare(o1.getLoad(), o2.getLoad());
    }
    
}
