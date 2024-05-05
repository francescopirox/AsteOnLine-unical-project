package AsteOnLine.shared;

import java.io.Serializable;
import java.util.Date;

public interface Asta extends Serializable{
    Date getInizio() ;
    Date getFine() ;
    long getId() ;
    double getValorePartenza() ;
    String getNomeArticolo() ;
    String getDescrizione();
    double getValore();
    void setValore( double valore );
}
