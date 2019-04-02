package ServeurAnnuaireRmi;

import java.net.*;
import java.rmi.*;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class AnnuaireServer {

    public static String getAdresseLocale() throws
            SocketException {
        InetAddress adr = null;
        Enumeration<InetAddress> listAdr =
                NetworkInterface.getByName("eth0").getInetAddresses();
        while (listAdr.hasMoreElements()) {
            adr = listAdr.nextElement();
            if (adr instanceof Inet4Address)
                break;
        }
        return adr.getHostAddress();
    }

    public static void main(String[] args) throws RemoteException, InterruptedException, NotBoundException, UnknownHostException, SocketException {
        // Pour communiquer entre client et serveur sur machines
        // différentes, il faut positionner le hostname du serveur à son @
        // IP. Pour avoir cette @ IP, on peut utiliser la valeur de ifconfig
        // comme on peut la reccupérer automatiquement avec la fonction
        // getAdresseLocale()

        Properties prop = System.getProperties();
        prop.put("java.rmi.server.hostname", getAdresseLocale());
        // Creer une instance de l'objet distant
        AnnuaireImpl obj = new AnnuaireImpl();
        // lancer le registre RMI sur la machine locale et
        // sur le port 1099
        Registry registre;
        registre = LocateRegistry.createRegistry(1099);

        // publier l'objet distant sur le registre avec la string "annuaire"
        // et se mettre en attente des requetes.
        registre.rebind("annuaire", obj);
        System.out.println("Serveur annuaire RMI pret !");
    }
}
