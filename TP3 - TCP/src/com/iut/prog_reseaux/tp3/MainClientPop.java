package com.iut.prog_reseaux.tp3;

public class MainClientPop {
    public static void main(String[] args) {
        ClientPop clientPop = new ClientPop("139.124.187.23", 110, "trouin", "trouin");
        clientPop.getMail(1);
    }
}
