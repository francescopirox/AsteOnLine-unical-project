package AsteOnLine.shared;

import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.exceptions.AstaBuilderException;
import AsteOnLine.shared.exceptions.IllegalFineAstaException;
import AsteOnLine.server.modelli.AstaConcreta;
import AsteOnLine.server.modelli.GestoreAstaConcreto;

import java.util.Date;

public final class AstaBuilderConcreto extends AstaBuilderAstratto {
    private Date dataInizio=new Date(System.currentTimeMillis());
    private double valorePartenza=0.0;
    private String descrizione="";

    public AstaBuilderConcreto( String nomeArticolo , Date fineAsta , Utente venditore ) throws AstaBuilderException {
        super(nomeArticolo , fineAsta , venditore);
    }

    @Override
    public AstaBuilder dataInizio( Date dataInizio ) {
        if(dataInizio == null) {
            throw new IllegalArgumentException();
        }else {
            this.dataInizio = dataInizio;
        }
        return this;
    }

    @Override
    public AstaBuilder valorePartenza( double valore ) {
        if(valore >= 0) {
            this.valorePartenza = valore;
        }
        else{
            throw new IllegalArgumentException();
        }
        return this;
    }

    @Override
    public AstaBuilder descrizione( String descrizione ) {
        if(descrizione!=null) {
            this.descrizione = descrizione;
        }
        else {
            throw new IllegalArgumentException();
        }
        return this;
    }

    @Override
    public GestoreAsta buildGestore() throws IllegalFineAstaException {
        Asta a = new AstaConcreta(dataInizio,super.getFineAsta(),super.hashCode(),valorePartenza,super.getNomeArticolo(),descrizione);
        a.setValore(valorePartenza);
        if(a.getFine().before(new Date(System.currentTimeMillis())))
            throw new IllegalFineAstaException("");
        return new GestoreAstaConcreto(a,super.getVenditore());
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AstaBuilderConcreto{");
        sb.append(super.toString());
        sb.append("dataInizio=").append(dataInizio);
        sb.append(", valorePartenza=").append(valorePartenza);
        sb.append(", descrizione='").append(descrizione).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
