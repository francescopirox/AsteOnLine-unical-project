package AsteOnLine.client.guicommand;

import AsteOnLine.client.guicommand.Command;

import java.rmi.RemoteException;

public class CommandHandler {


    public CommandHandler() {
    }

    public void handle( Command cmd) {
        cmd.doIt();
    }


}
