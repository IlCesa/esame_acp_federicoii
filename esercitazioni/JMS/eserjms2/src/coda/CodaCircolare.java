package coda;

public class CodaCircolare implements Coda{
    private String coda[];
    private int size;
    private int elem;
    private int head;
    private int tail;

    public CodaCircolare(int size){
        this.size = size;
        this.elem = this.head = this.tail = 0;
        coda = new String[size];
    }

    @Override
    public void put(String operation) {
        coda[tail%size] = operation;
        tail++;
        elem++;
    }

    @Override
    public String[] takeAll() {
        String temp[] = new String[elem];
        for(int i = 0; i< elem;i++){
            temp[i] = coda[head%size];
            elem--;
            head++;
        }
        

        return coda;
    }

    @Override
    public boolean isFull() {
        return this.elem == this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.elem == 0;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int getElem() {
        return this.elem;
    }
    
}
