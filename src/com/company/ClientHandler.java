package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ref.PhantomReference;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Objects;

public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;
    private String username;
    private String testname;

    public ClientHandler (Socket clientSocket , ArrayList<ClientHandler> clients) {
        this.client = clientSocket;
        this.clients = clients;
        try {
            this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        try {


            while (true) {
                testname  = in.readLine();
                boolean flag = false;
                for (ClientHandler handler: clients ) {
                    if (testname.equals(handler.username)) {
                        flag = true;
                        out.println("Username is already taken please enter a new username");
                        break;
                    }
                }
                if (!flag) break;
            }



            username = testname;


            for (ClientHandler handler: clients) {
                if (!Objects.equals(this.username, handler.username) && handler.username != null) {
                    handler.out.println("Server: " + username + " has connected to the chat");
                }

            }



            while (true) {
                String request = in.readLine();

                if (request.equals("/online")) {
                    out.println("Online chatters:");
                    for (ClientHandler handler: clients ) {
                        if (handler.username != null) {
                        out.println("- " + handler.username);
                    }}
                } else if (request.startsWith("/msg")) {
                    String reciever = request.substring(5);
                    boolean flag = false;
                    for (ClientHandler handler : clients) {

                        if (handler.username.equals(reciever)) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        out.println("User not found");
                    } else {
                        out.println("To " + reciever + ":");
                        String privateMsg = in.readLine();

                        if (privateMsg.startsWith("/cancel")) {
                            break;
                        }

                        for (ClientHandler handler : clients) {

                            if (handler.username.equals(reciever)) {
                                handler.out.println("From " + username + ": " + privateMsg);
                            }

                        }

                    }
                } else {



                    for (ClientHandler handler : clients) {
                        if (!Objects.equals(this.username, handler.username) && handler.username != null) {
                            handler.out.println(username + ": " + request);
                        }

                    }

                }
                System.out.println(request);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("[Server] test sent... closing");

            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }






    }

}
