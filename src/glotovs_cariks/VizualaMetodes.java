package glotovs_cariks;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// KLASIFIKATORS: Atbild par visu vizuālo stilu, krāsām un pielāgotajiem elementiem
public class VizualaMetodes {

    // --- KRĀSU PALETE (Tumšā tēma) ---
    public static final Color GALVENAIS_FONS = new Color(40, 44, 52);       // Tumši pelēks fons
    public static final Color PANELA_FONS = new Color(50, 54, 62);          // Nedaudz gaišāks paneļiem
    public static final Color AKCENTS = new Color(231, 76, 60);             // Sarkans (kā pokebola daļa)
    public static final Color AKCENTS_HOVER = new Color(192, 57, 43);       // Tumšāks sarkans pie uzbraukšanas
    public static final Color TEKSTS_GALVENAIS = new Color(236, 240, 241);  // Balts teksts
    public static final Color TEKSTS_PELMONS = new Color(189, 195, 199);    // Pelēks teksts

    // --- FONTI ---
    public static final Font FONTS_VIRSRKSTS = new Font("Verdana", Font.BOLD, 20);
    public static final Font FONTS_POGA = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONTS_TEKSTS = new Font("Segoe UI", Font.PLAIN, 14);

    // --- METODE: Izveido paneli ar noapaļotiem stūriem ---
    public static JPanel izveidotApaluPaneli() {
        JPanel panelis = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PANELA_FONS);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // 30px rādiuss
                g2.dispose();
            }
        };
        panelis.setOpaque(false); // Lai stūri būtu caurspīdīgi
        return panelis;
    }

    // --- METODE: Izveido stilizētu pogu ---
    public static JButton izveidotStiliguPogu(String teksts, ActionListener darbiba) {
        JButton poga = new JButton(teksts) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Poga fons
                if (getModel().isRollover()) {
                    g2.setColor(AKCENTS_HOVER);
                } else {
                    g2.setColor(AKCENTS);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                super.paintComponent(g2);
                g2.dispose();
            }
        };

        poga.setFont(FONTS_POGA);
        poga.setForeground(TEKSTS_GALVENAIS);
        poga.setFocusPainted(false);
        poga.setBorderPainted(false);
        poga.setContentAreaFilled(false);
        poga.setCursor(new Cursor(Cursor.HAND_CURSOR));
        poga.setBorder(new EmptyBorder(10, 20, 10, 20)); 

        if (darbiba != null) poga.addActionListener(darbiba);
        return poga;
    }

    // --- METODE: Pievieno loga pārvietošanas funkciju (drag & drop) ---
    public static void padaritParietojamu(Component komponente, Window logs) {
        MouseAdapter ma = new MouseAdapter() {
            int lastX, lastY;
            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getXOnScreen();
                lastY = e.getYOnScreen();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                logs.setLocation(logs.getLocation().x + (x - lastX), logs.getLocation().y + (y - lastY));
                lastX = x;
                lastY = y;
            }
        };
        komponente.addMouseListener(ma);
        komponente.addMouseMotionListener(ma);
    }

    // --- METODE: Attēlu ielāde ar drošību ---
    public static JLabel ieladetFonu(String failaNosaukums, int platums, int augstums) {
        JLabel attelaLauks = new JLabel();
        try {
            ImageIcon originalIcon = new ImageIcon(VizualaMetodes.class.getResource("/atteli/" + failaNosaukums));
            Image img = originalIcon.getImage().getScaledInstance(platums, augstums, Image.SCALE_SMOOTH);
            attelaLauks.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            attelaLauks.setText(""); 
        }
        return attelaLauks;
    }
}