package agenzia;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AgenziaImpl extends AgenziaSkeleton{
    private int biglietti;
    private Lock lock;
    private Condition acquista;
    public AgenziaImpl(){
        this.biglietti = 10;
        lock = new ReentrantLock();
        acquista = lock.newCondition();
    }

    @Override
    public void vendi(int quantita) {
        lock.lock();
        biglietti+=quantita;
        System.out.println("Disponibilita' aumentata, biglietti disponibili ora: " + biglietti);
        acquista.signal();

        lock.unlock();
    }

    @Override
    public void acquista(int quantita) {
        lock.lock();

        if(biglietti < quantita){
            try {
                System.out.println("Richiesta qta:" + quantita + " disp: " + biglietti + " await.");
                acquista.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        biglietti-=quantita;
        System.out.println("Biglietti acquistati, ora disponibili:" + biglietti);


        lock.unlock();

    }
    
}
