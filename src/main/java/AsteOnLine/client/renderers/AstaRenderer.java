package AsteOnLine.client.renderers;

import AsteOnLine.shared.Asta;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AstaRenderer extends JLabel implements ListCellRenderer<Asta> {
    public AstaRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent( JList<? extends Asta> list , Asta value , int index , boolean isSelected , boolean cellHasFocus ) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        setText(value.getNomeArticolo() + " prezzo attuale: " +value.getValore() +" €" +" scade:"+dateFormat.format(value.getFine()));

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
