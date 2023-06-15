package client;

import java.rmi.RemoteException;

import services.IBroker;

public class ClientThread extends Thread{
    private int REQUESTS;
    private IBroker brokerService;
    public ClientThread(int REQUESTS, IBroker brokerService){
        this.REQUESTS = REQUESTS;
        this.brokerService = brokerService;
    }

    @Override
    public void run() {
        for (int i = 0; i < REQUESTS; i++) {
            try {
                Thread.sleep(1 + (int)(Math.random() * 3));
                int tipoOperazione =  1 + (int) (Math.random() * 2);
                int quantita = 1 +(int) (Math.random() * 5);
                System.out.println(tipoOperazione + " " + quantita);
                brokerService.sottoponi(tipoOperazione, quantita);
                System.out.println("Richiesta sottoposta correttamente");
            } catch (InterruptedException e) {
                System.err.println("Thread Error with sleep.");
                e.printStackTrace();
            } catch (RemoteException e) {
                System.err.println("Problem with RemoteCall");
                e.printStackTrace();
            }

        }
    }
    
}
