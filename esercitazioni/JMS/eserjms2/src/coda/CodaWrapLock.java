package coda;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CodaWrapLock extends CodaWrapper{
    private Lock lock;
    private Condition prod, cons;

    public CodaWrapLock(Coda coda) {
        //coda è protected nella superclasse
        super(coda);
        lock = new ReentrantLock();
        prod = lock.newCondition();
        cons = lock.newCondition();
    }

    @Override
    public void put(String v) {

        lock.lock();

        while(coda.isFull()){
            try {
                prod.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        coda.put(v);
        cons.signal();

        lock.unlock();

    }

    @Override
    public String[] takeAll() {
        lock.lock();

        while(coda.isEmpty()){
            try {
                cons.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String[] all = coda.takeAll();
        prod.signal();

        lock.unlock();

        return all;
    }
    
}
