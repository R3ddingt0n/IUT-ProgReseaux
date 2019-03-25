package com.iut.prog_reseaux.tp3;

import java.io.IOException;

public class MainServeur {
    public static void main(String[] args) {
        ServeurTCPEchoMulti serveurMulti = new ServeurTCPEchoMulti(50001);
        ServeurTCPEchoPool serveurPool = new ServeurTCPEchoPool(50001);

        try {
            serveurPool.lancer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
