package ServeurAnnuaireRmi;

import Interfaces.AnnuaireInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AnnuaireClient {

    public static void main(String args[]) {
        try {
            String adrServeur = "10.203.9.88";
            Registry registre = LocateRegistry.getRegistry(adrServeur);
            AnnuaireInterface stub = (AnnuaireInterface) registre.lookup("annuaire");
            String garfieldEmail = stub.getEmail("garfield");
            System.out.println(garfieldEmail);
            String jonPhone = stub.getPhoneNumber("jon");
            System.out.println(jonPhone);
            String odieEmail = stub.getEmail("odie");
            System.out.println(odieEmail);
        } catch (Exception e) {e.printStackTrace();
        }
    }
}