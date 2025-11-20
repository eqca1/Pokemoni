package glotovs_cariks;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// VIZUĀLO METOŽU KLASE - ATBILDĪGA PAR INTERFEISA DIZAINU
public class VizualaMetodes {

    // === KRĀSU PALETE ===
    public static final Color GALVENAIS_FONS = new Color(30, 34, 42);       // Tumši zils-melns fons
    public static final Color PANELA_FONS = new Color(44, 48, 56);          // Vidēji tumšs panelis
    public static final Color AKCENTS = new Color(231, 76, 60);             // Spilgti sarkans (Pokebola krāsa)
    public static final Color AKCENTS_HOVER = new Color(192, 57, 43);       // Tumšāks sarkans hover
    public static final Color TEKSTS_GALVENAIS = new Color(236, 240, 241);  // Balts teksts
    public static final Color TEKSTS_PELMONS = new Color(169, 177, 184);    // Pelēcīgs teksts
    public static final Color ZALS_IESPEJAMS = new Color(46, 204, 113);     // Zaļš pozitīvām darbībām
    public static final Color DZELTENS_BRIDINAJ = new Color(241, 196, 15);  // Dzeltens brīdinājumiem

    // === FONTI ===
    public static final Font FONTS_VIRSRKSTS = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONTS_POGA = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONTS_TEKSTS = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONTS_MONO = new Font("Consolas", Font.PLAIN, 13); // Teksta laukiem

    // === METODE: IZVEIDOT PANELI AR NOAPAĻOTIEM STŪRIEM ===
    public static JPanel izveidotApaluPaneli() {
        JPanel panelis = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Zīmē paneli ar noapaļotiem stūriem
                g2d.setColor(PANELA_FONS);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
                
                g2d.dispose();
            }
        };
        panelis.setOpaque(false); // Ļauj redzēt noapaļotos stūrus
        return panelis;
    }

    // === METODE: IZVEIDOT STILIZĒTU POGU ===
    public static JButton izveidotStiliguPogu(String teksts, ActionListener darbiba) {
        JButton poga = new JButton(teksts) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Noteikt pogas krāsu atkarībā no stāvokļa
                Color pogasKrasa;
                if (getModel().isPressed()) {
                    pogasKrasa = AKCENTS_HOVER.darker();
                } else if (getModel().isRollover()) {
                    pogasKrasa = AKCENTS_HOVER;
                } else {
                    pogasKrasa = AKCENTS;
                }
                
                // Zīmēt pogas fona ar noapaļotiem stūriem
                g2d.setColor(pogasKrasa);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                
                // Zīmēt pogas tekstu
                g2d.setColor(TEKSTS_GALVENAIS);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int tekstsX = (getWidth() - fm.stringWidth(getText())) / 2;
                int tekstsY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(getText(), tekstsX, tekstsY);
                
                g2d.dispose();
            }
        };

        poga.setFont(FONTS_POGA);
        poga.setForeground(TEKSTS_GALVENAIS);
        poga.setFocusPainted(false);
        poga.setBorderPainted(false);
        poga.setContentAreaFilled(false);
        poga.setCursor(new Cursor(Cursor.HAND_CURSOR));
        poga.setBorder(new EmptyBorder(12, 25, 12, 25));
        poga.setPreferredSize(new Dimension(200, 50));

        if (darbiba != null) {
            poga.addActionListener(darbiba);
        }
        
        return poga;
    }

    // === METODE: IZVEIDOT STILIZĒTU POGU AR KONKRĒTU KRĀSU ===
    public static JButton izveidotStiliguPogu(String teksts, Color krasa, ActionListener darbiba) {
        JButton poga = izveidotStiliguPogu(teksts, darbiba);
        
        // Pielāgotā krāsa tiks izmantota paintComponent metodē
        poga.setBackground(krasa);
        
        return poga;
    }

    // === METODE: PĀRVIETOŠANAS FUNKCIJA LOGAM ===
    public static void padaritParietojamu(Component komponente, Window logs) {
        final Point[] pelesPozicija = new Point[1];
        
        MouseAdapter pelesAdapteris = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pelesPozicija[0] = e.getPoint();
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (pelesPozicija[0] != null) {
                    Point pašreizējāPozīcija = e.getLocationOnScreen();
                    logs.setLocation(
                        pašreizējāPozīcija.x - pelesPozicija[0].x,
                        pašreizējāPozīcija.y - pelesPozicija[0].y
                    );
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                pelesPozicija[0] = null;
            }
        };
        
        komponente.addMouseListener(pelesAdapteris);
        komponente.addMouseMotionListener(pelesAdapteris);
    }

    // === METODE: ATTĒLU IELĀDE AR DROŠĪBU ===
    public static JLabel ieladetFonu(String failaNosaukums, int platums, int augstums) {
        JLabel attelaLauks = new JLabel("", SwingConstants.CENTER);
        
        try {
            // Mēģina ielādēt attēlu no resursu mapes
            java.net.URL attelaURL = VizualaMetodes.class.getResource("/atteli/" + failaNosaukums);
            if (attelaURL != null) {
                ImageIcon originalaisAttels = new ImageIcon(attelaURL);
                Image skaletsAttels = originalaisAttels.getImage().getScaledInstance(platums, augstums, Image.SCALE_SMOOTH);
                attelaLauks.setIcon(new ImageIcon(skaletsAttels));
            } else {
                // Ja attēls nav atrasts, rāda tekstu
                attelaLauks.setText("<html><div style='text-align: center; color: #ecf0f1;'>" +
                                   "Pokemonu Arēna<br>" +
                                   "<span style='font-size: 12px; color: #aab7b8;'>Attēls nav atrasts</span></div></html>");
                attelaLauks.setFont(FONTS_TEKSTS);
            }
        } catch (Exception e) {
            // Kļūdas gadījumā rāda kļūdas ziņojumu
            attelaLauks.setText("<html><div style='text-align: center; color: #e74c3c;'>" +
                               "Kļūda ielādējot attēlu!<br>" +
                               "<span style='font-size: 12px; color: #aab7b8;'>" + e.getMessage() + "</span></div></html>");
            attelaLauks.setFont(FONTS_TEKSTS);
        }
        
        return attelaLauks;
    }

    // === METODE: IZVEIDOT STILIZĒTU TEKSTA LAUKU ===
    public static JTextField izveidotStiliguTekstaLauku(String aizstājējteksts) {
        JTextField tekstaLauks = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Zīmēt fona ar noapaļotiem stūriem
                g2d.setColor(PANELA_FONS);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Zīmēt apmali
                g2d.setColor(AKCENTS);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 20, 20);
                
                g2d.dispose();
                
                super.paintComponent(g);
            }
        };
        
        tekstaLauks.setFont(FONTS_TEKSTS);
        tekstaLauks.setForeground(TEKSTS_GALVENAIS);
        tekstaLauks.setCaretColor(TEKSTS_GALVENAIS);
        tekstaLauks.setBackground(new Color(0, 0, 0, 0)); // Caurspīdīgs fons
        tekstaLauks.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        tekstaLauks.setOpaque(false);
        
        if (aizstājējteksts != null && !aizstājējteksts.isEmpty()) {
            tekstaLauks.setText(aizstājējteksts);
        }
        
        return tekstaLauks;
    }

    // === METODE: IZVEIDOT STILIZĒTU TEKSTA APGABALU ===
    public static JTextArea izveidotStiliguTekstaApgabalu() {
        JTextArea tekstaApgabals = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Zīmēt fona ar noapaļotiem stūriem
                g2d.setColor(PANELA_FONS);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                g2d.dispose();
                
                super.paintComponent(g);
            }
        };
        
        tekstaApgabals.setFont(FONTS_MONO);
        tekstaApgabals.setForeground(TEKSTS_GALVENAIS);
        tekstaApgabals.setBackground(new Color(0, 0, 0, 0)); // Caurspīdīgs fons
        tekstaApgabals.setCaretColor(TEKSTS_GALVENAIS);
        tekstaApgabals.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        tekstaApgabals.setLineWrap(true);
        tekstaApgabals.setWrapStyleWord(true);
        tekstaApgabals.setOpaque(false);
        
        return tekstaApgabals;
    }

    // === METODE: IZVEIDOT STILIZĒTU SCROLL PANELI ===
    public static JScrollPane izveidotStiliguScrollPane(Component komponents) {
        JScrollPane scrollPane = new JScrollPane(komponents);
        
        // Pielāgot scrollbar izskatu
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(PANELA_FONS);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        // Pielāgot scrollbar krāsas
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = AKCENTS;
                this.trackColor = GALVENAIS_FONS;
            }
            
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createEmptyButton();
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createEmptyButton();
            }
            
            private JButton createEmptyButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
        
        return scrollPane;
    }

    // === METODE: RĀDĪT IZKLAIDES LOGU ===
    public static void paraditIzklaidesLogu(String virsraksts, String saturs) {
        JDialog dialogs = new JDialog((Frame)null, true);
        dialogs.setUndecorated(true);
        dialogs.setBackground(new Color(0, 0, 0, 0));
        dialogs.setSize(500, 350);
        dialogs.setLocationRelativeTo(null);

        JPanel panelis = new JPanel(new BorderLayout());
        panelis.setBackground(GALVENAIS_FONS);
        panelis.setBorder(BorderFactory.createLineBorder(AKCENTS, 2));
        
        // Virsraksts
        JLabel virsrakstaLabel = new JLabel(virsraksts, SwingConstants.CENTER);
        virsrakstaLabel.setFont(FONTS_VIRSRKSTS);
        virsrakstaLabel.setForeground(TEKSTS_GALVENAIS);
        virsrakstaLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        panelis.add(virsrakstaLabel, BorderLayout.NORTH);

        // Saturs
        JTextArea saturaLauks = izveidotStiliguTekstaApgabalu();
        saturaLauks.setText(saturs);
        saturaLauks.setEditable(false);
        
        JScrollPane scroll = izveidotStiliguScrollPane(saturaLauks);
        scroll.setBorder(new EmptyBorder(10, 20, 10, 20));
        panelis.add(scroll, BorderLayout.CENTER);

        // Aizvēršanas poga
        JButton aizvertPoga = izveidotStiliguPogu("Aizvērt", e -> dialogs.dispose());
        JPanel poguPanelis = new JPanel();
        poguPanelis.setOpaque(false);
        poguPanelis.add(aizvertPoga);
        poguPanelis.setBorder(new EmptyBorder(0, 0, 20, 0));
        panelis.add(poguPanelis, BorderLayout.SOUTH);

        padaritParietojamu(panelis, dialogs);
        dialogs.add(panelis);
        dialogs.setVisible(true);
    }
}