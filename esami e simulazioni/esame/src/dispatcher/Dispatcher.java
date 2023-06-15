package dispatcher;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Dispatcher {
    public static void main(String[] args) {
        Hashtable<String, String> props = new Hashtable<>();
        
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");
        props.put("queue.req", "req");
        props.put("queue.res", "res");

        try {
            Context jndiContext = new InitialContext(props);
            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory");
            Queue reqDestination = (Queue) jndiContext.lookup("req");
            Queue resDestination = (Queue) jndiContext.lookup("res");
            QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
            queueConnection.start();
            System.out.println("CodeJMS inizializzate...");

            //QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            DispatcherImpl di = new DispatcherImpl(queueConnection, reqDestination, resDestination);
            DispatcherSkeletonTcp dtp = new DispatcherSkeletonTcp(di);
            dtp.runSkeleton();//avvio il server tcp
            System.out.println("TCPSERVER avviato sulla porta 8080");
            
            
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }


        
    }
}
