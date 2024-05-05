package AsteOnLine.server.modelli;

import AsteOnLine.shared.Asta;
import AsteOnLine.shared.Utente;

import java.util.Date;
import java.util.Set;

public interface GestoreAsta {
    Utente getVenditore();
    Utente getAcquirenteMax();
    Asta getAsta();
    Date getFine();
    Date getInizio();
    double getPrezzo();
    boolean isAttivo();
    void startAsta();
    void stopAsta();
    void aggiungiUtente( Utente u );

    void effettuaOfferta( double valore , Utente u );
    Set<Utente> getUtenti();


}
