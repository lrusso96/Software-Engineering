package com.example.exam;

public class ExamClient {
    
    public static void main(String[] args) {
        ClientListener listener = new ClientListener();
        listener.start();
    }
}
