package AsteOnLine.client.guicommand;

import java.rmi.RemoteException;

public interface Command {

    default boolean doIt()  {
        throw new NoSuchMethodError();
    }
    default boolean undoIt(){
        throw new NoSuchMethodError();
    }
}
