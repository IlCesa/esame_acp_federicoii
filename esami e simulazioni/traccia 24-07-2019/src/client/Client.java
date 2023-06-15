package client;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Client {
    public static void main(String[] args) {
        if(args.length != 2){ 
            throw new IllegalArgumentException("Exactly 2 parameters required !");
        }


        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");
        props.put("queue.storage","storage");

        try {
            Context jndiContext = new InitialContext(props);
            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory)jndiContext.lookup("QueueConnectionFactory");
            Queue storage = (Queue) jndiContext.lookup("storage");

            QueueConnection queueConnection = (QueueConnection) queueConnectionFactory.createConnection();
            
            QueueSession queueSession = (QueueSession) queueConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            QueueSender queueSender = queueSession.createSender(storage);

            MapMessage mm = queueSession.createMapMessage();
            int dato = Integer.parseInt(args[0]);
            int porto = Integer.parseInt(args[1]);
            if(dato >=0 && dato <=100){
                mm.setInt("dato", dato);
                mm.setInt("porto", porto);
                queueSender.send(mm);
                System.out.println("Inviato MapMessage: {dato: " + dato + ", porto: " + porto +"}");

            }else{
                System.err.println("Il dato non è valido, non posso inviare.");
            }


            queueSender.close();
            queueSession.close();
            queueConnection.close();

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
