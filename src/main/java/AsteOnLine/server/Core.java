package AsteOnLine.server;

import AsteOnLine.server.controlleraste.ControllerAste;
import AsteOnLine.server.controlleraste.ControllerAsteConcreto;
import AsteOnLine.server.creatoreasta.CreatoreAsta;
import AsteOnLine.server.creatoreasta.CreatoreAstaConcreto;
import AsteOnLine.server.memorizzatoreAsta.MemorizzatoreAste;
import AsteOnLine.server.modelli.UtenteConcreto;
import AsteOnLine.shared.exceptions.IllegalClientException;
import AsteOnLine.shared.exceptions.IllegalFineAstaException;
import AsteOnLine.server.gestorenotifiche.GestoreNotifiche;
import AsteOnLine.server.gestorenotifiche.GestoreNotificheConcreto;
import AsteOnLine.server.gestoreutenti.GestoreUtenti;
import AsteOnLine.server.gestoreutenti.GestoreUtentiConcreto;
import AsteOnLine.server.memorizzatoreAsta.MemorizzatoreAsteConcreto;
import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public final class Core extends UnicastRemoteObject implements ServerRemoteInterface  {

    private final CreatoreAsta creatoreAsta= CreatoreAstaConcreto.ISTANZA;
    private final MemorizzatoreAste memorizzatoreAste= MemorizzatoreAsteConcreto.ISTANZA;
    private final ControllerAste controllerAste= ControllerAsteConcreto.ISTANZA;
    private final GestoreUtenti gestoreUtenti= GestoreUtentiConcreto.ISTANZA;
    private final GestoreNotifiche gestoreNotifiche= GestoreNotificheConcreto.ISTANZA;
    public static Core istanza;
    private Core() throws RemoteException{
    }

    public static Core getCore(){
        if(istanza==null) {
            try {
                istanza=new Core();
            } catch ( RemoteException e ) {
                System.out.println("Errore nella generazione del Core");
            }
        }
        return istanza;
    }

    @Override
    public Asta creaAsta( AstaBuilder astaBuilder ) throws IllegalFineAstaException {
        Utente venditore=gestoreUtenti.getUtente(astaBuilder.getVenditore());
        astaBuilder.setVenditore(venditore);
        GestoreAsta gestoreAsta=creatoreAsta.creaAsta(astaBuilder);
        if(!memorizzatoreAste.save(gestoreAsta.getAsta())){ return null;}
        if(!controllerAste.save(gestoreAsta)){return null;}
        return gestoreAsta.getAsta();
    }

    @Override
    public List<Asta> getAllAsteAttive(){
        return controllerAste.getAllAsteAttive();
    }

    @Override
    public boolean eliminaUtente( Utente utente  ) {
        return gestoreNotifiche.rimuovi(utente);
    }

    @Override
    public void partecipaAsta( Utente u , Asta a ){
        if(a != null && u != null )
        if(!controllerAste.isUserBinded(u,a)) {
            controllerAste.bindUser(gestoreUtenti.getUtente(u) , a);
            gestoreNotifiche.notificaEsitoPartecipazione(true,a,gestoreUtenti.getUtente(u));
        }else{
            gestoreNotifiche.notificaEsitoPartecipazione(false,null,gestoreUtenti.getUtente(u));
        }
    }

    @Override
    public void effettuaOfferta( Utente u , Offerta o ) {
        if(u !=null && o !=null)
            controllerAste.offerta(gestoreUtenti.getUtente(u),o);
    }

    @Override
    public void logIn( String username , String password , ClientRemoteInterface clientRemoteInterface){
        if(username != null && password != null  && clientRemoteInterface != null)
        if( gestoreUtenti.login(username, password)){
            gestoreNotifiche.notificaLogin(true , gestoreUtenti.search(username , password), clientRemoteInterface);
            gestoreNotifiche.aggiungi(gestoreUtenti.search(username,password),clientRemoteInterface);

        }else{
            gestoreNotifiche.notificaLogin( false , null, clientRemoteInterface);
        }
    }

    @Override
    public List<Asta> getAllAsteSeguiteByUser( Utente user ) {
        if(user != null)
            return controllerAste.getAsteSegiteByUser(user);
        return null;
    }

    @Override
    public void logOut( Utente user  ) {
        if(user != null)
            gestoreNotifiche.rimuovi(user);
    }

    @Override
    public List<AstaUtente> getAllAsteVinteByUser( Utente user )  {
        if(user != null)
            return gestoreUtenti.getUtente(user).getAsteVinte();
        return null;
    }

    @Override
    public List<Asta> getAllAsteAttiveByUser( Utente user )  {
        if(user != null)
            return gestoreUtenti.getUtente(user).getAsteIniziate();
        return null;
    }

    @Override
    public void aggiungiUtente( String email , String username , String password , ClientRemoteInterface cli ) throws IllegalClientException {
        if(cli==null)
            throw new IllegalClientException("");
        if(email == null || username==null || password==null)
            throw new IllegalArgumentException();
        Utente utenteSalvato=gestoreUtenti.save(new UtenteConcreto(email,username,password));
        if(utenteSalvato != null){
            gestoreNotifiche.aggiungi(utenteSalvato,cli);
            gestoreNotifiche.notificaEsitoSignUp(true,cli);
            return;
        }
        gestoreNotifiche.notificaEsitoSignUp(false,cli);
        return;

    }
}