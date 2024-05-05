package AsteOnLine.client;

import AsteOnLine.shared.Asta;
import AsteOnLine.shared.Utente;

public interface GUI {
    void start();

    void aggiornaAsta( Asta asta );

    void aggiungiAsta( Asta asta );

    void rimuoviAsta( Asta asta );

    void notificaLogIn( boolean b , Utente u );

    void notificaVincitore( Asta asta );

    void notificaOgettoVenduto( Asta asta , Utente acquirenteMax );

    void notificaSignUp( boolean b );

    void notificaOfferta( boolean b );

    void notificaPartecipazioneAsta( boolean b , Asta asta );
}
