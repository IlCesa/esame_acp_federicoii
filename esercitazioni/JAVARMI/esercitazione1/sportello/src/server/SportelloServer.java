package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IGestoreSportello;
import services.ISportello;

public class SportelloServer {
    public static void main(String[] args) {
        System.out.println("MainThread: "+Thread.currentThread().getName());
        for (int index = 0; index < 3; index++) {

            try {
                Registry rmi = LocateRegistry.getRegistry();
                IGestoreSportello gsService = (IGestoreSportello)rmi.lookup("gestoreservice");
                ISportello sportello = new SportelloImpl(index);
                System.out.println(sportello.toString());
                gsService.sottoscrivi(sportello);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NotBoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            };

            /*new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Registry rmi = LocateRegistry.getRegistry();
                        IGestoreSportello gsService = (IGestoreSportello)rmi.lookup("gestoreservice");
                        ISportello sportello = new SportelloImpl();
                        gsService.sottoscrivi(sportello);
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NotBoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    };
                }
                
            }).start();*/
            
        }
        
        
    }
}
