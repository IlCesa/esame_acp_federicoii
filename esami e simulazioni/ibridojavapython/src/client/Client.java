package client;

import java.util.Hashtable;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Client {
    public static void main(String[] args) throws Exception {
        //inviare i messaggi con jms
        //riceverli con proxyskeleton in udp

        final int N = 10;
        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");

        props.put("queue.req", "req");

        Context jndiContext = new InitialContext(props);
        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory");
        Queue requestDestination = (Queue) jndiContext.lookup("req");

        QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
        QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

        QueueSender queueSender = queueSession.createSender(requestDestination);

        for (int i = 0; i < N; i++) {
            TextMessage tx = queueSession.createTextMessage();
            int val = (int)(Math.random() * 50);
            tx.setText((Math.random() > 0.5 ? "deposita" : "preleva") + "#" + val);
            queueSender.send(tx);
        }

        queueSession.close();
        queueConnection.close();


        ServerImpl si = new ServerImpl();
        si.runSkeleton();


    }
}
