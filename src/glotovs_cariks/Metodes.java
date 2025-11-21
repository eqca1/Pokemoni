package glotovs_cariks;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Metodes {

    private static JDialog izveidotBazesDialogu(int platums, int augstums) {
        JDialog dialogs = new JDialog((Frame)null, true);
        dialogs.setUndecorated(true);
        dialogs.setBackground(new Color(0,0,0,0));
        dialogs.setSize(platums, augstums);
        dialogs.setLocationRelativeTo(null);
        return dialogs;
    }
    
    private static JPanel izveidotDialogaFonu(int padding) {
        JPanel fons = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(VizualaMetodes.GALVENAIS_FONS);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
                g2d.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(VizualaMetodes.AKCENTS);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 35, 35);
                g2d.dispose();
            }
        };
        fons.setOpaque(false);
        fons.setBorder(new EmptyBorder(padding, padding, padding, padding));
        return fons;
    }

    public static void info(String zinja) {

    	JDialog d = izveidotBazesDialogu(600, 450);
        
        JPanel fons = izveidotDialogaFonu(10);
        
        JLabel teksts = new JLabel("<html><div style='text-align: center; width: 450px;'>" + zinja.replace("\n", "<br>") + "</div></html>", SwingConstants.CENTER);
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

    public static String ievaditTekstu(String jautajums) {

    	JDialog d = izveidotBazesDialogu(500, 300);
        AtomicReference<String> teksts = new AtomicReference<>();
        
        JPanel fons = izveidotDialogaFonu(10);
        
        JLabel lbl = new JLabel("<html><center>" + jautajums + "</center></html>", SwingConstants.CENTER);
        lbl.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        lbl.setFont(VizualaMetodes.FONTS_TEKSTS);
        lbl.setBorder(new EmptyBorder(20, 20, 10, 20));
        
        JTextField ievadesLauks = new JTextField(20);
        ievadesLauks.setBackground(VizualaMetodes.PANELA_FONS);
        ievadesLauks.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        ievadesLauks.setCaretColor(VizualaMetodes.TEKSTS_GALVENAIS);
        ievadesLauks.setFont(VizualaMetodes.FONTS_TEKSTS);
        ievadesLauks.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel ievadesPanelis = new JPanel();
        ievadesPanelis.setOpaque(false);
        ievadesPanelis.add(ievadesLauks);

        JPanel poguPanelis = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        poguPanelis.setOpaque(false);
        poguPanelis.setBorder(new EmptyBorder(10, 0, 20, 0));
        
        JButton okPoga = VizualaMetodes.izveidotStiliguPogu("Apstiprināt", e -> {
            teksts.set(ievadesLauks.getText().trim());
            d.dispose();
        });
        
        JButton atceltPoga = VizualaMetodes.izveidotStiliguPogu("Atcelt", VizualaMetodes.SARKANS_AIZVERT, e -> {
            teksts.set(null);
            d.dispose();
        });
        
        poguPanelis.add(okPoga);
        poguPanelis.add(atceltPoga);

        fons.add(lbl, BorderLayout.NORTH);
        fons.add(ievadesPanelis, BorderLayout.CENTER);
        fons.add(poguPanelis, BorderLayout.SOUTH);
        
        VizualaMetodes.padaritParietojamu(fons, d);
        d.add(fons);
        d.setVisible(true);
        
        return teksts.get();
    }

    public static int ievaditSkaitliRobezas(String jautajums, int min, int max) {
        JDialog d = izveidotBazesDialogu(500, 300);
        AtomicInteger skaitlis = new AtomicInteger(-1);
        
        JPanel fons = izveidotDialogaFonu(10);
        
        JLabel lbl = new JLabel("<html><center>" + jautajums + "<br>(No " + min + " līdz " + max + ")</center></html>", SwingConstants.CENTER);
        lbl.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        lbl.setFont(VizualaMetodes.FONTS_TEKSTS);
        lbl.setBorder(new EmptyBorder(20, 20, 10, 20));
        
        JFormattedTextField ievadesLauks = new JFormattedTextField();
        ievadesLauks.setColumns(10);
        ievadesLauks.setBackground(VizualaMetodes.PANELA_FONS);
        ievadesLauks.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        ievadesLauks.setCaretColor(VizualaMetodes.TEKSTS_GALVENAIS);
        ievadesLauks.setFont(VizualaMetodes.FONTS_TEKSTS);
        ievadesLauks.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel ievadesPanelis = new JPanel();
        ievadesPanelis.setOpaque(false);
        ievadesPanelis.add(ievadesLauks);

        JPanel poguPanelis = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        poguPanelis.setOpaque(false);
        poguPanelis.setBorder(new EmptyBorder(10, 0, 20, 0));

        JButton okPoga = VizualaMetodes.izveidotStiliguPogu("Apstiprināt", e -> {
            try {
                int ievade = Integer.parseInt(ievadesLauks.getText());
                if (ievade >= min && ievade <= max) {
                    skaitlis.set(ievade);
                    d.dispose();
                } else {
                    VizualaMetodes.raditDialogu("Kļūda", "Skaitlim jābūt robežās no " + min + " līdz " + max + "!");
                }
            } catch (NumberFormatException ex) {
                VizualaMetodes.raditDialogu("Kļūda", "Nederīga skaitļa ievade!");
            }
        });
        
        JButton atceltPoga = VizualaMetodes.izveidotStiliguPogu("Atcelt", VizualaMetodes.SARKANS_AIZVERT, e -> {
            skaitlis.set(-1);
            d.dispose();
        });
        
        poguPanelis.add(okPoga);
        poguPanelis.add(atceltPoga);

        fons.add(lbl, BorderLayout.NORTH);
        fons.add(ievadesPanelis, BorderLayout.CENTER);
        fons.add(poguPanelis, BorderLayout.SOUTH);

        VizualaMetodes.padaritParietojamu(fons, d);
        d.add(fons);
        d.setVisible(true);
        
        return skaitlis.get();
    }

    public static int raditIzveli(String virsraksts, String jautajums, String[] opcijas) {

    	JDialog d = izveidotBazesDialogu(600, 450);
        AtomicInteger izvele = new AtomicInteger(-1);

        JPanel fons = izveidotDialogaFonu(10);
        fons.setLayout(new BorderLayout(10, 10));

        JLabel lbl = new JLabel("<html><center>" + virsraksts + "</center></html>", SwingConstants.CENTER);
        lbl.setFont(VizualaMetodes.FONTS_VIRSRKSTS);
        lbl.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        lbl.setBorder(new EmptyBorder(15, 20, 10, 20));
        
        JLabel jautajumaLbl = new JLabel("<html><center>" + jautajums + "</center></html>", SwingConstants.CENTER);
        jautajumaLbl.setForeground(VizualaMetodes.TEKSTS_PELMONS);
        jautajumaLbl.setFont(VizualaMetodes.FONTS_TEKSTS);
        jautajumaLbl.setBorder(new EmptyBorder(0, 20, 10, 20));

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
        
        JPanel centraPanelis = new JPanel(new BorderLayout());
        centraPanelis.setOpaque(false);
        centraPanelis.add(jautajumaLbl, BorderLayout.NORTH);
        centraPanelis.add(poguPanelis, BorderLayout.CENTER);
        
        fons.add(centraPanelis, BorderLayout.CENTER);
        
        VizualaMetodes.padaritParietojamu(fons, d);
        d.add(fons);
        d.setVisible(true);
        
        return izvele.get();
    }

    public static Pokemons izveletiesPokemonuCinai(String virsraksts, List<Pokemons> saraksts) {

    	JDialog d = izveidotBazesDialogu(600, 600);
        AtomicReference<Pokemons> izveletais = new AtomicReference<>(null);

        JPanel fons = izveidotDialogaFonu(10);
        fons.setLayout(new BorderLayout(10, 10));

        JLabel lbl = new JLabel("<html><center>" + virsraksts + "</center></html>", SwingConstants.CENTER);
        lbl.setFont(VizualaMetodes.FONTS_VIRSRKSTS);
        lbl.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        lbl.setBorder(new EmptyBorder(15, 20, 10, 20));

        JPanel sarakstaPanelis = new JPanel();
        sarakstaPanelis.setLayout(new BoxLayout(sarakstaPanelis, BoxLayout.Y_AXIS));
        sarakstaPanelis.setOpaque(false);

        for (Pokemons p : saraksts) {
            JPanel pPanelis = new JPanel(new BorderLayout());
            pPanelis.setOpaque(false);
            pPanelis.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(5, 10, 5, 10),
                BorderFactory.createMatteBorder(0, 0, 1, 0, VizualaMetodes.AKCENTS)
            ));
            pPanelis.setMaximumSize(new Dimension(550, 80));

            JLabel infoLbl = new JLabel("<html><b>" + p.getVards() + "</b> (" + p.getTipaNosaukums() + ")<br>" +
                    "HP: " + p.getDziviba() + "/" + p.getMaxDziviba() + "</html>");
            infoLbl.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
            infoLbl.setFont(VizualaMetodes.FONTS_TEKSTS);

            JPanel poguGrupa = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            poguGrupa.setOpaque(false);

            JButton izveletiesBtn = VizualaMetodes.izveidotStiliguPogu("Izvēlēties", e -> {
                izveletais.set(p);
                d.dispose();
            });
            izveletiesBtn.setPreferredSize(new Dimension(120, 40));

            JButton dziedetBtn = VizualaMetodes.izveidotStiliguPogu("Dziedēt", VizualaMetodes.ZALS_IESPEJAMS, e -> {
                p.dziedet();
                infoLbl.setText("<html><b>" + p.getVards() + "</b> (" + p.getTipaNosaukums() + ")<br>" +
                        "HP: " + p.getDziviba() + "/" + p.getMaxDziviba() + "</html>");
                infoLbl.repaint();
            });
            dziedetBtn.setPreferredSize(new Dimension(100, 40));

            poguGrupa.add(izveletiesBtn);
            poguGrupa.add(dziedetBtn);

            pPanelis.add(infoLbl, BorderLayout.CENTER);
            pPanelis.add(poguGrupa, BorderLayout.EAST);
            sarakstaPanelis.add(pPanelis);
        }

        JScrollPane scroll = VizualaMetodes.izveidotStiliguScrollPane(sarakstaPanelis);
        
        JButton atceltBtn = VizualaMetodes.izveidotStiliguPogu("Atcelt", VizualaMetodes.SARKANS_AIZVERT, e -> d.dispose());
        JPanel apaksasPanelis = new JPanel();
        apaksasPanelis.setOpaque(false);
        apaksasPanelis.add(atceltBtn);

        fons.add(lbl, BorderLayout.NORTH);
        fons.add(scroll, BorderLayout.CENTER);
        fons.add(apaksasPanelis, BorderLayout.SOUTH);

        VizualaMetodes.padaritParietojamu(fons, d);
        d.add(fons);
        d.setVisible(true);

        return izveletais.get();
    }
}