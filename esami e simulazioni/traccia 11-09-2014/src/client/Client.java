package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import services.IBroker;

public class Client {
    public static void main(String[] args) throws Exception {
        int T = 5, R = 25;
        Thread tList[] = new Thread[T];
        //ExecutorService es = Executors.newFixedThreadPool(T);
        

        Registry rmiRegistry = LocateRegistry.getRegistry();
        IBroker brokerService = (IBroker) rmiRegistry.lookup("service");
        
        for (int i = 0; i < T; i++) {
            tList[i] = new ClientThread(R, brokerService);
            tList[i].start();
        }

        for (int i = 0; i < T; i++) tList[i].join(); 

    }
}
