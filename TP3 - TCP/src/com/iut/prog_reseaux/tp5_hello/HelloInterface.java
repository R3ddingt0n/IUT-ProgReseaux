package com.iut.prog_reseaux.tp5_hello;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloInterface extends Remote {

    String direHello() throws RemoteException;

}