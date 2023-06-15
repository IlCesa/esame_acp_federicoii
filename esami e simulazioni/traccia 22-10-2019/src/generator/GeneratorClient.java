package generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IDispatcher;

public class GeneratorClient {
    public static void main(String[] args) {
        final int NUM_THREAD = 3;
        final int NUM_REQUESTS = 3;

        Registry rmiRegistry;
        try {
            rmiRegistry = LocateRegistry.getRegistry();
            IDispatcher disp = (IDispatcher)rmiRegistry.lookup("service");
            for(int i = 0; i<NUM_THREAD; i++){
                GeneratorThread gt = new GeneratorThread(NUM_REQUESTS, disp);
                gt.start();
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        




    }
}
