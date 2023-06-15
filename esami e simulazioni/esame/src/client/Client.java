package client;


public class Client {
    public static void main(String[] args) throws Exception {
        /*ExecutorService ex = Executors.newFixedThreadPool(10);
        for(int i = 0; i< 10; i++){
            ex.execute(new Runnable() {

                @Override
                public void run() {

                }
                
            });
        }*/
        DispatcherProxyTcp dpt = new DispatcherProxyTcp();
        int id = (int)(Math.random() * 10000);
        int year = 1900 + (int)(Math.random() * (2400-1900));
        String prediction = dpt.sendDato(year, id);
        System.out.println(prediction);
    }
}
