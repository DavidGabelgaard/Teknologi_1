package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private static final  String ip = "10.200.130.33";
    private static final int serverPort = 1234;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(ip , serverPort);

            ServerConnection serverCon = new ServerConnection(socket);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in) );

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Please enter your username");

            out.println(keyboard.readLine());

            System.out.println("You are now connected to the chat");

            new Thread(serverCon).start();



            while (true) {

                String command = keyboard.readLine();

                if (command.equals("quit")) {
                    break;
                }

                out.println(command);

            }

            socket.close();
            System.exit(0);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
