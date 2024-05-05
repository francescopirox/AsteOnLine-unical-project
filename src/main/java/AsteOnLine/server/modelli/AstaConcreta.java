package AsteOnLine.server.modelli;

import AsteOnLine.shared.Asta;

import java.util.Date;
import java.util.Objects;

public final class AstaConcreta implements Asta {
    private final Date inizio;
    private final Date fine;
    private final long id;
    private final double valorePartenza;
    private final String nomeArticolo;
    private final String descrizione;
    private double valore =0.0;


    public AstaConcreta( Date inizio , Date fine , long id , double valorePartenza , String nomeArticolo , String descrizione ) {
        this.inizio = inizio;
        this.fine = fine;
        this.id = id;
        this.valorePartenza = valorePartenza;
        this.nomeArticolo = nomeArticolo;
        this.descrizione = descrizione;
    }

    public Date getInizio() {
        return inizio;
    }

    public Date getFine() {
        return fine;
    }

    public long getId() {
        return id;
    }

    public double getValorePartenza() {
        return valorePartenza;
    }

    public String getNomeArticolo() {
        return nomeArticolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setValore( double valore ) {
        this.valore = valore;
    }

    @Override
    public double getValore() {
        return valore;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;
        AstaConcreta that = (AstaConcreta) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb;
        sb = new StringBuilder("AstaConcreta{");
        sb.append("inizio=").append(inizio);
        sb.append(", fine=").append(fine);
        sb.append(", id=").append(id);
        sb.append(", valorePartenza=").append(valorePartenza);
        sb.append(", nomeArticolo='").append(nomeArticolo).append('\'');
        sb.append(", descrizione='").append(descrizione).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
