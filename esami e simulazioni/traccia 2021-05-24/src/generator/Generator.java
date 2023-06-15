package generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


import services.IManager;

public class Generator {
    public static void main(String[] args) {
        final int NUM_THREADS = 300;
        try {
            
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IManager man = (IManager)rmiRegistry.lookup("service");
            for (int i = 0; i < NUM_THREADS; i++) {
                GeneratorThread gt = new GeneratorThread(man);
                gt.run();
            }
            
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
