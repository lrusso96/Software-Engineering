package client.ptp;

import client.Stock;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.Random;

import static client.Utils.setupProperties;

/**
 * Show an example of MessageProducer, who sends messages.
 */
public class StockProducer {

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
            MessageProducer producer = session.createProducer(destination);

            // start the connection
            connection.start();

            int item = 0;
            while (true) {
                item++;
                Stock stock = createStock();
                TextMessage msg = session.createTextMessage();
                msg.setStringProperty("Name", stock.getCompany());
                msg.setFloatProperty("Value", stock.getValue());
                msg.setText(String.format("Item %d: %s, Value: %s", item, stock.getCompany(), stock.getValue()));
                producer.send(msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException: " + e.getMessage());
                    break;
                }
            }
            connection.close();
        } catch (NamingException e) {
            System.out.println("NamingException: " + e.getMessage());
        } catch (JMSException e) {
            System.out.println("JMSException: " + e.getMessage());
        }
    }

    private static Stock createStock() {
        Random random = new Random();
        String[] companies = {"Fed", "orD", "ost", "oev", "ski"};
        String company = companies[random.nextInt(companies.length)];
        float value = random.nextFloat() * 100;
        return new Stock(company, value);
    }
}
