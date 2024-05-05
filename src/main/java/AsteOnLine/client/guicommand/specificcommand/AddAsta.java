package AsteOnLine.client.guicommand.specificcommand;

import AsteOnLine.client.guicommand.Command;
import AsteOnLine.shared.*;
import AsteOnLine.shared.exceptions.IllegalFineAstaException;

import java.rmi.RemoteException;


public class AddAsta implements Command {


    private final AstaBuilder asta;
    private final ServerRemoteInterface serverRemoteInterface;
    private final ClientRemoteInterface clientRemoteInterface;

    public AddAsta( AstaBuilder asta, ServerRemoteInterface serverRemoteInterface , ClientRemoteInterface clientRemoteInterface ) {
        this.asta = asta;

        this.serverRemoteInterface = serverRemoteInterface;
        this.clientRemoteInterface = clientRemoteInterface;
    }


    @Override
    public boolean doIt() {

        try {
            serverRemoteInterface.creaAsta(asta);
        } catch ( IllegalFineAstaException e ) {
            e.printStackTrace();
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }
        return false;
    }
}
