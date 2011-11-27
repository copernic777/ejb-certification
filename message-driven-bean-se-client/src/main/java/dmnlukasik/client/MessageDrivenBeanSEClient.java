package dmnlukasik.client;

import dmnlukasik.commons.MyMessage;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class MessageDrivenBeanSEClient {

    private final static String JMS_CONNECTION_FACTORY_JNDI = "jms/QueueConnectionFactory";
    private final static String JMS_QUEUE_JNDI = "jms/QueueDestination";

    private ConnectionFactory connectionFactory;
    private Queue queue;
    private AtomicLong messageNumber = new AtomicLong(0);

    private void lookupJmsResources() throws NamingException {
        InitialContext theContext = new InitialContext();
        System.out.println("*** Starting JMS Resource Lookup...");

        connectionFactory = (ConnectionFactory) theContext.lookup(JMS_CONNECTION_FACTORY_JNDI);
        queue = (Queue) theContext.lookup(JMS_QUEUE_JNDI);

        System.out.println("    JMS Resource Lookup Finished.");
    }

    private void sendJmsMessage() throws JMSException {
        Connection connection = null;
        try {
            /* Retrieve a JMS connection from the queue connection factory. */
            connection = connectionFactory.createConnection();
            /* Create the JMS session; not transacted and with auto-acknowledge. */
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            /* Create a JMS message producer for the queue destination. */
            MessageProducer messageProducer = session.createProducer(queue);
            /* Create the object to be sent in the message created above. */
            MyMessage myMessage = new MyMessage();
            myMessage.setMessageNumber(messageNumber.incrementAndGet());
            myMessage.setMessageString("Hello Message Driven Beans");
            myMessage.setMessageTime(new Date());
            /* Create message used to send a Java object. */
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(myMessage);
            /* Send the message. */
            messageProducer.send(objectMessage);
        } finally {
            closeJmsResources(connection);
        }
    }

    private void closeJmsResources(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException ignored) {
            }
        }
    }

    public static void main(String[] args) {
        MessageDrivenBeanSEClient client = new MessageDrivenBeanSEClient();
        try {
            client.lookupJmsResources();

            for (int i = 0; i < 10; i++) {
                client.sendJmsMessage();
                System.out.println("### Sent message: " + (i + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("*** Java SE JMS Client finished.");
        System.exit(0);
    }
}