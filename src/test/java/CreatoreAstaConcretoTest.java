import AsteOnLine.server.creatoreasta.CreatoreAsta;
import AsteOnLine.server.creatoreasta.CreatoreAstaConcreto;
import AsteOnLine.shared.exceptions.AstaBuilderException;
import AsteOnLine.shared.exceptions.IllegalFineAstaException;
import AsteOnLine.server.modelli.UtenteConcreto;
import AsteOnLine.shared.AstaBuilder;
import AsteOnLine.shared.AstaBuilderConcreto;
import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.Utente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CreatoreAstaConcretoTest {

    private final CreatoreAsta creatoreAsta= CreatoreAstaConcreto.ISTANZA;

    @Test
    @SuppressWarnings("*")
    @DisplayName("Simple creation")
    public void testCreation() throws AstaBuilderException, IllegalFineAstaException {
        Date fine = new Date(System.currentTimeMillis()+1000*60);
        Utente utente = new UtenteConcreto("a@b.com ","pino carmelo","");
        AstaBuilderConcreto ab= new AstaBuilderConcreto("articolo",fine,utente);
        GestoreAsta ga=creatoreAsta.creaAsta(ab);
        assertTrue(ga instanceof GestoreAsta); //ControlliSulGestoreASta
        assertFalse(ga.isAttivo()); //non lo attiva il creatore
        assertEquals(0,ga.getPrezzo(),0.0000001);
        assertEquals(utente,ga.getVenditore());

        assertEquals(0,ga.getAsta().getValorePartenza(),0.0000001);
        assertEquals("",ga.getAsta().getDescrizione());
        assertEquals("articolo",ga.getAsta().getNomeArticolo());
        assertEquals(fine,ga.getAsta().getFine());

    }

    @Test
    @DisplayName("Test exception throwing IllegalArgument")
    public void testCreationWithFineNull(){
        Date fine = null;
        Utente utente = new UtenteConcreto("a@b.com ","pino carmelo","");

        assertThrows(AstaBuilderException.class,() ->{ AstaBuilder ab= new AstaBuilderConcreto("articolo",fine,utente); creatoreAsta.creaAsta(ab);});

    }

    @Test
    @DisplayName("Test exception throwing")
    public void testCreationWithUtenteNull(){
        Date fine = new Date(System.currentTimeMillis()+1000*60);
        Utente utente = null;

        assertThrows(AstaBuilderException.class,() ->{ AstaBuilder ab= new AstaBuilderConcreto("articolo",fine,utente); creatoreAsta.creaAsta(ab);});

    }

    @Test
    @DisplayName("Test exception throwing")
    public void testCreationWithArticoloNull(){
        Date fine = new Date(System.currentTimeMillis()+1000*60);
        Utente utente = new UtenteConcreto("a@b.com ","pino carmelo","");

        assertThrows(AstaBuilderException.class,() ->{AstaBuilder ab= new AstaBuilderConcreto(null,fine,utente); creatoreAsta.creaAsta(ab);});

    }

    @Test
    @DisplayName("Test exception throwing IllegalArgument")
    public void testCreationWithValorePartenzaNegativo(){
        Date fine = new Date(System.currentTimeMillis()+1000*60);
        Utente utente = new UtenteConcreto("a@b.com ","pino carmelo","");

        assertThrows(IllegalArgumentException.class,() ->{ AstaBuilder ab= new AstaBuilderConcreto("articolo",fine,utente).valorePartenza(-1); creatoreAsta.creaAsta(ab);});

    }

    @Test
    @DisplayName("Test exception throwing IllegalArgument")
    public void testCreationWithValorePartenzaZero() throws AstaBuilderException, IllegalFineAstaException {
        Date fine = new Date(System.currentTimeMillis()+1000*60);

        Utente utente = new UtenteConcreto("a@b.com ","pino carmelo","");

        AstaBuilder ab= new AstaBuilderConcreto("articolo",fine,utente);
        ab.valorePartenza(0);
        GestoreAsta ga=creatoreAsta.creaAsta(ab);
        assertTrue(ga instanceof GestoreAsta);
        assertFalse(ga.isAttivo()); //non lo attiva il creatore
        assertEquals(0,ga.getPrezzo(),0.0000001);
        assertEquals(utente,ga.getVenditore());

        assertEquals(0,ga.getAsta().getValorePartenza(),0.0000001);
        assertEquals("",ga.getAsta().getDescrizione());
        assertEquals("articolo",ga.getAsta().getNomeArticolo());
        assertEquals(fine,ga.getAsta().getFine());

    }


    @Test
    @DisplayName("Test timeStop ")
    public void testCreationWithTempoFinePassato() throws AstaBuilderException, IllegalFineAstaException {
        Date fine = new Date(System.currentTimeMillis()-1000*60);
        Utente utente = new UtenteConcreto("a@b.com ","pino carmelo","");
        AstaBuilder ab= new AstaBuilderConcreto("articolo",fine,utente);

        assertThrows(IllegalFineAstaException.class,() -> creatoreAsta.creaAsta(ab));

    }

    @Test
    @DisplayName("Test timeStart ")
    public void testCreationWithTempoInizioPassato() throws AstaBuilderException, IllegalFineAstaException {
        Date inizio = new Date(System.currentTimeMillis()-1000*60);
        Date fine = new Date(System.currentTimeMillis()+1000*60);
        Utente utente = new UtenteConcreto("a@b.com ","pino carmelo","");
        AstaBuilder ab= new AstaBuilderConcreto("articolo",fine,utente);
        ab.dataInizio(inizio);
        GestoreAsta ga = creatoreAsta.creaAsta(ab);

        assertTrue(ga instanceof GestoreAsta);
        assertFalse(ga.isAttivo()); //non lo attiva il creatore
        assertEquals(0,ga.getPrezzo(),0.0000001);
        assertEquals(utente,ga.getVenditore());

        assertEquals(0,ga.getAsta().getValorePartenza(),0.0000001);
        assertEquals("",ga.getAsta().getDescrizione());
        assertEquals("articolo",ga.getAsta().getNomeArticolo());
        assertEquals(fine,ga.getAsta().getFine());
        assertEquals(inizio,ga.getAsta().getInizio());


    }

    @Test
    @DisplayName("Test descrizione ")
    public void testCreationWithDescrizione() throws AstaBuilderException, IllegalFineAstaException {

        Date fine = new Date(System.currentTimeMillis()+1000*60);
        Utente utente = new UtenteConcreto("a@b.com ","pino carmelo","");
        AstaBuilder ab= new AstaBuilderConcreto("articolo",fine,utente);
        ab.descrizione("AAA");
        GestoreAsta ga=creatoreAsta.creaAsta(ab);
        assertTrue(ga instanceof GestoreAsta);
        assertFalse(ga.isAttivo()); //non lo attiva il creatore
        assertEquals(0,ga.getPrezzo(),0.0000001);
        assertEquals(utente,ga.getVenditore());

        assertEquals(0,ga.getAsta().getValorePartenza(),0.0000001);
        assertEquals("AAA",ga.getAsta().getDescrizione());
        assertEquals("articolo",ga.getAsta().getNomeArticolo());
        assertEquals(fine,ga.getAsta().getFine());

    }

    @Test
    @DisplayName("Test exception throwing IllegalArgument")
    public void testCreationWithDescrizioneNull(){
        Date fine = new Date(System.currentTimeMillis()+1000*60);
        Utente utente = new UtenteConcreto("a@b.com ","pino carmelo","");

        assertThrows(IllegalArgumentException.class,() ->{ AstaBuilder ab= new AstaBuilderConcreto("articolo",fine,utente).descrizione(null); creatoreAsta.creaAsta(ab);});
    }

}//testClass



