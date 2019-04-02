package com.iut.prog_reseaux.tp5_hello;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloRemoteObject extends UnicastRemoteObject implements HelloInterface {

    protected HelloRemoteObject() throws RemoteException {
        super();
    }
    public String direHello() throws RemoteException {
        return "Hello World !";
    }

}
