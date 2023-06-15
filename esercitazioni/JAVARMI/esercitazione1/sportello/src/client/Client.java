package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IGestoreSportello;

public class Client {

    public static void main(String[] args) {
        final int T = 10;
        final int R = 10;
        Thread tList[] = new Thread[T];

        for (int i = 0; i < T; i++) {
            tList[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int j = 0; j < R; j++) {
                        int waitS = 1 + (int)(Math.random() * 2) ;
                        try {
                            Thread.sleep(waitS * 1000);
                            int idCliente = 1 + (int)(Math.random() * 99) ;
                            Registry rmi = LocateRegistry.getRegistry();;
                            IGestoreSportello gsService = (IGestoreSportello)rmi.lookup("gestoreservice");
                            boolean res = gsService.sottoponiRichiesta(idCliente);
                            System.out.println("Richiesta gestita con esito " + res + " id:" + idCliente);
                            

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (RemoteException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (NotBoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
                
            });
            tList[i].start();
        }

        for (int i = 0; i < T; i++) {
            try {
                tList[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        

    }

}


