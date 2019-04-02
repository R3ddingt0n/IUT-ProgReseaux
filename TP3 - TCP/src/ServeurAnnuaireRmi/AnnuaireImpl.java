package ServeurAnnuaireRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import Interfaces.AnnuaireInterface;

public class AnnuaireImpl extends UnicastRemoteObject implements AnnuaireInterface {

    Map<String, Coordonnee> Annuaire;
    class Coordonnee {
        private String phoneNumber;
        private String email;
        public Coordonnee(String phoneNumber, String email) {
            this.phoneNumber = phoneNumber;
            this.email = email;
        }
    }
    protected AnnuaireImpl() throws RemoteException {
        super();
        Annuaire = new HashMap<String, Coordonnee>();
        Annuaire.put("garfield", new Coordonnee("0611223344",
                "garfield@jupiter.fr"));
        Annuaire.put("jon", new Coordonnee("0722222222",
                "jon@jupier.fr"));
        Annuaire.put("odie", new Coordonnee("0655555555",
                "odie@saturne.fr"));
    }
    public String getPhoneNumber(String user) throws RemoteException {
        return Annuaire.get(user).phoneNumber;
    }
    public String getEmail(String user) throws RemoteException {
        return Annuaire.get(user).email;
    }
}