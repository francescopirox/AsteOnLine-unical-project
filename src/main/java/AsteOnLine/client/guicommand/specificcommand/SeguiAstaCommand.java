package AsteOnLine.client.guicommand.specificcommand;

import AsteOnLine.client.RMIClientConcreto;
import AsteOnLine.client.guicommand.Command;
import AsteOnLine.shared.Asta;
import AsteOnLine.shared.ServerRemoteInterface;
import AsteOnLine.shared.Utente;

import java.rmi.RemoteException;

public class SeguiAstaCommand implements Command {
    public SeguiAstaCommand( Asta asta , Utente user , ServerRemoteInterface serverRemoteInterface , RMIClientConcreto callbackObj ) {
        try {
            serverRemoteInterface.partecipaAsta(user,asta);
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }

    }
}
