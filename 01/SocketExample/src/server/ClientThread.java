package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread implements Runnable {

    private Socket sock;
    private ServerThread st = null;
    private boolean running = false;

    public ClientThread(Socket s) {
        sock = s;
    }

    @Override
    public void run() {
        boolean running = true;
        Scanner in = null;
        PrintWriter pw = null;
        try {
            in = new Scanner(sock.getInputStream());
            pw = new PrintWriter(sock.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error in client thread: " + Thread.currentThread());
            return;
        }
        try {
            while (running) {
                String cmd = in.nextLine();
                System.out.println("Received command: " + cmd);
                switch (cmd) {
                    case "start":
                        st = new ServerThread();
                        Thread t = new Thread(st);
                        t.start();
                        break;
                    case "getStatus":
                        String out;
                        if (st.isRunning()) {
                            out = "running";
                        } else {
                            out = "finished";
                        }
                        pw.println(out);
                        pw.flush();
                        System.out.println("Sending: " + out);
                        break;
                    case "getResult":
                        for (int n : st.getResult()) {
                            pw.println(String.valueOf(n));
                            pw.flush();
                        }
                        running = false;
                        pw.println("###");
                        pw.flush();
                        break;
                }
            }
            sock.close();
            pw.close();
            in.close();

        } catch (Exception ex) {
            System.out.println("Error in client thread: " + Thread.currentThread());
            ex.printStackTrace();
        }
    }
}
