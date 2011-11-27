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
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
}, mappedName = "jms/TopicDestination", name = "TopicListener1")
public class TopicListenerEJB implements MessageListener {

    private static AtomicInteger mCurrentBeanNumber = new AtomicInteger(0);

    private int mBeanNumber = mCurrentBeanNumber.incrementAndGet();

    public TopicListenerEJB() {
        System.out.println("*** TopicListenerEJB created: " + mBeanNumber);
    }

    @Override
    public void onMessage(Message inMessage) {
        System.out.println("*** Bean " + mBeanNumber + " received message: " + inMessage);
        extractMessagePayload(inMessage);
    }

    private void extractMessagePayload(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                ObjectMessage objectMessage = (ObjectMessage) message;
                MyMessage myMessage = (MyMessage) objectMessage.getObject();

                System.out.println("Received message with number: " + myMessage.getMessageNumber());
                System.out.println("   Message string: " + myMessage.getMessageString());
                System.out.println("   Message time: " + myMessage.getMessageTime());
            } catch (JMSException theException) {
                System.out.println("An error occurred retrieving message payload: " + theException);
            }
        }
    }
}