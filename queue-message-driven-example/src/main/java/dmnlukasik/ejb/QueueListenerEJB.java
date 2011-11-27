package dmnlukasik.ejb;

import dmnlukasik.commons.MyMessage;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.concurrent.atomic.AtomicInteger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
}, mappedName = "jms/QueueDestination", name = "QueueListener1")
public class QueueListenerEJB implements MessageListener {

    private static AtomicInteger mCurrentBeanNumber = new AtomicInteger(0);

    private int mBeanNumber = mCurrentBeanNumber.incrementAndGet();


    public QueueListenerEJB() {
        System.out.println("*** QueueListenerEJB created: " + mBeanNumber);
    }

    @Override
    public void onMessage(Message inMessage) {
        System.out.println("*** Bean " + mBeanNumber + " received message: " + inMessage);
        extractMessagePayload(inMessage);
    }

    private void extractMessagePayload(Message inMessage) {
        if (inMessage instanceof ObjectMessage) {
            try {
                ObjectMessage theObjMsg = (ObjectMessage) inMessage;
                MyMessage theMsgPayload = (MyMessage) theObjMsg.getObject();
                System.out.println("Received message with number: " + theMsgPayload.getMessageNumber());
                System.out.println("   Message string: " + theMsgPayload.getMessageString());
                System.out.println("   Message time: " + theMsgPayload.getMessageTime());
            } catch (JMSException theException) {
                System.out.println("An error occurred retrieving message payload: " + theException);
            }
        }
    }
}