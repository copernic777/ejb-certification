package dmnlukasik.client;

import dmnlukasik.ejb.MyMessage;

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

    private static final long serialVersionUID = 1647640647915937983L;

    @Resource(mappedName = "jms/TopicConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/TopicDestination")
    private Topic topic;

    private AtomicLong messageNumber = new AtomicLong(0);

    public MessageProducerServlet() {
        System.out.println("*** MessageProducerServlet created");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sendJmsMessage();
        PrintWriter responseWriter = response.getWriter();
        responseWriter.println("A message was sent at " + new Date());
    }

    private void sendJmsMessage() {
        MessageProducer messageProducer = null;
        Connection connection = null;
        try {
            /* Retrieve a JMS connection from the topic connection factory. */
            connection = connectionFactory.createConnection();
            /*
             * Create the JMS session; not transacted and with auto-acknowledge.
             */
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            /* Create a JMS message producer for the topic destination. */
            messageProducer = session.createProducer(topic);
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
        } catch (JMSException theException) {
            theException.printStackTrace();
        } finally {
            if (messageProducer != null) {
                try {
                    messageProducer.close();
                } catch (JMSException ignored) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ignored) {
                }
            }
        }
    }
}