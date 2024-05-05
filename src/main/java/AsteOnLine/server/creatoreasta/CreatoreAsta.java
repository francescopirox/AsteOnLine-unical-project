package AsteOnLine.server.creatoreasta;

import AsteOnLine.shared.exceptions.IllegalFineAstaException;
import AsteOnLine.shared.AstaBuilder;
import AsteOnLine.server.modelli.GestoreAsta;

public interface CreatoreAsta {
    GestoreAsta creaAsta( AstaBuilder astabuilder) throws IllegalFineAstaException;
}
