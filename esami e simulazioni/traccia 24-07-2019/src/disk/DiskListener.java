package disk;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class DiskListener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        MapMessage mp = (MapMessage)message;
        try {
            int porto = mp.getInt("porto");
            int dato = mp.getInt("dato");
            DiskThread diskThread = new DiskThread(dato,porto);
            diskThread.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    
}
