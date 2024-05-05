package AsteOnLine.client.renderers;

import AsteOnLine.shared.AstaUtente;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AstaUtenteRenderer extends JLabel implements ListCellRenderer<AstaUtente> {

    public AstaUtenteRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent( JList<? extends AstaUtente> list , AstaUtente value , int index , boolean isSelected , boolean cellHasFocus ) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        setText(value.getAsta().getNomeArticolo() + " prezzo: " +value.getAsta().getValore() +" €" +" vinta il"+dateFormat.format(value.getAsta().getFine())+" venditore: "+value.getUtente().getNome()+ " mail: "+value.getUtente().getEmail());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
