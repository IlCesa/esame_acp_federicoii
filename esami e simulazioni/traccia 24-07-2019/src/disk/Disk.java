package disk;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Disk {
    public static void main(String[] args) {

        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");
        props.put("queue.storage","storage");

        try {
            Context jndiContext = new InitialContext(props);
            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory)jndiContext.lookup("QueueConnectionFactory");
            Queue storage = (Queue) jndiContext.lookup("storage");

            QueueConnection queueConnection = (QueueConnection) queueConnectionFactory.createConnection();
            queueConnection.start();
            
            QueueSession queueSession = (QueueSession) queueConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            QueueReceiver queueReceiver = queueSession.createReceiver(storage);

            DiskListener diskListener = new DiskListener();
            queueReceiver.setMessageListener(diskListener);

           /*/ queueReceiver.close();
            queueSession.close();
            queueConnection.close();*/

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
