package com.iut.prog_reseaux.tp3;

public class MainClient2 {
    public static void main(String[] args) {
        ClientTCPEcho client = new ClientTCPEcho("10.203.9.148", 50001);
        client.lancerBW();
    }
}