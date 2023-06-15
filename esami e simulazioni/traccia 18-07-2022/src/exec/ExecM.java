package exec;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import services.IStorage;

public class ExecM {
    public static void main(String[] args) {
        //String argTopic = "math";
        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");
        props.put("topic.math", "math");

        try {
            Context jndiContext = new InitialContext(props);
            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory)jndiContext.lookup("TopicConnectionFactory");
            Topic topic = (Topic) jndiContext.lookup("math");
            TopicConnection topicConnection = (TopicConnection) topicConnectionFactory.createConnection();
            topicConnection.start();
            TopicSession topicSession = (TopicSession) topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer topicCunsumer = topicSession.createConsumer(topic);

            topicCunsumer.setMessageListener(new MessageListener() {

                @Override
                public void onMessage(Message message) {
                    MapMessage mm = (MapMessage) message;
                    try {
                        int val1 = mm.getInt("val1");
                        int val2 = mm.getInt("val2");
                        System.out.println(val1 + " " + val2);
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                int result = val1 * val2;
                                
                                try {
                                    Registry rmRegistry = LocateRegistry.getRegistry();
                                    IStorage is = (IStorage) rmRegistry.lookup("service");
                                    is.store("result", result);
                                } catch (AccessException e) {
                                    e.printStackTrace();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                } catch (NotBoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            
                        }).start();
                        


                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                   
                }
                
            });

            
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        
    }
}
