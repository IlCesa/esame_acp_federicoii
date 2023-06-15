package controlstation;

import java.util.Hashtable;

import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

public class ControlStation {
    public static void main(String[] args) throws Exception {
        final int N = 20;
        String inputs[] = {"startSensor","stopSensor","read"};
        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");//61616 is the port for jms interface that uses openWire protocol
        props.put("topic.sensors", "sensors");

        Context jndiContext = new InitialContext(props);
        TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
        Topic topicDestination = (Topic) jndiContext.lookup("sensors");
        TopicConnection topicConnection = (TopicConnection) topicConnectionFactory.createConnection();
        TopicSession topicSession = (TopicSession) topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        TopicPublisher topicPublisher = topicSession.createPublisher(topicDestination);
        
        for (int i = 0; i < N; i++) {
            TextMessage tm = topicSession.createTextMessage();
            int index = (int)(Math.random() * inputs.length);
            String operation = inputs[index];
            System.out.println(operation);
            tm.setText(operation);
            topicPublisher.send(tm);
        }

        topicSession.close();
        topicConnection.close();
    
    }
}
