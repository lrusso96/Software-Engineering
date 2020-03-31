package client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Enumeration;

/**
 * Whenever a message arrives at the destination, the JMS provider
 * delivers the message by calling the listener's onMessage() method.
 */
public class ConsumerListener implements MessageListener {

    private final boolean verbose;

    public ConsumerListener(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Processes the current message and prints its content.
     * If verbose is true, it also prints the properties of the message.
     *
     * @param message the message retrieved.
     */
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage msg = (TextMessage) message;
                System.out.println("Reading message: " + msg.getText());
                if (verbose) printProperties(msg);
            } else
                System.out.println("Message of wrong type: " + message.getClass().getName());
        } catch (JMSException e) {
            System.out.println("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            System.out.println("Exception in onMessage():" + t.getMessage());
        }
    }

    /**
     * Prints the properties of the message.
     *
     * @param message a TextMessage
     */
    private void printProperties(TextMessage message) {
        try {
            Enumeration enumeration = message.getPropertyNames();
            if (enumeration != null) {
                while (enumeration.hasMoreElements()) {
                    String key = (String) enumeration.nextElement();
                    Object value = message.getObjectProperty(key);
                    System.out.println(key + " " + value.toString());
                }
            }
        } catch (JMSException e) {
            System.out.println("JMSException in printProperties(): " + e.toString());
        }
    }
}