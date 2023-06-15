package observer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IDispatcher;


public class Observer {
    public static void main(String[] args) {
        if(args.length !=2){
            throw new IllegalArgumentException("Servono 2 parametri!");
        }

        try {
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IDispatcher disp = (IDispatcher)rmiRegistry.lookup("service");
            services.Observer obs = new ObserverImpl(disp, args[1]);
            
            disp.attach(obs,args[0]);
            System.out.println("Attachato al server!");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }


    }
}
