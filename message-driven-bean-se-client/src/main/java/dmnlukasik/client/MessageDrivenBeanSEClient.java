package dmnlukasik.client;

import dmnlukasik.commons.MyMessage;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class MessageDrivenBeanSEClient {

    private final static String JMS_CONNECTIONFACTORY_JNDI = "jms/QueueConnectionFactory";
    private final static String JMS_JMS_QUEUEDESTINATION_JNDI = "jms/QueueDestination";

    private ConnectionFactory mQueueConnectionFactory;
    private Queue mQueueDestination;
    private AtomicLong mMessageNumber = new AtomicLong(0);

    private void lookupJmsResources() throws NamingException {
        InitialContext theContext = new InitialContext();
        System.out.println("*** Starting JMS Resource Lookup...");

        mQueueConnectionFactory = (ConnectionFactory) theContext.lookup(JMS_CONNECTIONFACTORY_JNDI);
        mQueueDestination = (Queue) theContext.lookup(JMS_JMS_QUEUEDESTINATION_JNDI);

        System.out.println("    JMS Resource Lookup Finished.");
    }

    private void sendJmsMessage() throws JMSException {
        MessageProducer theJMSMessageProducer;
        Connection theJMSConnection = null;
        try {
            /* Retrieve a JMS connection from the queue connection factory. */
            theJMSConnection = mQueueConnectionFactory.createConnection();
            /* Create the JMS session; not transacted and with auto-acknowledge. */
            Session theJMSSession = theJMSConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            /* Create a JMS message producer for the queue destination. */
            theJMSMessageProducer = theJMSSession.createProducer(mQueueDestination);
            /* Create the object to be sent in the message created above. */
            MyMessage theObjectToSend = new MyMessage();
            theObjectToSend.setMessageNumber(mMessageNumber.incrementAndGet());
            theObjectToSend.setMessageString("Hello Message Driven Beans");
            theObjectToSend.setMessageTime(new Date());
            /* Create message used to send a Java object. */
            ObjectMessage theJmsObjectMessage = theJMSSession.createObjectMessage();
            theJmsObjectMessage.setObject(theObjectToSend);
            /* Send the message. */
            theJMSMessageProducer.send(theJmsObjectMessage);
        } finally {
            closeJmsResources(theJMSConnection);
        }
    }

    private void closeJmsResources(Connection inJMSConnection) {
        if (inJMSConnection != null) {
            try {
                inJMSConnection.close();
            } catch (JMSException theException) {
                // Ignore exceptions.
            }
        }
    }

    public static void main(String[] args) {
        MessageDrivenBeanSEClient theClient = new MessageDrivenBeanSEClient();
        try {
            theClient.lookupJmsResources();

            for (int i = 0; i < 10; i++) {
                theClient.sendJmsMessage();
                System.out.println("### Sent message: " + (i + 1));
            }
        } catch (Exception theException) {
            theException.printStackTrace();
        }

        System.out.println("*** Java SE JMS Client finished.");
        System.exit(0);
    }
}