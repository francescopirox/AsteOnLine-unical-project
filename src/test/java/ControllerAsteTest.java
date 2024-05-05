import AsteOnLine.server.controlleraste.ControllerAste;
import AsteOnLine.server.controlleraste.ControllerAsteConcreto;
import AsteOnLine.server.modelli.AstaConcreta;
import AsteOnLine.server.modelli.GestoreAstaConcreto;
import AsteOnLine.server.modelli.UtenteConcreto;
import AsteOnLine.shared.Asta;
import AsteOnLine.server.modelli.GestoreAsta;
import AsteOnLine.shared.Offerta;
import AsteOnLine.shared.Utente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerAsteTest {
    private ControllerAste controllerAste= ControllerAsteConcreto.ISTANZA;

    @Test
    @DisplayName("Test Utilizzo Normale")
    void TestSaveGestoriAsteStandardCase() throws InterruptedException {
        Utente u=new UtenteConcreto("a@b.com","pino franco","123");
        Asta a1= new AstaConcreta(new Date(System.currentTimeMillis()-60*1000),new Date(System.currentTimeMillis()+60*1000),111,0,"Art1","");
        Asta a2= new AstaConcreta(new Date(System.currentTimeMillis()-60*1000),new Date(System.currentTimeMillis()+1*1000),212,0,"Art2","");
        Asta a3= new AstaConcreta(new Date(System.currentTimeMillis()+1000),new Date(System.currentTimeMillis()+1*1000),313,0,"Art3","");

        GestoreAsta ga1=new GestoreAstaConcreto(a1,u);
        GestoreAsta ga2=new GestoreAstaConcreto(a2,u);
        GestoreAsta ga3=new GestoreAstaConcreto(a3,u);

        assertEquals(true,controllerAste.save(ga1)); //posso inserirli tutti e tre
        assertEquals(true,controllerAste.save(ga2));
        assertEquals(true,controllerAste.save(ga3));

        List<Asta> aste = controllerAste.getAllAsteAttive();

        assertEquals(true,aste.contains(a2));
        assertEquals(false,aste.contains(a3)); //partenza Posticipata
        assertEquals(true,aste.contains(a1));

        Thread.sleep(1300); //controllo di distruzione Delle aste dopo il tempo stabilito

        aste = controllerAste.getAllAsteAttive();
        assertEquals(true,aste.contains(a1));
        assertEquals(false,aste.contains(a2)); //rimozione da lista
        assertEquals(true,aste.contains(a3));

    }


    @Test
    @DisplayName("Save Asta")
    void TestSaveGestoriAsteAstaConIdGiaPresente() throws InterruptedException {
        Utente u=new UtenteConcreto("a@b.com","pino franco","");
        Asta a1= new AstaConcreta(new Date(System.currentTimeMillis()-60*1000),new Date(System.currentTimeMillis()+60*1000),5,0,"Art1","");
        Asta a2= new AstaConcreta(new Date(System.currentTimeMillis()-60*1000),new Date(System.currentTimeMillis()+1*1000),6,0,"Art2","");


        GestoreAsta ga1=new GestoreAstaConcreto(a1,u);
        GestoreAsta ga2=new GestoreAstaConcreto(a2,u);
        GestoreAsta ga3=new GestoreAstaConcreto(a2,u);

        assertEquals(true,controllerAste.save(ga1));
        assertEquals(true,controllerAste.save(ga2));
        assertEquals(false,controllerAste.save(ga3));

    }


    @Test
    @DisplayName("Effettua Offerta in Asta")
    void TestOfferta() throws InterruptedException {
        Utente u=new UtenteConcreto("a@b.com","pino franco","");
        Utente u1=new UtenteConcreto("a@b1.com","pino 1franco","");
        Asta a1= new AstaConcreta(new Date(System.currentTimeMillis()-60*1000),new Date(System.currentTimeMillis()+2*1000),1,0,"Art1","");

        GestoreAsta ga1=new GestoreAstaConcreto(a1,u);

        assertTrue(controllerAste.save(ga1)); //posso inserirli tutti e tre

        List<Asta> aste = controllerAste.getAllAsteAttive();

        assertTrue(aste.contains(a1));

        controllerAste.bindUser(u1,a1);
            controllerAste.offerta(u1 , new Offerta() {
                @Override
                public Asta getAsta() {
                    return a1;
                }

                @Override
                public double getValore() {
                    return 10;
                }
            });

        Thread.sleep(2000); //controllo di distruzione Delle aste dopo il tempo stabilito

        aste = controllerAste.getAllAsteAttive();
        assertEquals(10,ga1.getPrezzo());
        assertFalse(ga1.isAttivo());
        assertFalse(aste.contains(a1));
        assertEquals(u1,ga1.getAcquirenteMax());


    }

    @Test
    @DisplayName("Effettua offerta con ribasso")
    void TestOffertaDoppiaRibasso() throws InterruptedException {
        Utente u=new UtenteConcreto("a@b.com","pino franco","");
        Utente u1=new UtenteConcreto("a@b1.com","pino 1franco","");
        Utente u2=new UtenteConcreto("a@b2.com","pino 2franco","");
        Asta a1= new AstaConcreta(new Date(System.currentTimeMillis()-60*1000),new Date(System.currentTimeMillis()+2*1000),9,0,"Art1","");

        GestoreAsta ga1=new GestoreAstaConcreto(a1,u);

        assertTrue(controllerAste.save(ga1)); //posso inserirli tutti e tre

        List<Asta> aste = controllerAste.getAllAsteAttive();

        assertTrue(aste.contains(a1));

        controllerAste.bindUser(u1,a1);
        controllerAste.bindUser(u2,a1);

            controllerAste.offerta(u1 , new Offerta() {
                @Override
                public Asta getAsta() {
                    return a1;
                }

                @Override
                public double getValore() {
                    return 10;
                }
            });



            controllerAste.offerta(u2 , new Offerta() {
                @Override
                public Asta getAsta() {
                    return a1;
                }

                @Override
                public double getValore() {
                    return 6;
                }
            });

        Thread.sleep(3000); //controllo di distruzione Delle aste dopo il tempo stabilito

        assertEquals(10,ga1.getPrezzo());
        assertEquals(u1,ga1.getAcquirenteMax());
        assertFalse(ga1.isAttivo());



    }

    @Test
    @DisplayName("Effettua offerta con rialzo")
    void TestOffertaDoppiaRialzo() throws InterruptedException {
        Utente u=new UtenteConcreto("a@b.com","pino franco","");
        Utente u1=new UtenteConcreto("b@b.com","pino antonio","");
        Utente u2=new UtenteConcreto("b@b1.com","pino 1antonio","");
        Asta a1= new AstaConcreta(new Date(System.currentTimeMillis()-60*1000),new Date(System.currentTimeMillis()+2*1000),11,0,"Art1","");


        GestoreAsta ga1=new GestoreAstaConcreto(a1,u);

        assertTrue(controllerAste.save(ga1)); //posso inserirli tutti e tre

        List<Asta> aste = controllerAste.getAllAsteAttive();

        assertTrue(aste.contains(a1));

        controllerAste.bindUser(u,a1);

            controllerAste.offerta(u , new Offerta() {
                @Override
                public Asta getAsta() {
                    return a1;
                }

                @Override
                public double getValore() {
                    return 10;
                }
            });


        controllerAste.bindUser(u1,a1);

            controllerAste.offerta(u1 , new Offerta() {
                @Override
                public Asta getAsta() {
                    return a1;
                }

                @Override
                public double getValore() {
                    return 60;
                }
            });

        Thread.sleep(3000); //controllo di distruzione Delle aste dopo il tempo stabilito

        aste = controllerAste.getAllAsteAttive();
        assertEquals(60,ga1.getPrezzo());
        assertEquals(u1,ga1.getAcquirenteMax());
        assertFalse(ga1.isAttivo());
        assertFalse(aste.contains(a1));
    }

}
