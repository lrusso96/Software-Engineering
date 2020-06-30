package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerMain {

    public static void main(String[] args) {

        List<ClientThread> clients = new ArrayList<>();
        ServerSocket lis = null;

        try {
            lis = new ServerSocket(Integer.parseInt(args[0]));
        } catch (IOException e1) {
            System.out.println("Error: cannot create ServerSocket");
            System.exit(1);
        }
        System.out.println("Server successfully started");

        Socket sock;
        while (true) {
            try {
                sock = lis.accept();
            } catch (IOException e) {
                break;
            }
            System.out.println("Socket has been created and the connection has been accepted");
            ClientThread cl = new ClientThread(sock);
            Thread tr = new Thread(cl);
            tr.start();
            clients.add(cl);
        }
    }
}
