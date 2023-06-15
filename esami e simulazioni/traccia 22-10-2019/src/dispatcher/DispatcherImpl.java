package dispatcher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import services.IDispatcher;
import services.Observer;
import services.Reading;

public class DispatcherImpl extends UnicastRemoteObject implements IDispatcher{
    Vector<Observer> obs;
    Vector<String> str;
    private Reading reading;

    protected DispatcherImpl() throws RemoteException {
        super();
        obs = new Vector<>();
        str = new Vector<>();
        reading = new Reading("", 0);

    }

    @Override
    public void setReading(Reading reading) throws RemoteException {
        System.out.println("called setReading");
        synchronized(this){
            this.reading = reading;
            //check degli observer
            for(int i = 0; i<obs.size(); i++){
                if(str.get(i).compareTo(reading.getTipo()) == 0){
                    obs.get(i).notifyReading();//notifica il topic corretto;
                }
            }
        }
    }

    @Override
    public void attach(Observer observer, String tipo) throws RemoteException {
        obs.add(observer);
        str.add(tipo);
        System.out.println("Observer attached sul topic: " + tipo + " " + observer);
    }

    @Override
    public Reading getReading() throws RemoteException {
        return this.reading;
    }
    
}
