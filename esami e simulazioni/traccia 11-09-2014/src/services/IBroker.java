package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBroker extends Remote{
    public abstract void sottoponi(int tipoOperazione, int quantita) throws RemoteException;
    public abstract void sottoscrivi(String address, int port) throws RemoteException;
}
