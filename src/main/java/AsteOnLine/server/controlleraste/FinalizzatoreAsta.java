package AsteOnLine.server.controlleraste;

import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.Asta;

import java.util.List;
import java.util.TimerTask;

public final class FinalizzatoreAsta extends TimerTask {
    private final GestoreAsta gestoreAsta;
    private final List<Asta> asteAttive;

    public FinalizzatoreAsta( GestoreAsta gestoreAsta , List<Asta> asteAttive ) {
        this.asteAttive=asteAttive;
        this.gestoreAsta=gestoreAsta;
    }

    @Override
    public void run() {
        asteAttive.remove(gestoreAsta.getAsta());
        gestoreAsta.stopAsta();
        if(gestoreAsta.getAcquirenteMax()!= null) //Asta non deserta
            gestoreAsta.getAcquirenteMax().addAstaVinta(gestoreAsta.getAsta(),gestoreAsta.getVenditore()); //salva asta vinta nel profilo dell acquirente
        gestoreAsta.getVenditore().addAstaFinita(gestoreAsta.getAsta()); //salva asta nel profilo
    }
}
