package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Semaphore;

import services.ISportello;

public class SportelloImpl extends UnicastRemoteObject implements ISportello{

    Semaphore concurrent, waiting;
    int index;
    protected SportelloImpl(int index) throws RemoteException {
        super();
        concurrent = new Semaphore(3);
        waiting = new Semaphore(5);
        this.index = index;
    }

    @Override
    public boolean serviRichiesta(int idCliente) throws RemoteException {
        System.out.println("Called serviRichista on Thread: "+Thread.currentThread().getName());
        if(!waiting.tryAcquire()){
            System.out.println(Thread.currentThread().getName() + " non posso servire " + idCliente);
            return false;
        }

        try {
            concurrent.acquire();
            int towait = 1 + ((int)Math.random() * 4);
            Thread.sleep(towait * 1000);
            System.out.println("scrivo su file: " + idCliente);

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("file" + index +".out", true)));
            out.append("scrivo su file: " + idCliente + "\n");
            out.close();

            concurrent.release();
            waiting.release();

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return true;
    }
    
}
