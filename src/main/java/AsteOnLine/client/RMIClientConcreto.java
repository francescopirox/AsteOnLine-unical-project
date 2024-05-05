package AsteOnLine.client;

import AsteOnLine.shared.Asta;
import AsteOnLine.shared.ClientRemoteInterface;
import AsteOnLine.shared.ServerRemoteInterface;
import AsteOnLine.shared.Utente;

import javax.swing.*;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public enum RMIClientConcreto implements ClientRemoteInterface {
    ISTANZA;
    private GUI gui;

    public static void main( String[] args ) throws RemoteException, NotBoundException {
        RMIClientConcreto.ISTANZA.start();
    }

    public void start() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        try {
            ServerRemoteInterface serverRemoteInterface = (ServerRemoteInterface) registry.lookup("AOLServer");
            RMIClientConcreto callbackObj = this;
            UnicastRemoteObject.exportObject(callbackObj,0);
            gui= GUIconcreta.getGui(serverRemoteInterface,callbackObj);
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    gui.start();
                }
            });
        }
        catch ( ConnectException ce ) {
            JOptionPane.showMessageDialog(null , "Server non in funzione");
        }
    }

    @Override
    public void riceviNotifica( String vincitore_asta ) {
        System.out.println(vincitore_asta);
    }

    @Override
    public void logInCallback( boolean loggedIn, Utente user ) {
        gui.notificaLogIn(loggedIn,user);
    }

    @Override
    public void notificaPartecipazioneAsta( boolean b, Asta asta ) {
        gui.notificaPartecipazioneAsta(b,asta);

    }

    @Override
    public void riceviNotificaAggiornamentoAsta( Asta asta ) throws RemoteException {
        gui.aggiornaAsta(asta);
    }

    @Override
    public void offertaCallBack(boolean b){
        gui.notificaOfferta(b);
    }

    @Override
    public void riceviNotificaInizioAsta( Asta asta ) throws RemoteException {
        gui.aggiungiAsta(asta);
    }

    @Override
    public void riceviNotificaFineAsta( Asta asta ) throws RemoteException {
        gui.rimuoviAsta(asta);
    }

    @Override
    public void riceviNotificaVincitoreAsta( Asta asta ) throws RemoteException {
        gui.notificaVincitore(asta);
    }

    @Override
    public void riceviNotificaOggettoVenduto( Asta asta , Utente acquirenteMax ) throws RemoteException {
        gui.notificaOgettoVenduto(asta,acquirenteMax);
    }

    @Override
    public void notificaSignup( boolean b ) throws RemoteException {
        gui.notificaSignUp(b);
    }
}
