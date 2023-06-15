package dispatcher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import services.IClient;
import services.IDispatcher;

public class DispatcherImpl extends UnicastRemoteObject implements IDispatcher{

    Vector<IClient> observers;

    protected DispatcherImpl() throws RemoteException {
        super(); //super esporta il servizio in rmi registry
        observers = new Vector<>();
    }

    @Override
    public void sottoscrivi(IClient client) throws RemoteException {
        observers.add(client);
        System.out.println("[DISPATCHER] CLIENT SOTTOSCRITTO.");
       // notifica();
    }

    public void notifica(String operation){
        for (IClient iClient : observers) {
            try {
                iClient.clientService(operation);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    
}
