package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDispatcher extends Remote{
    public abstract void setReading(Reading reading) throws RemoteException;
    public abstract void attach(Observer Observer, String tipo) throws RemoteException;
    public abstract Reading getReading() throws RemoteException;
}
