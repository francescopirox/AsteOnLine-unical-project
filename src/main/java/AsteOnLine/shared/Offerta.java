package AsteOnLine.shared;


import java.io.Serializable;

public interface Offerta extends Serializable {
    Asta getAsta() ;
    double getValore();
}
