package generator;


import java.rmi.RemoteException;


import services.IDispatcher;
import services.Reading;

public class GeneratorThread extends Thread{
    private final int NUM_REQUESTS;
    IDispatcher iDisp;

    final static String TYPE[] = {"temperatura","pressione"};
    public GeneratorThread(final int NUM_REQUESTS, IDispatcher iDisp){
        this.NUM_REQUESTS = NUM_REQUESTS;
        this.iDisp = iDisp;
    }

    @Override
    public void run() {

       

        for(int i = 0; i<NUM_REQUESTS;i++){

            try {
                
                int rand1 = (Math.random() > 0.5) ? 1 : 0;
                int val = (int)(Math.random() * 50);
                Reading r = new Reading(GeneratorThread.TYPE[rand1], val);
                
                iDisp.setReading(r);
                System.out.println("Generated:" +  r.toString());
    
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            /*try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
        }




    }
    
}
