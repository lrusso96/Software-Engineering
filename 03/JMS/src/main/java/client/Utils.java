package client;

import javax.naming.Context;
import java.util.Properties;

public class Utils {

    /**
     * Initializes the properties to connect to the local server.
     *
     * @return the properties
     */
    public static Properties setupProperties() {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");
        return props;
    }
}
