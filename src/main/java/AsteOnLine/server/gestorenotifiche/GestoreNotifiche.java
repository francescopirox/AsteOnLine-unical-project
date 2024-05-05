package AsteOnLine.server.gestorenotifiche;

import AsteOnLine.shared.Asta;
import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.ClientRemoteInterface;
import AsteOnLine.shared.Utente;

public interface
GestoreNotifiche {

    void notificaFineAsta( GestoreAsta gestoreAsta );

    void aggiornamentoAsta( GestoreAsta gestoreAsta );

    void notificaEsitoOfferta( boolean esito ,Utente u );

    void notificaInizioAsta( GestoreAsta gestoreAsta );

    void notificaEsitoSignUp( boolean b , ClientRemoteInterface cli );

    void notificaEsitoPartecipazione( boolean b , Asta a , Utente u );

    void notificaLogin( boolean b , Utente u , ClientRemoteInterface cli );

    void aggiungi( Utente u , ClientRemoteInterface cli );

    boolean rimuovi( Utente utente );
}
