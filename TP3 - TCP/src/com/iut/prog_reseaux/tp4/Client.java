package com.iut.prog_reseaux.tp4;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    private String serveurFichier;
    private int port;

    public Client(String serveurFichier, int port) {
        this.serveurFichier = serveurFichier;
        this.port = port;
    }

    public void getFile(String fileName){
        try (Socket sockClient = new Socket()){
            sockClient.connect(new InetSocketAddress(serveurFichier,port));
            InputStream in = sockClient.getInputStream();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sockClient.getOutputStream()));

            sendLine(fileName, out);

            //in.transferTo(new FileOutputStream(new File("/amuhome/t17009199/Documents/IUT-ProgReseaux/TP3 - TCP/src/com/iut/prog_reseaux/tp4/image1.jpg")));

            FileOutputStream fileOutputStream = new FileOutputStream(new File("/amuhome/t17009199/Documents/ProgRepartie/TP3 - TCP/src/com/iut/prog_reseaux/tp4/image2.jpg"));
            byte buf[] = new byte[512];
            int nbOctets = 0;


            while (   (nbOctets = in.read(buf) ) != -1) {
                fileOutputStream.write(buf, 0, nbOctets);
            }

            System.out.println("fin boucle");
            fileOutputStream.close();
            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendLine(String line, BufferedWriter out) throws IOException {
        out.write(line);
        out.newLine();
        out.flush();
        System.out.println(line);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new Client("10.203.9.88", 2000).getFile("image.jpg");

    }

}
