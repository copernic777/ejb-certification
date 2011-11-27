package dmnlukasik.client;

import dmnlukasik.commons.MyMessage;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@WebServlet(name = "MessageProducerServlet", urlPatterns = "/test.do")
public class MessageProducerServlet extends HttpServlet {

    private static final long serialVersionUID = -4364474814559146703L;

    @Resource(mappedName = "jms/QueueConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/QueueDestination")
    private Queue queue;

    private AtomicLong messageNumber = new AtomicLong(0);

    public MessageProducerServlet() {
        super();
        System.out.println("*** MessageProducerServlet created");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter responseWriter = response.getWriter();

        try {
            sendJmsMessage();
            responseWriter.println("A message was sent at " + new Date());
        } catch (JMSException theException) {
            responseWriter.println("An error occurred sending message: " + theException);
        }
    }

    private void sendJmsMessage() throws JMSException {
        MessageProducer messageProducer;
        Connection connection = null;
        try {
            /* Retrieve a JMS connection from the queue connection factory. */
            connection = connectionFactory.createConnection();
            /* Create the JMS session; not transacted and with auto-acknowledge. */
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            /* Create a JMS message producer for the queue destination. */
            messageProducer = session.createProducer(queue);
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

    /**
     * Closes the supplied JMS connection if it is not null.
     * If supplied connection is null, then do nothing.
     *
     * @param connection JMS connection to close.
     */
    private void closeJmsResources(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException ignored) {
            }
        }
    }
}