package AsteOnLine.server.memorizzatoreAsta;

import AsteOnLine.shared.Asta;

import java.util.*;

public enum MemorizzatoreAsteConcreto implements MemorizzatoreAste{
    ISTANZA;
    private static final Map<Long, Asta> aste= new HashMap<>();

    public boolean save( Asta asta ) {
        if(aste.containsKey(asta.getId())){
            return false;
        }
        aste.put(asta.getId(),asta);
        return true;
    }
}
