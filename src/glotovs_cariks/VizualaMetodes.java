package glotovs_cariks;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VizualaMetodes {

    public static final Color GALVENAIS_FONS = new Color(18, 18, 28);
    public static final Color PANELA_FONS = new Color(33, 33, 50);
    public static final Color AKCENTS = new Color(120, 90, 255);
    public static final Color AKCENTS_HOVER = new Color(95, 70, 220);
    public static final Color TEKSTS_GALVENAIS = new Color(230, 230, 255);
    public static final Color TEKSTS_PELMONS = new Color(140, 140, 160);
    public static final Color ZALS_IESPEJAMS = new Color(46, 204, 113);
    public static final Color DZELTENS_BRIDINAJ = new Color(255, 193, 7);
    public static final Color SARKANS_AIZVERT = new Color(192, 57, 43);

    public static final Font FONTS_VIRSRKSTS = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONTS_POGA = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONTS_TEKSTS = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONTS_MONO = new Font("Consolas", Font.PLAIN, 13);

    public static JPanel izveidotApaluPaneli() {
        JPanel panelis = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(PANELA_FONS);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
                g2d.dispose();
            }
        };
        panelis.setOpaque(false);
        return panelis;
    }
    
    private static Color samazinatKrasu(Color krasa, int samazinajums) {
        int r = Math.max(0, krasa.getRed() - samazinajums);
        int g = Math.max(0, krasa.getGreen() - samazinajums);
        int b = Math.max(0, krasa.getBlue() - samazinajums);
        return new Color(r, g, b, krasa.getAlpha());
    }

    private static JButton izveidotBazesPogu(String teksts, Color bazeKrasa, ActionListener darbiba) {
        JButton poga = new JButton(teksts) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color krasa = getBackground();
                Color hoverKrasa = samazinatKrasu(krasa, 20);
                Color pressKrasa = samazinatKrasu(krasa, 40);
                
                Color pogasKrasa;
                if (getModel().isPressed()) {
                    pogasKrasa = pressKrasa;
                } else if (getModel().isRollover()) {
                    pogasKrasa = hoverKrasa;
                } else {
                    pogasKrasa = krasa;
                }
                
                g2d.setColor(pogasKrasa);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                
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
        poga.setBackground(bazeKrasa);

        if (darbiba != null) {
            poga.addActionListener(darbiba);
        }
        
        return poga;
    }
    
    public static JButton izveidotStiliguPogu(String teksts, ActionListener darbiba) {
        return izveidotBazesPogu(teksts, AKCENTS, darbiba);
    }

    public static JButton izveidotStiliguPogu(String teksts, Color krasa, ActionListener darbiba) {
        return izveidotBazesPogu(teksts, krasa, darbiba);
    }

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
                    Point pasreizejaPozicija = e.getLocationOnScreen();
                    logs.setLocation(
                        pasreizejaPozicija.x - pelesPozicija[0].x,
                        pasreizejaPozicija.y - pelesPozicija[0].y
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

    public static void ieladetGif(JLabel label, String failaNosaukums) {
        label.setText(""); 
        try {
            // Meklē failu mapē /atteli/
            java.net.URL attelaURL = VizualaMetodes.class.getResource("/atteli/" + failaNosaukums);
            if (attelaURL != null) {
                ImageIcon gifIcon = new ImageIcon(attelaURL);
                label.setIcon(gifIcon);
            } else {
                label.setText("<html><center>GIF NAV ATRASTS:<br>/atteli/" + failaNosaukums + "</center></html>");
            }
        } catch (Exception e) {
            label.setText("Kļūda: " + e.getMessage());
        }
    }
    
    public static JTextArea izveidotStiliguTekstaApgabalu() {
        JTextArea tekstaApgabals = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(PANELA_FONS);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        tekstaApgabals.setFont(FONTS_MONO);
        tekstaApgabals.setForeground(TEKSTS_GALVENAIS);
        tekstaApgabals.setBackground(new Color(0, 0, 0, 0));
        tekstaApgabals.setCaretColor(TEKSTS_GALVENAIS);
        tekstaApgabals.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        tekstaApgabals.setLineWrap(true);
        tekstaApgabals.setWrapStyleWord(true);
        
        return tekstaApgabals;
    }

    public static JScrollPane izveidotStiliguScrollPane(Component komponente) {
        JScrollPane scroll = new JScrollPane(komponente);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    public static void raditDialogu(String virsraksts, String saturs) {
        JDialog dialogs = new JDialog((Frame)null, true);
        dialogs.setUndecorated(true);
        dialogs.setBackground(new Color(0, 0, 0, 0));
        dialogs.setSize(500, 350);
        dialogs.setLocationRelativeTo(null);

        JPanel panelis = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(GALVENAIS_FONS);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
                g2d.setColor(AKCENTS);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 35, 35);
                g2d.dispose();
            }
        };
        panelis.setOpaque(false);
        panelis.setLayout(new BorderLayout());
        
        JLabel virsrakstaLabel = new JLabel(virsraksts, SwingConstants.CENTER);
        virsrakstaLabel.setFont(FONTS_VIRSRKSTS);
        virsrakstaLabel.setForeground(TEKSTS_GALVENAIS);
        virsrakstaLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        panelis.add(virsrakstaLabel, BorderLayout.NORTH);

        JTextArea saturaLauks = izveidotStiliguTekstaApgabalu();
        saturaLauks.setText(saturs);
        saturaLauks.setEditable(false);
        
        JScrollPane scroll = izveidotStiliguScrollPane(saturaLauks);
        scroll.setBorder(new EmptyBorder(10, 20, 10, 20));
        panelis.add(scroll, BorderLayout.CENTER);

        JButton aizvertPoga = izveidotStiliguPogu("Aizvērt", e -> dialogs.dispose());
        JPanel poguPanelis = new JPanel();
        poguPanelis.setOpaque(false);
        poguPanelis.setBorder(new EmptyBorder(0, 0, 15, 0));
        poguPanelis.add(aizvertPoga);
        panelis.add(poguPanelis, BorderLayout.SOUTH);

        padaritParietojamu(panelis, dialogs);
        dialogs.setContentPane(panelis);
        dialogs.setVisible(true);
    }
}