package AsteOnLine.server;

import AsteOnLine.shared.ServerRemoteInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public final class RMIServer  {

    public static void main( String[] args) throws RemoteException, AlreadyBoundException {
        final ServerRemoteInterface server=Core.getCore();
        Registry registry= LocateRegistry.createRegistry(1099);
        registry.bind("AOLServer",server);
        System.out.println("Server Started");
        }

}
