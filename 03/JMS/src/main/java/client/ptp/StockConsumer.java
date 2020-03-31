package client.ptp;

import client.ConsumerListener;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

import static client.Utils.setupProperties;

/**
 * Show an example of MessageConsumer, who asynchronously processes messages.
 */
public class StockConsumer {

    public static void main(String[] args) {
        try {
            // setup the context
            Properties properties = setupProperties();
            Context jndiContext = new InitialContext(properties);

            // lookup a destination object
            String destinationName = "dynamicTopics/Stocks";
            Destination destination = (Destination) jndiContext.lookup(destinationName);

            // look up for the connection factory
            ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");

            // create a connection
            Connection connection = connectionFactory.createConnection();

            // create a session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // create a simple Consumer
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new ConsumerListener(false));

            // start the connection
            connection.start();
        } catch (NamingException e) {
            System.out.println("NamingException: " + e.getMessage());
        } catch (JMSException e) {
            System.out.println("JMSException: " + e.getMessage());
        }
    }
}
