package com.iut.prog_reseaux.tp3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCPEchoMulti {

    private int port;
    private static final int nbClients = 10;

    public ServeurTCPEchoMulti(int port) {
        this.port = port;
    }

    public void lancer() throws IOException{
        ServerSocket serveur = new ServerSocket(port);
        System.out.println("Serveur TCP Echo lancé sur le port " + port);
        Socket client;

        for (int i = 1; i <= nbClients; i++) {
            client = serveur.accept();
            System.out.println("Client : " + client.getInetAddress().getHostAddress());
            TaskServeurEcho task = new TaskServeurEcho(client);
            new Thread(task).start();
        }
    }
}
