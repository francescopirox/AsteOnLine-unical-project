package AsteOnLine.server.gestorenotifiche;

import AsteOnLine.shared.Asta;
import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.ClientRemoteInterface;
import AsteOnLine.shared.Utente;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public enum GestoreNotificheConcreto implements GestoreNotifiche {
    ISTANZA;

    private Map<Utente,ClientRemoteInterface> utentiMap=new HashMap<>();

    @Override
    public void notificaFineAsta( GestoreAsta gestoreAsta ) {
        for(Utente u: gestoreAsta.getUtenti()){
            if( utentiMap.containsKey(u) ) {
                ClientRemoteInterface cli = utentiMap.get(u);
                try {
                    cli.riceviNotificaFineAsta(gestoreAsta.getAsta()); //Notifica di fine asta a tutti gli utenti
                } catch ( RemoteException e ) {
                    e.printStackTrace();
                }
            }
        }
        if(utentiMap.containsKey(gestoreAsta.getAcquirenteMax()) && gestoreAsta.getAcquirenteMax()!=null){
            ClientRemoteInterface cli=utentiMap.get(gestoreAsta.getAcquirenteMax());
            try {
                cli.riceviNotificaVincitoreAsta(gestoreAsta.getAsta()); //notifica al vincitore
            } catch ( RemoteException e ) {
                e.printStackTrace();
            }
        }
        if(utentiMap.containsKey(gestoreAsta.getVenditore()) && gestoreAsta.getVenditore() != null){
            ClientRemoteInterface cli=utentiMap.get(gestoreAsta.getVenditore());
            try {
                cli.riceviNotificaOggettoVenduto(gestoreAsta.getAsta(),gestoreAsta.getAcquirenteMax()); //notifica venditore
            } catch ( RemoteException e ) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void notificaLogin( boolean b , Utente u , ClientRemoteInterface cli ) {
            try {
                cli.logInCallback(b , u);
            } catch ( RemoteException e ) {
                e.printStackTrace();
            }
    }

    @Override
    public void aggiornamentoAsta( GestoreAsta gestoreAsta ) {
        if(gestoreAsta != null)
        for(Utente u: gestoreAsta.getUtenti()){
            if( utentiMap.containsKey(u) ) {
                ClientRemoteInterface cli = utentiMap.get(u);
                try {
                    cli.riceviNotificaAggiornamentoAsta(gestoreAsta.getAsta());
                } catch ( RemoteException e ) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void notificaEsitoOfferta( boolean accettata, Utente u  ) {
        if(utentiMap.containsKey(u)) {
            ClientRemoteInterface cli = utentiMap.get(u);
            try {
                cli.offertaCallBack(accettata);
            } catch ( RemoteException e ) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void notificaInizioAsta( GestoreAsta gestoreAstaConcreto ) {
        for(ClientRemoteInterface cli: utentiMap.values()){
            try {
                if(cli!=null);
                cli.riceviNotificaInizioAsta(gestoreAstaConcreto.getAsta());
            } catch ( RemoteException e ) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void notificaEsitoSignUp( boolean b , ClientRemoteInterface cli ) {
        try {
            cli.notificaSignup(b);
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void notificaEsitoPartecipazione( boolean b , Asta a , Utente u ) {
        try {
            if(u!= null && utentiMap.containsKey(u)) {
                ClientRemoteInterface cli = utentiMap.get(u);
                cli.notificaPartecipazioneAsta(b , a);
            }
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void aggiungi( Utente u , ClientRemoteInterface cli ) {
        if(cli != null)
            utentiMap.put(u,cli);
    }

    @Override
    public boolean rimuovi( Utente utente ) {
        if( utente != null)
            return utentiMap.remove(utente)!= null;
        return false;
    }
}