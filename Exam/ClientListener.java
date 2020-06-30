package com.example.exam;

import java.util.Properties;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ClientListener implements MessageListener {
    private TopicConnection connection;
    
    public ClientListener() {
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
            Context ctx = new InitialContext(props);

            ConnectionFactory connectionFactory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
            connection = (TopicConnection)connectionFactory.createConnection();
            TopicSession session = (TopicSession)connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = (Destination)ctx.lookup("dynamicTopics/professors");
            TopicSubscriber subscriber = session.createSubscriber((Topic)destination);
            subscriber.setMessageListener(this);
        } catch (JMSException err) {
            err.printStackTrace();
        } catch (NamingException err) {
            err.printStackTrace();
        }
    }
    
    public void onMessage(Message msg) {
        try {
            String id = msg.getStringProperty("id");
            
            it.sapienza.softeng.exam.InterfaceImplService service = new it.sapienza.softeng.exam.InterfaceImplService();
            it.sapienza.softeng.exam.Interface port = service.getInterfaceImplPort();
            it.sapienza.softeng.exam.Professor result = port.getDetails(id);
            
            System.out.println("Professor " + id + " is " + result.getName() + " " + result.getSurname() + " and teaches " + result.getCourse());
        } catch (JMSException err) {
            err.printStackTrace();
        }
    }
    
    public void start() {
        try {
            connection.start();
        } catch (JMSException err) {
            err.printStackTrace();
        }
    }
    
    public void stop() {
        try {
            connection.stop();
        } catch (JMSException err) {
            err.printStackTrace();
        }
    }
}
