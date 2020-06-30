package server;

import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        ServerImpl implementor = new ServerImpl();
        String address = "http://localhost:8080/SoapInterface";
        Endpoint.publish(address, implementor);
        while (true) { }
    }
}