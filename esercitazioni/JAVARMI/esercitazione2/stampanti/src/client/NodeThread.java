package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IDispatcher;

public class NodeThread extends Thread{

    public NodeThread(){

    }

    @Override 
    public void run(){
        for (int i = 0; i < 3; i++) {
            sendRequest();
            System.out.println(Thread.currentThread().getName() + " inviata printRequest!");
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }

    private void sendRequest(){

        try{
            Registry rmi = LocateRegistry.getRegistry();
            IDispatcher disp = (IDispatcher)rmi.lookup("service");
            int id = 1+(int)(Math.random()*49);
            String docName = "doc" + id;
            boolean res = disp.printRequest(docName);
            System.out.println("Ricevuto " + res + " per " + docName);
        }catch(RemoteException e){
            e.printStackTrace();
        }catch(NotBoundException e){
            e.printStackTrace();
        }

    }
}
