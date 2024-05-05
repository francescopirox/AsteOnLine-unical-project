package AsteOnLine.server.creatoreasta;

import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.exceptions.IllegalFineAstaException;
import AsteOnLine.shared.*;

public enum CreatoreAstaConcreto implements CreatoreAsta{
    ISTANZA;

    @Override
    public GestoreAsta creaAsta( AstaBuilder astabuilder ) throws IllegalFineAstaException {
        return astabuilder.buildGestore();
    }
}
