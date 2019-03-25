package com.iut.prog_reseaux.tp4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurFichier {

    private int port;
    private int nbClients;

    public ServeurFichier(int port, int nbClients) {
        this.port =  port ;
        this.nbClients = nbClients;
    }

    public void lancer() {
        try {
            ServerSocket serveur = new ServerSocket(port);

            for (int i = 1; i<=nbClients; i++) {

                Socket client = serveur.accept();
                ThreadServeur thread = new ThreadServeur(client);
                thread.start();

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new ServeurFichier(2000, 10).lancer();

    }

}