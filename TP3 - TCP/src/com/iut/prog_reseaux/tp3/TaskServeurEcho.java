package com.iut.prog_reseaux.tp3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TaskServeurEcho implements Runnable {

    private Socket client;

    public TaskServeurEcho(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        BufferedWriter out;
        BufferedReader in;
        try {
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String message = "";

            while ((message = in.readLine()) != null){

                if(message.toUpperCase().equals("QUIT"))
                    break;

                System.out.println("Message client : " + message);

                message = message.toUpperCase();
                out.write(message);
                out.newLine();
                out.flush();
            }
            in.close();
            out.close();
            client.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}