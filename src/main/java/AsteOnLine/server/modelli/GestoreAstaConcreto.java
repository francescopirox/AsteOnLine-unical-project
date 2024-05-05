package AsteOnLine.server.modelli;

import AsteOnLine.server.gestorenotifiche.GestoreNotifiche;
import AsteOnLine.server.gestorenotifiche.GestoreNotificheConcreto;
import AsteOnLine.shared.Asta;
import AsteOnLine.shared.Utente;

import java.util.*;

public final class GestoreAstaConcreto implements GestoreAsta {
    private final Utente venditore;
    private final Asta asta;
    private final Set<Utente> utenti;
    private Utente acquirenteMax;
    private double prezzo;
    private boolean isAttivo;

    private final GestoreNotifiche gestoreNotifiche= GestoreNotificheConcreto.ISTANZA;

    public GestoreAstaConcreto( Asta asta , Utente venditore ) {
        this.asta=asta;
        this.venditore=venditore;
        this.acquirenteMax=null;
        this.prezzo=0;
        this.isAttivo=false;
        utenti= new HashSet<>();
    }//GestoreAsta

    public Set<Utente> getUtenti() {
        return utenti;
    }

    public Utente getVenditore() {
        return venditore;
    }

    public Utente getAcquirenteMax() {
        return acquirenteMax;
    }

    public Asta getAsta() {
        return asta;
    }

    public Date getFine(){
        return asta.getFine();
    }

    @Override
    public Date getInizio() {
        return asta.getInizio();
    }

    public double getPrezzo(){
        return prezzo;
    }

    public boolean isAttivo() {
        return isAttivo;
    }

    @Override
    public void startAsta() {
        this.isAttivo=true;
        gestoreNotifiche.notificaInizioAsta(this);
        venditore.addAstaIniziata(asta);
    }

    @Override
    public void stopAsta() {
        this.isAttivo=false;
        gestoreNotifiche.notificaFineAsta(this);
    }

    @Override
    public void aggiungiUtente( Utente u ) {
        if(isAttivo)
        utenti.add(u);
    }

    @Override
    public void effettuaOfferta( double valore , Utente u ) {
        if(isAttivo && valore>0 && valore>= asta.getValorePartenza() && valore>prezzo && !u.equals(venditore)) {
            prezzo = valore;
            acquirenteMax=u;
            this.asta.setValore(valore);
            gestoreNotifiche.aggiornamentoAsta(this);
            gestoreNotifiche.notificaEsitoOfferta(true,u);
        }
        else {
            gestoreNotifiche.notificaEsitoOfferta( false,u);
        }
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;
        GestoreAstaConcreto that = (GestoreAstaConcreto) o;
        return Objects.equals(asta , that.asta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asta);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GestoreAstaConcreto{");
        sb.append("venditore=").append(venditore);
        sb.append(", asta=").append(asta);
        sb.append(", acquirenteMax=").append(acquirenteMax);
        sb.append(", prezzo=").append(prezzo);
        sb.append(", isAttivo=").append(isAttivo);
        sb.append('}');
        return sb.toString();
    }
}
