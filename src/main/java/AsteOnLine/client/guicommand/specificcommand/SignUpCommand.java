package AsteOnLine.client.guicommand.specificcommand;

import AsteOnLine.client.guicommand.Command;
import AsteOnLine.shared.ClientRemoteInterface;
import AsteOnLine.shared.ServerRemoteInterface;
import AsteOnLine.shared.exceptions.IllegalClientException;

import java.rmi.RemoteException;

public class SignUpCommand implements Command {

    private final ServerRemoteInterface serverRemoteInterface;
    private final ClientRemoteInterface clientRemoteInterface;
    private final String email;
    private final String nome_cognome;
    private final String password;

    public SignUpCommand(String email, String nome_cognome, String password,ServerRemoteInterface serverRemoteInterface, ClientRemoteInterface clientRemoteInterface ) {

        this.serverRemoteInterface = serverRemoteInterface;
        this.clientRemoteInterface = clientRemoteInterface;
        this.email=email;
        this.password=password;
        this.nome_cognome=nome_cognome;
    }

    @Override
    public boolean doIt() {

        try {
             serverRemoteInterface.aggiungiUtente(email, nome_cognome ,password,clientRemoteInterface);
            return false;
        } catch ( IllegalClientException | RemoteException e ) {
            e.printStackTrace();
        }
        return true;
    }
}
