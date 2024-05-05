package AsteOnLine.shared;

import AsteOnLine.shared.exceptions.IllegalClientException;
import AsteOnLine.shared.exceptions.IllegalFineAstaException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerRemoteInterface extends Remote {

    Asta creaAsta(AstaBuilder astaBuilder) throws RemoteException, IllegalFineAstaException;

    List<Asta> getAllAsteAttive() throws RemoteException;

    //void aggiungiUtente( Utente utente, ClientRemoteInterface cli) throws RemoteException, IllegalClientException;

    boolean eliminaUtente( Utente utente) throws RemoteException;

    void partecipaAsta( Utente u , Asta a ) throws RemoteException;

    void effettuaOfferta( Utente u, Offerta o)throws RemoteException;

    void logIn( String username , String password, ClientRemoteInterface clientRemoteInterface ) throws RemoteException;

    List<Asta> getAllAsteSeguiteByUser( Utente user) throws RemoteException;

    void logOut( Utente user  ) throws RemoteException;

    List<AstaUtente> getAllAsteVinteByUser( Utente user ) throws RemoteException;

    List<Asta> getAllAsteAttiveByUser( Utente user ) throws RemoteException;

    void aggiungiUtente( String email , String nome_cognome , String password , ClientRemoteInterface clientRemoteInterface ) throws IllegalClientException, RemoteException;
}
