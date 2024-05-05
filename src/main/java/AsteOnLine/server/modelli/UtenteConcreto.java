package AsteOnLine.server.modelli;

import AsteOnLine.shared.Asta;
import AsteOnLine.shared.AstaUtente;
import AsteOnLine.shared.Utente;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class UtenteConcreto implements Utente
{
    private String email;
    private String username;
    private String password;
    private List<AstaUtente> asteVinte;
    private List<Asta> asteIniziate;

    public UtenteConcreto( String email , String username , String password ) {
        this.email = email;
        this.username = username;
        this.password=password;
        asteIniziate=new LinkedList<>();
        asteVinte=new LinkedList<>();
    }
    public String getPassword(){
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNome(){
        return username;
    }

    @Override
    public List<AstaUtente> getAsteVinte() {
        return asteVinte;
    }

    @Override
    public List<Asta> getAsteIniziate() {
        return asteIniziate;
    }

    @Override
    public boolean hasSamePassword( String password ) {
        return this.password.equals(password);
    }

    @Override
    public void addAstaVinta( Asta asta, Utente venditore  ) {
        asteVinte.add(new AstaUtenteConcreto(asta,venditore));
    }

    @Override
    public void addAstaFinita( Asta asta ) {
        //asteIniziate.add(asta);
    }

    @Override
    public void addAstaIniziata( Asta asta ) {
        asteIniziate.add(asta);
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;
        UtenteConcreto UtenteConcreto = (UtenteConcreto) o;
        return Objects.equals(email , UtenteConcreto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Utente{");
        sb.append("email='").append(email).append('\'');
        sb.append(", nome_cognome='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
class AstaUtenteConcreto implements AstaUtente {
    Asta a;
    Utente v;

    public AstaUtenteConcreto( Asta a , Utente v ) {
        this.a = a;
        this.v = v;
    }

    @Override
    public Asta getAsta() {
        return a;
    }

    @Override
    public Utente getUtente() {
        return v;
    }
}
