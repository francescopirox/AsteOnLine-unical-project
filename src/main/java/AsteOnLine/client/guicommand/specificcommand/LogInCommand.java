package AsteOnLine.client.guicommand.specificcommand;

import AsteOnLine.client.guicommand.Command;
import AsteOnLine.shared.ClientRemoteInterface;
import AsteOnLine.shared.ServerRemoteInterface;

import java.rmi.RemoteException;

public class LogInCommand implements Command {

    private final String username;
    private final String password;
    private final ServerRemoteInterface server;
    private final ClientRemoteInterface clientRemoteInterface;

    public LogInCommand( String username, String password, ServerRemoteInterface server, ClientRemoteInterface clientRemoteInterface ) {


        this.username = username;
        this.password = password;
        this.server = server;
        this.clientRemoteInterface = clientRemoteInterface;
    }

    @Override
    public boolean doIt() {
        try {
            server.logIn(username,password,clientRemoteInterface);
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }
        return false;
    }
}
