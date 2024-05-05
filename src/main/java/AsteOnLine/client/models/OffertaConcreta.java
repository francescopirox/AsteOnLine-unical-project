package AsteOnLine.client.models;

import AsteOnLine.client.RMIClientConcreto;
import AsteOnLine.shared.Asta;
import AsteOnLine.shared.Offerta;

public class OffertaConcreta implements Offerta {
    private final Asta asta;
    private final double valore;

    public OffertaConcreta( Asta asta , double valore , RMIClientConcreto callbackObj ) {
        this.asta = asta;
        this.valore = valore;
    }

    @Override
    public Asta getAsta() {
        return asta;
    }

    @Override
    public double getValore() {
        return valore;
    }
}
