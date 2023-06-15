package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import services.IGestoreSportello;
import services.ISportello;

public class GestoreSportelloImpl extends UnicastRemoteObject implements IGestoreSportello{
    Vector<ISportello> attachedSportelli;
    protected GestoreSportelloImpl() throws RemoteException {
        super();
        attachedSportelli = new Vector<>();
    }

    @Override
    public boolean sottoponiRichiesta(int idCliente) throws RemoteException {
        boolean result = false;
        int i = 0;
        while(!result && (i++)<attachedSportelli.size()){
            result = attachedSportelli.get(i-1).serviRichiesta(idCliente);
        }
        result = attachedSportelli.get(0).serviRichiesta(idCliente);
        return result;
    }

    @Override
    public void sottoscrivi(ISportello sportello) throws RemoteException{
        attachedSportelli.add(sportello);
        System.out.println("Aggiungo SPORTELLO alla flotta! " + sportello.toString());
    }
    
}
