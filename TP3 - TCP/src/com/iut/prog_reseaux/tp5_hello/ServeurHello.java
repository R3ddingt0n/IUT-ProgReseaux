package com.iut.prog_reseaux.tp5_hello;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class ServeurHello {

    public static void main(String args[]) {
        HelloRemoteObject obj;
        try {
            Properties properties = System.getProperties();
//            properties.put("java.rmi.server.hostname","ntrouin");
            obj = new HelloRemoteObject();
//            Registry registre = LocateRegistry.getRegistry();
            Registry registre = LocateRegistry.createRegistry(1099);
            registre.rebind("Hello", obj);
            System.out.println("Ready");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
