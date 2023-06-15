package sensor;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import coda.CodaCircolare;
import coda.CodaWrapLock;

public class Sensor {
    public static void main(String[] args) {
        
        final int D = 5;
        //String inputs[] = {"startSensor","stopSensor","read"};
        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");//61616 is the port for jms interface that uses openWire protocol
        props.put("topic.sensors", "sensors");

        Context jndiContext;
        try {
            jndiContext = new InitialContext(props);
            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic topicDestination = (Topic) jndiContext.lookup("sensors");
            TopicConnection topicConnection = (TopicConnection) topicConnectionFactory.createConnection();
            topicConnection.start();
            TopicSession topicSession = (TopicSession) topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicSubscriber topicSubscriber = topicSession.createSubscriber(topicDestination);
            


            CodaWrapLock codaWrapLock = new CodaWrapLock(new CodaCircolare(D));
            SensorMessageListener sml = new SensorMessageListener(codaWrapLock);
            topicSubscriber.setMessageListener(sml);
            TExecutor tExecutor = new TExecutor(codaWrapLock);
            tExecutor.start();


        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        



    }
}
