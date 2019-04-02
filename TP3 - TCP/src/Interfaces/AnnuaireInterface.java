package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AnnuaireInterface extends Remote {

    String getPhoneNumber(String user) throws RemoteException;
    String getEmail(String user) throws RemoteException;

}