package AsteOnLine.client.guicommand.specificcommand;

import AsteOnLine.client.models.OffertaConcreta;
import AsteOnLine.client.RMIClientConcreto;
import AsteOnLine.client.guicommand.Command;
import AsteOnLine.shared.Asta;
import AsteOnLine.shared.ServerRemoteInterface;
import AsteOnLine.shared.Utente;

import java.rmi.RemoteException;

public class OffertaCommand implements Command {
    private final Asta asta;
    private final String text;
    private final Utente user;
    private final ServerRemoteInterface serverRemoteInterface;
    private final RMIClientConcreto callbackObj;

    public OffertaCommand( Asta asta , String text , Utente user , ServerRemoteInterface serverRemoteInterface , RMIClientConcreto callbackObj ) {

        this.asta = asta;
        this.text = text;
        this.user = user;
        this.serverRemoteInterface = serverRemoteInterface;
        this.callbackObj = callbackObj;
    }

    @Override
    public boolean doIt() {
        if(Double.valueOf(text)>asta.getValore()) {
            try {
                serverRemoteInterface.effettuaOfferta(user , new OffertaConcreta(asta , Double.valueOf(text), callbackObj));
            } catch ( RemoteException e ) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
