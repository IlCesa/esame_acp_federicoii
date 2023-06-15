package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDispatcher extends Remote{
    //abstract è ridondante, ogni metodo definito nelle interfacce è abstract by default
    //questa cosa l'ho scoperta studiando la classe Thread e l'interfaccia Runnable on my own.
    public abstract boolean printRequest(String docName) throws RemoteException;
    public abstract void addPrinter(Printer printer) throws RemoteException;
}
