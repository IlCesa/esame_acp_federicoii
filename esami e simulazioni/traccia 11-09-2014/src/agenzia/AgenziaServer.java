package agenzia;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IBroker;

public class AgenziaServer {
    public static void main(String[] args) {
        if(args.length != 1) throw new IllegalArgumentException("Serve il porto.");
        try {
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IBroker ib = (IBroker) rmiRegistry.lookup("service");
            int port = Integer.parseInt(args[0]);
            ib.sottoscrivi("localhost",port);
            System.out.println("Sottoscritto al broker!");
            AgenziaImpl agenziaImpl = new AgenziaImpl();
            agenziaImpl.runSkeleton(port);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
