package disk;

public class DiskThread extends Thread{
    final private int porto, dato;
    public DiskThread(final int dato, final int porto){
        this.dato = dato;
        this.porto = porto;
    }

    @Override
    public void run() {
        System.out.println("Ricevuta richiesta di scrittura: " + dato);
        //System.out.println("Invio richiesta a logger:"+porto);
        LoggerProxy lp = new LoggerProxy(porto);
        lp.registraDato(dato);
    }
    
}
