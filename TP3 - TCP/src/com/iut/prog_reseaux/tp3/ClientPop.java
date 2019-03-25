package com.iut.prog_reseaux.tp3;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientPop {
    private String serveurPop;
    private int port;
    private String username;
    private String password;

    public ClientPop(String serveurPop, int port, String username, String password) {
        this.serveurPop = serveurPop;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public boolean getMail(int mailNumber){
        try (Socket sockClient = new Socket()){
            sockClient.connect(new InetSocketAddress(serveurPop,port));
            BufferedReader in = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sockClient.getOutputStream()));

            getLine(in);
            sendLine("USER " + username, out);

            getLine(in);
            sendLine("PASS " + password, out);

            getLine(in);
            sendLine("RETR " + mailNumber, out);

            getLine(in);
            getLines(in);

            sendLine("QUIT", out);
            getLine(in);


            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void sendLine(String line, BufferedWriter out) throws IOException {
        out.write(line);
        out.newLine();
        out.flush();
        System.out.println(line);
    }

    private void getLine(BufferedReader in) throws IOException {
        String msgRecu = in.readLine();
        System.out.println(msgRecu);
        if(msgRecu.charAt(0) == '-')
            throw new RuntimeException();
    }

    private void getLines(BufferedReader in) throws IOException {
        String msgRecu;
        while(true){
            msgRecu = in.readLine();
            System.out.println(msgRecu);
            if(msgRecu.equals(".")){
                break;
            }
        }
    }
}
