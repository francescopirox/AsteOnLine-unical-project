package AsteOnLine.server.controlleraste;

import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.Asta;
import AsteOnLine.shared.Offerta;
import AsteOnLine.shared.Utente;

import java.util.List;

public interface ControllerAste {

   boolean save( GestoreAsta gestoreAsta );

    List<Asta> getAllAsteAttive();

    void bindUser( Utente u , Asta a );

    void offerta( Utente u , Offerta o ) ;

    List<Asta> getAsteSegiteByUser( Utente user );

    boolean isUserBinded( Utente u , Asta a );
}
