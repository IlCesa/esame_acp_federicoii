package sensor;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import coda.Coda;

public class SensorMessageListener implements MessageListener{
    private Coda coda;
    public SensorMessageListener(Coda coda){
        this.coda = coda;
    }
    @Override
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        
        try {
            String operation = tm.getText();
            System.out.println(operation);
            TManager tManager = new TManager(coda, operation);
            tManager.start();
            
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
}
