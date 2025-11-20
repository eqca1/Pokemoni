package glotovs_cariks;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Metodes {

    // --- UNIVERSĀLS DIALOGA LOGU VEIDOTĀJS ---
    // Šī metode izveido "bāzi" visiem paziņojumiem, lai nebūtu balto malu
    private static JDialog izveidotBazesDialogu(int platums, int augstums) {
        JDialog dialogs = new JDialog((Frame)null, true); // true = modāls (bloķē citus logus)
        dialogs.setUndecorated(true); // Noņemam Windows rāmi
        dialogs.setBackground(new Color(0,0,0,0)); // Caurspīdīgs fons, lai redzētu apaļos stūrus
        dialogs.setSize(platums, augstums);
        dialogs.setLocationRelativeTo(null);
        return dialogs;
    }

    // --- INFORMĀCIJAS PAZIŅOJUMS ---
    public static void info(String zinja) {
        JDialog d = izveidotBazesDialogu(400, 250);
        
        JPanel fons = new JPanel(new BorderLayout());
        fons.setBackground(VizualaMetodes.GALVENAIS_FONS);
        fons.setBorder(BorderFactory.createLineBorder(VizualaMetodes.AKCENTS, 2)); // Sarkana maliņa
        
        JLabel teksts = new JLabel("<html><div style='text-align: center;'>" + zinja.replace("\n", "<br>") + "</div></html>", SwingConstants.CENTER);
        teksts.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        teksts.setFont(VizualaMetodes.FONTS_TEKSTS);
        teksts.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JButton okPoga = VizualaMetodes.izveidotStiliguPogu("Sapratu", e -> d.dispose());
        JPanel poguPanelis = new JPanel();
        poguPanelis.setOpaque(false);
        poguPanelis.add(okPoga);
        poguPanelis.setBorder(new EmptyBorder(0, 0, 20, 0));

        fons.add(teksts, BorderLayout.CENTER);
        fons.add(poguPanelis, BorderLayout.SOUTH);
        
        VizualaMetodes.padaritParietojamu(fons, d);
        d.add(fons);
        d.setVisible(true);
    }

    // --- TEKSTA IEVADE ---
    public static String ievaditTekstu(String jautajums) {
        JDialog d = izveidotBazesDialogu(400, 200);
        final String[] rezultats = {null}; // Lai saglabātu vērtību no klausītāja

        JPanel fons = new JPanel(new GridLayout(3, 1, 10, 10));
        fons.setBackground(VizualaMetodes.GALVENAIS_FONS);
        fons.setBorder(new EmptyBorder(20, 20, 20, 20));
        fons.setBorder(BorderFactory.createLineBorder(VizualaMetodes.AKCENTS, 1));

        JLabel lbl = new JLabel(jautajums, SwingConstants.CENTER);
        lbl.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        lbl.setFont(VizualaMetodes.FONTS_VIRSRKSTS);

        JTextField lauks = new JTextField();
        lauks.setBackground(VizualaMetodes.PANELA_FONS);
        lauks.setForeground(Color.WHITE);
        lauks.setCaretColor(Color.WHITE);
        lauks.setHorizontalAlignment(SwingConstants.CENTER);
        lauks.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton okPoga = VizualaMetodes.izveidotStiliguPogu("Apstiprināt", e -> {
            if (!lauks.getText().trim().isEmpty()) {
                rezultats[0] = lauks.getText().trim();
                d.dispose();
            }
        });

        fons.add(lbl);
        fons.add(lauks);
        fons.add(okPoga);

        VizualaMetodes.padaritParietojamu(fons, d);
        d.add(fons);
        d.setVisible(true);
        
        return rezultats[0];
    }

    // --- SKAITĻA IEVADE ---
    public static int ievaditSkaitliRobezas(String zinojums, int min, int max) {
        while (true) {
            String val = ievaditTekstu(zinojums + " (" + min + "-" + max + ")");
            if (val == null) return min; // Ja aizver logu, atgriežam minimumu
            try {
                int x = Integer.parseInt(val);
                if (x >= min && x <= max) return x;
                info("Kļūda! Skaitlim jābūt no " + min + " līdz " + max);
            } catch (NumberFormatException e) {
                info("Lūdzu, ievadiet derīgu skaitli!");
            }
        }
    }

    // --- IZVĒLES LOGS (AIZVIETO OPTION DIALOG) ---
    public static int raditIzveli(String virsraksts, String jautajums, String[] opcijas) {
        JDialog d = izveidotBazesDialogu(500, 300);
        AtomicInteger izvele = new AtomicInteger(-1);

        JPanel fons = new JPanel(new BorderLayout(10, 10));
        fons.setBackground(VizualaMetodes.GALVENAIS_FONS);
        fons.setBorder(BorderFactory.createLineBorder(VizualaMetodes.AKCENTS, 2));

        JLabel lbl = new JLabel("<html><center>" + jautajums + "</center></html>", SwingConstants.CENTER);
        lbl.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        lbl.setFont(VizualaMetodes.FONTS_TEKSTS);
        lbl.setBorder(new EmptyBorder(20, 20, 10, 20));

        JPanel poguPanelis = new JPanel(new GridLayout(opcijas.length, 1, 5, 5));
        poguPanelis.setOpaque(false);
        poguPanelis.setBorder(new EmptyBorder(10, 50, 20, 50));

        for (int i = 0; i < opcijas.length; i++) {
            final int index = i;
            JButton btn = VizualaMetodes.izveidotStiliguPogu(opcijas[i], e -> {
                izvele.set(index);
                d.dispose();
            });
            poguPanelis.add(btn);
        }

        fons.add(lbl, BorderLayout.NORTH);
        fons.add(poguPanelis, BorderLayout.CENTER);

        VizualaMetodes.padaritParietojamu(fons, d);
        d.add(fons);
        d.setVisible(true);

        return izvele.get();
    }
}