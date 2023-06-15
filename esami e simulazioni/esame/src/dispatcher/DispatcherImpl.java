package dispatcher;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import services.IDispatcher;

public class DispatcherImpl implements IDispatcher{
    private QueueConnection queueConnection;
    private Queue reqDestination;
    private Queue resDestination;
    public DispatcherImpl(QueueConnection queueConnection, Queue reqDestination, Queue resDestination){
        this.queueConnection = queueConnection;
        this.reqDestination = reqDestination;
        this.resDestination = resDestination;
        
    }

    @Override
    public String sendDato(int year, int id) {
        //devo fare qui la richiesta alla coda
        String val = "";
        try {
            QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender queueSender = queueSession.createSender(this.reqDestination);
            TextMessage tm = queueSession.createTextMessage();
            tm.setText("forecast#"+year+"#"+id);
            queueSender.send(tm);

            QueueReceiver queueReceiver = queueSession.createReceiver(resDestination);
            TextMessage response = (TextMessage) queueReceiver.receive();
            val = response.getText();
            System.out.println("ricevuto:"+val);
            queueSession.close();
            

        } catch (JMSException e) {
            e.printStackTrace();
        }

        return val;
        
    }
}
