package com.iut.prog_reseaux.tp3;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSmtp {
    private String serveurSmtp;
    private int port;
    private String hostname;

    public ClientSmtp(String serveurSmtp, String hostname, int port) {
        this.serveurSmtp = serveurSmtp;
        this.hostname = hostname;
        this.port = port;
    }

    public boolean sendMail(String from, String to, String subject, String body){
        try (Socket sockClient = new Socket()){
            sockClient.connect(new InetSocketAddress(serveurSmtp,port));
            BufferedReader in = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sockClient.getOutputStream()));
            String msgRecu;

            getLines(in);

            sendLine("EHLO " + hostname, out);

            msgRecu = in.readLine();
            getLines(in);

            sendLine("MAIL FROM: " + from, out);
            getLines(in);

            sendLine("RCPT TO: " + to, out);
            getLines(in);

            sendLine("DATA", out);
            getLines(in);

            sendLine("FROM: " + from + "\n" + "TO: " + to + "\n" + "SUBJECT: " + subject + "\n" + body + "\n" + ".", out);
            getLines(in);

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

    private void getLines(BufferedReader in) throws IOException {
        String msgRecu;
        while(true){
            msgRecu = in.readLine();
            System.out.println(msgRecu);
            if(msgRecu.charAt(3) != '-'){
                if(msgRecu.charAt(0) == '4' || msgRecu.charAt(0) == '5')
                    throw new RuntimeException();
                break;
            }
            if(msgRecu.charAt(0) == '4' || msgRecu.charAt(0) == '5')
                throw new RuntimeException();
        }
    }
}
