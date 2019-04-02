package com.iut.prog_reseaux.tp5_hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientHello {

    public static void main(String args[]) {
        try {
            String adrServeur = "localhost";
            Registry registre = LocateRegistry.getRegistry(adrServeur);
            HelloInterface stub = (HelloInterface) registre.lookup("Hello");
            String msg = stub.direHello();
            System.out.println(msg);
        } catch (Exception e) {e.printStackTrace();
        }
    }
}