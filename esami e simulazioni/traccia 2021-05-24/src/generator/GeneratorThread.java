package generator;

import java.rmi.RemoteException;

import services.AlertNotification;
import services.IManager;

public class GeneratorThread extends Thread{

    private IManager man;
    public GeneratorThread(IManager man){
        this.man = man;
    }

    @Override
    public void run() {
        final int componentID = 1 + (int)(Math.random() * 5);
        final int criticality = 1 + (int)(Math.random() * 3);

        AlertNotification alertNotification = new AlertNotification(componentID, criticality);

        try {
            man.sendNotification(alertNotification);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    
}
