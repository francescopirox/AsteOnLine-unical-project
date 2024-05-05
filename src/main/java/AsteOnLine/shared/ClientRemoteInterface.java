package AsteOnLine.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRemoteInterface extends Remote {

    void riceviNotifica( String vincitore_asta ) throws RemoteException;

    void logInCallback( boolean b ,Utente utente) throws RemoteException;

    void notificaPartecipazioneAsta( boolean b, Asta asta ) throws RemoteException;

    void riceviNotificaAggiornamentoAsta( Asta asta ) throws RemoteException;

    void offertaCallBack( boolean b ) throws RemoteException;

    void riceviNotificaInizioAsta( Asta asta ) throws  RemoteException;

    void riceviNotificaFineAsta( Asta asta ) throws RemoteException;

    void riceviNotificaVincitoreAsta( Asta asta ) throws RemoteException;

    void riceviNotificaOggettoVenduto( Asta asta , Utente acquirenteMax ) throws RemoteException;

    void notificaSignup( boolean b ) throws RemoteException;
}
