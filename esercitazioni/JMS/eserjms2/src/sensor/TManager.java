package sensor;

import coda.Coda;

public class TManager extends Thread{

    private Coda coda;
    private String operation;

    public TManager(Coda coda, String operation){
        this.coda = coda;
        this.operation = operation;
    }

    @Override
    public void run() {
         coda.put(operation);
    }

}
