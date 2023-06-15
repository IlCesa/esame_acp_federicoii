package coda;

public abstract class CodaWrapper implements Coda{
    protected Coda coda;
    public CodaWrapper(Coda coda){
        this.coda = coda;
    }
    @Override
    public int getElem() {
        return coda.getElem();
    }

    @Override
    public int getSize() {
        return coda.getSize();
    }

    @Override
    public boolean isEmpty() {
        return coda.isEmpty();
    }

    @Override
    public boolean isFull() {
        return coda.isFull();
    }
    
}
