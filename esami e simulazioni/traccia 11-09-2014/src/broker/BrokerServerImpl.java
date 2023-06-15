package broker;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import services.IBroker;

public class BrokerServerImpl extends UnicastRemoteObject implements IBroker{
    Vector<Connection> observers;
    private int indexAgency;
    protected BrokerServerImpl() throws RemoteException {
        super();//super esporta il servizio in jndi
        observers = new Vector<>();
        this.indexAgency = 0;
    }

    @Override
    public void sottoponi(int tipoOperazione, int quantita) throws RemoteException {
        //calling proxy stub for remote
        Connection conn = observers.get(indexAgency);
        AgenziaProxy ap = new AgenziaProxy(conn.getAddress(), conn.getPort());
        if(tipoOperazione == 1){
            ap.acquista(quantita);
        }else if(tipoOperazione == 2){
            ap.vendi(quantita);
        }else throw new UnsupportedOperationException("Operazione non supportata " + tipoOperazione);

        indexAgency = ++indexAgency % observers.size();
    
    }


    @Override
    public void sottoscrivi(String address, int port) throws RemoteException {
        System.out.println("ricevuta sottoscrizione " + address + " " + port);
        observers.add(new Connection(address, port));
    }

    private class Connection{
        private String address;
        private int port;
        public Connection(String address, int port) {
            this.address = address;
            this.port = port;
        }

        public String getAddress() {
            return address;
        }

        public int getPort() {
            return port;
        }

        
        
    }
    
}
