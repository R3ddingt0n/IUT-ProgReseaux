package com.iut.prog_reseaux.tp3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServeurTCPEchoPool {

    private int port;
    private static final int nbThreads = 1;
    private static final int nbClients = 3;

    public ServeurTCPEchoPool(int port) {
        this.port = port;
    }

    public void lancer() throws IOException {
        ServerSocket serveur = new ServerSocket(port);
        System.out.println("Serveur TCP Echo lancé sur le port " + port);
        Socket client;

        ExecutorService pool = Executors.newFixedThreadPool(nbThreads);

        for (int i = 1; i <= nbClients; i++) {
            client = serveur.accept();
            System.out.println("Client : " + client.getInetAddress().getHostAddress());
            TaskServeurEcho task = new TaskServeurEcho(client);
            pool.execute(task);
        }
    }
}
