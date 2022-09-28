package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable {

    private Socket server;
    private BufferedReader in;



    public ServerConnection (Socket server) {
        this.server = server;
        try {
            this.in = new BufferedReader(new InputStreamReader(this.server.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {


            try {
                while (true) {
                    String serverResponse = in.readLine();

                    if (serverResponse == null) break;
                    System.out.println(serverResponse);
                }
            }catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }








    }
}
