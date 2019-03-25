package com.iut.prog_reseaux.tp3;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCPEcho {
    private String hostname;
    private int port;

    public ClientTCPEcho(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void lancerBW(){
        try (Socket sockClient = new Socket()){
            sockClient.connect(new InetSocketAddress(hostname,port));
            BufferedReader in = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sockClient.getOutputStream()));
            Scanner sc = new Scanner(System.in);
            String message = "";
            String msgRecu;

            while (!message.toLowerCase().equals("quit")) {
                System.out.println("Entrez un message : (quit pour quitter)");
                message = sc.nextLine();
                out.write(message);
                out.newLine();
                out.flush();

                msgRecu = in.readLine();
                System.out.println(msgRecu);
            }

            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

