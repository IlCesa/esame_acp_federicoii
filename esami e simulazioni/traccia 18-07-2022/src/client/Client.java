package client;

import java.util.Hashtable;

import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Client {
    public static void main(String[] args) throws Exception {
        if(args.length != 1) throw new IllegalArgumentException("Serve almeno 1 parametro");
        String argTopic = args[0];
        System.out.println(argTopic);

        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");
        props.put("topic."+argTopic,argTopic);
       
        Context jndiContext = new InitialContext(props);
        TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
        Topic topic = (Topic) jndiContext.lookup(argTopic);
        TopicConnection tConn = (TopicConnection) topicConnectionFactory.createConnection();
        TopicSession topicSession = (TopicSession) tConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicPublisher topicPublisher = topicSession.createPublisher(topic);


        for(int i = 0; i<5;i++){
            MapMessage mm = topicSession.createMapMessage();
            int val1 = (int) (Math.random() * 100);
            if(argTopic.compareTo("math")==0){
                int val2 = (int) (Math.random() * 100);
                mm.setInt("val1", val1);
                mm.setInt("val2", val2);
            }else if(argTopic.compareTo("text")==0){
                mm.setString("string", "save#" + val1);
            }else{
                System.out.println("Nessun topic corretto trovato!");
            }
            topicPublisher.publish(mm);
        }

        //topicPublisher.close();
        topicSession.close();
        tConn.close();

    }
}
