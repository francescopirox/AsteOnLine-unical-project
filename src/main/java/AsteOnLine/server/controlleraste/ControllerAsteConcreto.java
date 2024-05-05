package AsteOnLine.server.controlleraste;

import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.Asta;
import AsteOnLine.shared.Offerta;
import AsteOnLine.shared.Utente;

import java.util.*;

public enum ControllerAsteConcreto implements ControllerAste{
    ISTANZA;

    private final List<Asta> asteAttive = Collections.synchronizedList(new LinkedList<>()); //syn to norm
    private final Map<Long, GestoreAsta> gestoriAste =Collections.synchronizedMap(new HashMap<>());
    private final Timer timer=new Timer();

    @Override
    public List<Asta> getAllAsteAttive() {
        return asteAttive;
    }

    @Override
    public void bindUser( Utente u , Asta a ) {
        gestoriAste.get(a.getId()).aggiungiUtente(u); //Asta <-> Utente
    }

    @Override
    public void offerta( Utente u , Offerta o )  {
        gestoriAste.get(o.getAsta().getId()).effettuaOfferta(o.getValore(),u);
    }

    @Override
    public List<Asta> getAsteSegiteByUser( Utente user ) {
        LinkedList<Asta> ret= new LinkedList<Asta>();
        if(user != null){
            for(GestoreAsta ga :gestoriAste.values()){
                if(ga.getUtenti().contains(user)){
                    ret.add(ga.getAsta());
                }
            }
        }
        return ret;
    }

    @Override
    public boolean save( GestoreAsta gestoreAsta ) {
        if(gestoreAsta != null) //protegge null pointer ex
        try {
            if( gestoriAste.containsKey(gestoreAsta.getAsta().getId()) )  //controlla che non ci sia già un asta con lo stesso id
                return false;
            gestoriAste.put(gestoreAsta.getAsta().getId() , gestoreAsta);
            if( gestoreAsta.getInizio().before(new Date(System.currentTimeMillis())) ) {//se l'inizio dell'asta è prima del momento in cui si invia l0asta allora si fa partie subito
                asteAttive.add(gestoreAsta.getAsta());
                gestoreAsta.startAsta();
            } else {
                timer.schedule(new InizializzatoreAsta(gestoreAsta , asteAttive) , gestoreAsta.getInizio()); //altrimenti si scheedula l'inizio
            }
            timer.schedule(new FinalizzatoreAsta(gestoreAsta , asteAttive) , gestoreAsta.getFine()); // schedulazione finale
            return true;
        }
        catch ( IllegalStateException iae ){
            return false;
        }
        return false;
    }

    @Override
    public boolean isUserBinded( Utente u , Asta a ) {
        return gestoriAste.get(a.getId()).getUtenti().contains(u);
    }
}
