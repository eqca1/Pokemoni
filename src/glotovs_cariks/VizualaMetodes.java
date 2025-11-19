package glotovs_cariks;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

// Klase visu vizuālo elementu apstrādei
public class VizualaMetodes {

    // Metode pikseļu pogu izveidei
    public static JButton izveidotAttelaPogu(String teksts, ActionListener listeners) {
        // Poga Iziet būs "iziet.png", poga Mana komanda būs "komanda.png" (pēc Pokedatnis.java)
        String attelaNosaukums = teksts.toLowerCase().replace(" ", "") + ".png";
        
        JButton poga = new JButton(teksts);
        
        try {
            // Meklē attēlus mapē atteli
            ImageIcon ikona = new ImageIcon(VizualaMetodes.class.getResource("/atteli/" + attelaNosaukums));
            poga.setIcon(ikona);
            poga.setText(""); // Noņemam tekstu, ja izmantojam attēlu
            
            // Iestatām pogas izmēru
            poga.setPreferredSize(new Dimension(ikona.getIconWidth(), ikona.getIconHeight()));
            poga.setMaximumSize(new Dimension(ikona.getIconWidth(), ikona.getIconHeight()));
            poga.setSize(ikona.getIconWidth(), ikona.getIconHeight());
            
        } catch (Exception e) {
            // Kļūda: attēls netika atrasts. Atstājam pogu ar tekstu.
            System.err.println("Kļūda ielādējot pogas attēlu (" + attelaNosaukums + "): " + e.getMessage());
        }

        poga.setAlignmentX(Component.CENTER_ALIGNMENT);
        poga.setFocusPainted(false); 
        poga.setContentAreaFilled(false); // Padara pogu caurspīdīgu
        poga.setBorderPainted(false); // Noņem apmali

        poga.addActionListener(listeners);
        return poga;
    }

    public static JLabel ieladetFonu(String failaNosaukums, int platums, int augstums) {
        JLabel attelaLauks = new JLabel();
        try {
            ImageIcon originalIcon = new ImageIcon(VizualaMetodes.class.getResource("/atteli/" + failaNosaukums));
            
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(platums, augstums, Image.SCALE_SMOOTH);
            
            attelaLauks.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("Kļūda ielādējot fona attēlu (" + failaNosaukums + "): " + e.getMessage());
            attelaLauks.setText("Fons nav ielādēts!"); //
        }
        return attelaLauks;
}
}
