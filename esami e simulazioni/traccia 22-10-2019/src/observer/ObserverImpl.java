package observer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import services.IDispatcher;
import services.Observer;
import services.Reading;

public class ObserverImpl extends UnicastRemoteObject implements Observer{
    
    private IDispatcher disp;
    private String filename;
    protected ObserverImpl(IDispatcher disp, String filename) throws RemoteException {
        super();
        this.disp = disp;
        this.filename = filename;
    }

    @Override
    public void notifyReading() throws RemoteException {
        Reading r = disp.getReading();
        try {
            FileWriter f = new FileWriter(new File(filename), true);
            f.append(r.toString() + "\n");
            f.close();
            System.out.println("Scrittura sul file completata. " + r.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
