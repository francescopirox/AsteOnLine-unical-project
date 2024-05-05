package AsteOnLine.server.controlleraste;

import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.Asta;

import java.util.List;
import java.util.TimerTask;

public final class InizializzatoreAsta extends TimerTask {
    private final GestoreAsta gestoreAsta;
    private final List<Asta> asteAttive;

    public InizializzatoreAsta( GestoreAsta gestoreAsta , List<Asta> asteAttive ) {
        this.asteAttive=asteAttive;
        this.gestoreAsta=gestoreAsta;
    }

    @Override
    public void run() {
        this.asteAttive.add(gestoreAsta.getAsta());
        gestoreAsta.startAsta();
    }
}
