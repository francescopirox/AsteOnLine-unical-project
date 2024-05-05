package AsteOnLine.server.gestoreutenti;

import AsteOnLine.server.modelli.UtenteConcreto;
import AsteOnLine.shared.Utente;

import java.util.HashMap;
import java.util.Map;

public enum GestoreUtentiConcreto implements GestoreUtenti{
    ISTANZA;

    private Map<String,Utente> utentiMap=new HashMap<String,Utente>();
    @Override
    public Utente save( Utente utente ) {
        if(utente == null)
            return null;
        if(!utente.getEmail().matches("^(.+)@(.+)$"))
            return null;
        if(utentiMap.containsKey(utente.getEmail()))
            return null;
        utentiMap.put(utente.getEmail(),utente);
        return utente;
    }

    @Override
    public boolean login( String useremail, String password ) {
        if(utentiMap.containsKey(useremail)){
            return utentiMap.get(useremail).hasSamePassword(password);
        }
        return false;
    }

    @Override
    public Utente search( String useremail, String password ) {
        if(utentiMap.containsKey(useremail)){
            if(utentiMap.get(useremail).hasSamePassword(password))
                return utentiMap.get(useremail);
        }
        return null;
    }

    @Override
    public Utente getUtente( Utente u ){
        if(utentiMap.values().contains(u))
            for(Utente user:utentiMap.values()){
                if(user.equals(u))
                    return user;
            }
        return null;
    }
}
