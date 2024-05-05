package AsteOnLine.server.gestoreutenti;

import AsteOnLine.shared.Utente;

public interface GestoreUtenti {
    Utente save( Utente utente);

    boolean login( String username , String password );

    Utente search( String useremail , String password );

    Utente getUtente( Utente u );

}
