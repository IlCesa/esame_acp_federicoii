package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDispatcher extends Remote{
    public abstract void sottoscrivi(IClient client) throws RemoteException;

}
