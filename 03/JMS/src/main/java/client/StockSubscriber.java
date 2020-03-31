package client;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

import static client.Utils.setupProperties;

/**
 * Show an example of TopicSubscriber, who subscribes to a specific topic
 * and asynchronously processes messages.
 */
public class StockSubscriber {

    public static void main(String[] args) {
        try {
            // setup the context
            Properties properties = setupProperties();
            Context jndiContext = new InitialContext(properties);

            // lookup the topic object
            String topicName = "dynamicTopics/Quotazioni";
            Topic topic = (Topic) jndiContext.lookup(topicName);

            // lookup the topic connection factory
            TopicConnectionFactory connectionFactory = (TopicConnectionFactory) jndiContext.lookup("ConnectionFactory");

            // create a topic connection
            TopicConnection connection = connectionFactory.createTopicConnection();

            // create a topic session
            TopicSession topicSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            // create a subscriber, filtering messages whose property 'Nome' is 'Barilla'
            TopicSubscriber subscriber = topicSession.createSubscriber(topic, "Nome = 'Barilla'", false);
            subscriber.setMessageListener(new ConsumerListener(true));

            // start the connection
            connection.start();
        } catch (NamingException e) {
            System.out.println("NamingException: " + e.getMessage());
        } catch (JMSException e) {
            System.out.println("JMSException: " + e.getMessage());
        }
    }
}
