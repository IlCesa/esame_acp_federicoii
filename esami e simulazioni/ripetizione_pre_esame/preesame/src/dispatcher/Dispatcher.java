package dispatcher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class Dispatcher {
    public static void main(String[] args) {

        try {
            Registry rmiRegistry = LocateRegistry.getRegistry();
            DispatcherImpl disp = new DispatcherImpl();
            rmiRegistry.rebind("service", disp);
            System.out.println("[DISPATCHER] Servizio avviato.");

            Hashtable<String, String> props = new Hashtable<>();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.put(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");

            props.put("queue.messages", "messages");

            Context jndiContext = new InitialContext(props);
            //administered objects
            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory");
            Queue messagesQueue = (Queue) jndiContext.lookup("messages");

            QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
            QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            QueueSender queueSender = queueSession.createSender(messagesQueue);
            Thread.sleep(10 * 1000);
            
            for(int i = 0; i< 10; i++){

                TextMessage tx = queueSession.createTextMessage();
                int val = (int)(Math.random() * 50);
                String operation = "preleva#"+ val;
                tx.setText(operation);
                queueSender.send(tx);
                disp.notifica(operation);
            }

            queueSession.close();
            queueConnection.close();
            


            




        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
