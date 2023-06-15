package subscriber;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IManager;

public class Subscriber {
    public static void main(String[] args) {
        if(args.length != 3) throw new IllegalArgumentException("Servono 3 parametri! {componentID, port, filename}");
        try {
            Registry rmi = LocateRegistry.getRegistry();
            IManager im = (IManager) rmi.lookup("service");
            int componentID = Integer.parseInt(args[0]);
            int port = Integer.parseInt(args[1]);
            String filename = args[2];
            im.subscribe(componentID, port);
            System.out.println("Registrazione al servizio remoto sul componentID:" + componentID);

            SubscriberSkeleton sk = new SubscriberSkeleton(port, filename);
            sk.runSkeleton();


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
