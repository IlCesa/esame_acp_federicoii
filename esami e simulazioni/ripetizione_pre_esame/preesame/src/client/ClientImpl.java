package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;



import services.IClient;

public class ClientImpl extends UnicastRemoteObject implements IClient{

    protected ClientImpl() throws RemoteException {
        super();
    }

    @Override
    public void clientService(String operation) throws RemoteException {
        System.out.println("[CLIENT] callback invocata." + operation);
    }
    
}
