package client;

public class Node {
    public static void main(String[] args) {

       final int T = 5;
       Thread tList[] = new Thread[T];
       for (int i = 0; i < tList.length; i++) {
            tList[i] = new NodeThread();
            tList[i].start();
       }

    }
}
