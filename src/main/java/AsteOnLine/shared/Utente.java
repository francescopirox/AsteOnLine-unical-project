package AsteOnLine.shared;

import java.io.Serializable;
import java.util.List;

public interface Utente extends Serializable {

    String getEmail();

    String getNome();

    List<AstaUtente> getAsteVinte();

    List<Asta> getAsteIniziate();

    boolean hasSamePassword( String password );

    void addAstaVinta( Asta asta , Utente acquirenteMax );

    void addAstaFinita( Asta asta );

    void addAstaIniziata( Asta asta );
}
