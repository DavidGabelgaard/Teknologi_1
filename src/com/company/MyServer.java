package com.company;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Executable;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {



    public static  final int Port = 1337;

    public static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {

        try {
            ServerSocket listener = new ServerSocket(Port);


            while (true) {
                System.out.println("[Server] waiting for clients to connect");
                Socket client = listener.accept();
                System.out.println("[Server] Connected to client");
                ClientHandler clientThread = new ClientHandler(client , clients);
                clients.add(clientThread);

                pool.execute(clientThread);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
