package AsteOnLine.shared;

import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.exceptions.IllegalFineAstaException;

import java.io.Serializable;
import java.util.Date;

public interface AstaBuilder extends Serializable {

    AstaBuilder dataInizio( Date data );

    AstaBuilder valorePartenza(double valore);

    AstaBuilder descrizione(String descrizione);
    
    GestoreAsta buildGestore() throws IllegalFineAstaException;

    AstaBuilder setVenditore( Utente venditore );

    Utente getVenditore();
}
