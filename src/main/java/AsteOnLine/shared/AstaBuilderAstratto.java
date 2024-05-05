package AsteOnLine.shared;

import AsteOnLine.shared.exceptions.AstaBuilderException;

import java.util.Date;
import java.util.Objects;

public abstract class AstaBuilderAstratto implements AstaBuilder {
    private String nomeArticolo;
    private Date fineAsta;
    private Utente venditore;

    public AstaBuilderAstratto( String nomeArticolo, Date fineAsta, Utente venditore) throws AstaBuilderException {
        if(nomeArticolo==null|| fineAsta==null || venditore==null) {
            throw new AstaBuilderException("");
        }
        this.nomeArticolo=nomeArticolo;
        this.fineAsta=fineAsta;
        this.venditore=venditore;
    }

    public Date getFineAsta() {
        return fineAsta;
    }

    public String getNomeArticolo() {
        return nomeArticolo;
    }

    public Utente getVenditore() {
        return venditore;
    }

    @Override
    public AstaBuilder setVenditore( Utente venditore ) {
        this.venditore = venditore;
        return this;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;
        AstaBuilderAstratto that = (AstaBuilderAstratto) o;
        return Objects.equals(nomeArticolo , that.nomeArticolo) && Objects.equals(fineAsta , that.fineAsta) && Objects.equals(venditore , that.venditore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeArticolo , fineAsta , venditore);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AstaBuilderAstratto{");
        sb.append("nomeArticolo='").append(nomeArticolo).append('\'');
        sb.append(", fineAsta=").append(fineAsta);
        sb.append(", venditore=").append(venditore);
        sb.append('}');
        return sb.toString();
    }
}
