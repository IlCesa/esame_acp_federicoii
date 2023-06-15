package manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import services.AlertNotification;
import services.IManager;

public class ManagerImpl extends UnicastRemoteObject implements IManager{

    Vector<Subscriber> subscribers;
    protected ManagerImpl() throws RemoteException {
        super(); //chiamando super il componente viene registrato nel rmiRegistry locale;
        subscribers = new Vector<>();
    }

    //in questo caso synchronized fa rimerimento al monitor dell'oggetto this, che è quello che vogliamo per eseguire le operazioni
    //in mutua esclusione (come richiesto)
    @Override
    public synchronized void sendNotification(AlertNotification alertNotification) throws RemoteException {
        System.out.println("Ricevuto " + alertNotification.toString());
        //System.out.println("Notifico i subscribers...");
        for (Subscriber subscriber : subscribers) {
            if(subscriber.getComponentID() == alertNotification.getComponentID()){
                SubscriberProxy sb = new SubscriberProxy(subscriber.getPort());
                sb.notifyAlert(alertNotification.getCriticality());
                System.out.println("Notifico subscriber port:" + subscriber.getPort() + " registrato sul componentID: " + subscriber.getComponentID());
            }
        }
    }

    @Override
    public void subscribe(int componentID, int port) throws RemoteException {
        subscribers.add(new Subscriber(componentID, port));
        System.out.println("Subscriber registrato!");
    }
    
}
