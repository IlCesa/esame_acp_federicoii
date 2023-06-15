package coda;

public interface Coda {
    public abstract void put(String operation);
    public abstract String[] takeAll();
    public abstract boolean isFull();
    public abstract boolean isEmpty();
    public abstract int getSize();
    public abstract int getElem();
}
