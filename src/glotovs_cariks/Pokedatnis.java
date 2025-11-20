package glotovs_cariks;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// GALVENĀ KLASE
public class Pokedatnis extends JFrame { 

    static Treneris speletajs; 
    static ArrayList<Pokemons> visiPokemoni = new ArrayList<>(); 
    
    private JPanel galvenaisPanelis;
    private float caurspidigums = 0.0f; // Animācijai

    public Pokedatnis() {
        // --- LOGA IESTATĪJUMI ---
        setTitle("Pokedatnis Ultimate"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setUndecorated(true); // SVARĪGI: Noņem balto augšējo joslu
        setBackground(new Color(0,0,0,0)); // Caurspīdīgs fons, lai darbotos noapaļotie stūri
        setLocationRelativeTo(null);

        // --- GALVENAIS FONDA PANELIS ---
        galvenaisPanelis = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(VizualaMetodes.GALVENAIS_FONS);
                // Zīmējam fonu ar noapaļotiem stūriem
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                // Zīmējam sarkanu apmali (pēc izvēles)
                g2.setColor(VizualaMetodes.AKCENTS);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 40, 40);
            }
        };
        galvenaisPanelis.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(galvenaisPanelis);

        // --- AUGŠĒJĀ JOSLA (Virsraksts + Aizvērt) ---
        JPanel augsa = new JPanel(new BorderLayout());
        augsa.setOpaque(false);
        
        JLabel virsraksts = new JLabel("  POKEDATNIS: ARĒNA", SwingConstants.LEFT);
        virsraksts.setFont(VizualaMetodes.FONTS_VIRSRKSTS);
        virsraksts.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        
        JButton aizvertPoga = VizualaMetodes.izveidotStiliguPogu("X", e -> System.exit(0));
        aizvertPoga.setBackground(VizualaMetodes.AKCENTS);
        aizvertPoga.setPreferredSize(new Dimension(50, 40));

        augsa.add(virsraksts, BorderLayout.CENTER);
        augsa.add(aizvertPoga, BorderLayout.EAST);
        galvenaisPanelis.add(augsa, BorderLayout.NORTH);

        // --- CENTRĀLAIS SATURS (Sadalīts 2 daļās) ---
        JPanel saturs = new JPanel(new GridLayout(1, 2, 20, 0));
        saturs.setOpaque(false);
        saturs.setBorder(new EmptyBorder(20, 0, 20, 0));

        // KREISĀ PUSE: Informatīvais panelis
        JPanel kreisaPuse = VizualaMetodes.izveidotApaluPaneli();
        kreisaPuse.setLayout(new BorderLayout());
        kreisaPuse.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel attels = VizualaMetodes.ieladetFonu("fons.png", 350, 350);
        if (attels.getIcon() == null) {
            attels.setText("<html><center>Sveicināti Arēnā!<br>Izvēlieties darbību.</center></html>");
            attels.setFont(VizualaMetodes.FONTS_VIRSRKSTS);
            attels.setForeground(VizualaMetodes.TEKSTS_PELMONS);
            attels.setHorizontalAlignment(SwingConstants.CENTER);
        }
        kreisaPuse.add(attels, BorderLayout.CENTER);

        // LABĀ PUSE: Izvēlne ar pogām
        JPanel labaPuse = new JPanel();
        labaPuse.setOpaque(false);
        labaPuse.setLayout(new GridLayout(5, 1, 0, 15)); 

        // POGAS
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Izveidot Pokemonu", e -> izveidotPokemonu()));
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Mana Komanda", e -> paraditKomandu())); // Šeit tagad ir dziedināšana
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Visi Pokemoni", e -> paraditVisus()));
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Sākt Turnīru", e -> organizetCinu()));
        
        // Iepriekšējā poga "Noteikumi" ir dzēsta, tās vietā var ielikt tukšumu vai atstāt 4 pogas
        // Lai saglabātu izkārtojumu, pievienojam neredzamu vietu vai vienkārši atstājam pogas izstiepties
        // Šajā gadījumā GridLayout automātiski pielāgos izmērus.

        saturs.add(kreisaPuse);
        saturs.add(labaPuse);
        galvenaisPanelis.add(saturs, BorderLayout.CENTER);

        // Iespēja pārvietot logu ar peli
        VizualaMetodes.padaritParietojamu(galvenaisPanelis, this);
        
        setVisible(true);
        animacijaParadities(); 
    }

    // --- ANIMĀCIJA: Loga lēna parādīšanās ---
    private void animacijaParadities() {
        setOpacity(0.0f);
        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caurspidigums += 0.05f;
                if (caurspidigums >= 1.0f) {
                    caurspidigums = 1.0f;
                    setOpacity(1.0f);
                    ((Timer)e.getSource()).stop();
                } else {
                    setOpacity(caurspidigums);
                }
            }
        });
        timer.start();
    }
    
    // --- SPĒLES SĀKUMS (LOGIN LOGS) ---
    public static void saktSpeli() {
        String vards = Metodes.ievaditTekstu("Sveiks, Treneri! Ievadi savu vārdu:");
        
        if (vards != null && !vards.isEmpty()) {
            speletajs = new Treneris(vards);
            SwingUtilities.invokeLater(() -> new Pokedatnis());
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(() -> saktSpeli());
    }

    // --- LOĢIKAS METODES ---
    
    public static void izveidotPokemonu() {
        String[] ipasnieki = {"Man (Trenerim)", "Savvaļas"};
        int kamPiederIndex = Metodes.raditIzveli("Īpašnieks", "Kam pieder šis pokemons?", ipasnieki);
        if(kamPiederIndex == -1) return;
        
        String ipasnieks = (kamPiederIndex == 0) ? speletajs.getVards() : "Savvaļas";
        
        String[] tipi = {"Parastais", "Elektriskais", "Ūdens"};
        int tips = Metodes.raditIzveli("Tips", "Izvēlies pokemona tipu:", tipi);
        if (tips == -1) return;
        
        String pVards = Metodes.ievaditTekstu("Ievadi pokemona vārdu:");
        if(pVards == null) return;
        
        int hp = Metodes.ievaditSkaitliRobezas("Dzīvība (HP):", 50, 200);
        int atk = Metodes.ievaditSkaitliRobezas("Uzbrukums (ATK):", 5, 50);
        int def = Metodes.ievaditSkaitliRobezas("Aizsardzība (DEF %):", 0, 30);
        
        Pokemons p = null;
        if(tips == 0) p = new ParastaisP(pVards, ipasnieks, hp, atk, def);
        else if(tips == 1) {
            int v = Metodes.ievaditSkaitliRobezas("Voltāža (V):", 10, 200);
            p = new ElektriskaisP(pVards, ipasnieks, hp, atk, def, v);
        } else {
            p = new UdensP(pVards, ipasnieks, hp, atk, def);
        }
        
        visiPokemoni.add(p);
        if(kamPiederIndex == 0) speletajs.pievienotPokemonu(p);
        Metodes.info("Veiksmīgi izveidots: " + pVards);
    }

    // --- ATJAUNOTĀ KOMANDAS METODE AR ĀRSTĒŠANU ---
    public static void paraditKomandu() {
        if (speletajs.getKomanda().isEmpty()) { 
            Metodes.info("Tava komanda ir tukša!"); 
            return; 
        }

        // Izveidojam pielāgotu logu (JDialog)
        JDialog dialogs = new JDialog((Frame)null, true);
        dialogs.setUndecorated(true);
        dialogs.setBackground(new Color(0,0,0,0));
        dialogs.setSize(500, 450);
        dialogs.setLocationRelativeTo(null);

        JPanel panelis = new JPanel(new BorderLayout());
        panelis.setBackground(VizualaMetodes.GALVENAIS_FONS);
        panelis.setBorder(BorderFactory.createLineBorder(VizualaMetodes.AKCENTS, 2));
        
        // Virsraksts
        JLabel virsraksts = new JLabel("TAVA KOMANDA", SwingConstants.CENTER);
        virsraksts.setFont(VizualaMetodes.FONTS_VIRSRKSTS);
        virsraksts.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        virsraksts.setBorder(new EmptyBorder(15,0,15,0));
        panelis.add(virsraksts, BorderLayout.NORTH);

        // Teksta lauks ar informāciju
        JTextArea tekstaLauks = new JTextArea();
        tekstaLauks.setEditable(false);
        tekstaLauks.setBackground(VizualaMetodes.PANELA_FONS);
        tekstaLauks.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        tekstaLauks.setFont(VizualaMetodes.FONTS_TEKSTS);
        tekstaLauks.setMargin(new Insets(10,10,10,10));
        
        // Funkcija, kas atjauno tekstu
        Runnable atjaunotTekstu = () -> {
            StringBuilder sb = new StringBuilder();
            for (Pokemons p : speletajs.getKomanda()) {
                sb.append(p.toString()).append("\n\n");
            }
            tekstaLauks.setText(sb.toString());
            tekstaLauks.setCaretPosition(0); // Uz augšu
        };
        atjaunotTekstu.run(); // Pirmā ielāde

        JScrollPane scroll = new JScrollPane(tekstaLauks);
        scroll.setBorder(new EmptyBorder(10, 20, 10, 20));
        scroll.getViewport().setBackground(VizualaMetodes.PANELA_FONS);
        // Noņemam scrollbar rāmjus
        scroll.setBorder(null);
        panelis.add(scroll, BorderLayout.CENTER);

        // Apakšas pogas (Aizvērt un Dziedēt)
        JPanel poguPanelis = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        poguPanelis.setOpaque(false);

        JButton dziedetPoga = VizualaMetodes.izveidotStiliguPogu("Dziedēt Visus", e -> {
            for (Pokemons p : speletajs.getKomanda()) {
                // Tā kā nav tiešas setDziviba metodes un dziedet() tikai daļēji dziedē,
                // mēs izsaucam dziedet() ciklā, kamēr dzīvība ir pilna.
                while(p.getDziviba() < p.getMaxDziviba()) {
                    p.dziedet();
                }
            }
            atjaunotTekstu.run();
            JOptionPane.showMessageDialog(dialogs, "Visi pokemoni ir pilnībā izārstēti!");
        });
        // Padarām dziedēšanas pogu zaļu (kā ārstēšanu)
        dziedetPoga.setBackground(new Color(46, 204, 113));

        JButton aizvertPoga = VizualaMetodes.izveidotStiliguPogu("Aizvērt", e -> dialogs.dispose());

        poguPanelis.add(dziedetPoga);
        poguPanelis.add(aizvertPoga);
        panelis.add(poguPanelis, BorderLayout.SOUTH);

        VizualaMetodes.padaritParietojamu(panelis, dialogs);
        dialogs.add(panelis);
        dialogs.setVisible(true);
    }

    public static void paraditVisus() {
        if (visiPokemoni.isEmpty()) { Metodes.info("Nav reģistrētu pokemonu."); return; }
        StringBuilder sb = new StringBuilder("VISI POKEMONI:\n");
        for (Pokemons p : visiPokemoni) sb.append(p).append("\n");
        Metodes.info(sb.toString());
    }

    public static void organizetCinu() {
        if (speletajs.getKomandasIzmers() == 0) { Metodes.info("Tev nav pokemonu cīņai!"); return; }
        
        Pokemons mans = speletajs.getKomanda().get(0); 
        
        Pokemons ienaidnieks = null;
        for(Pokemons p : visiPokemoni) {
            if(!p.getTreneris().equals(speletajs.getVards())) { ienaidnieks = p; break; }
        }
        
        if(ienaidnieks == null) { Metodes.info("Nav pretinieku!\nIzveido 'Savvaļas' pokemonu."); return; }
        
        kaujasProcess(mans, ienaidnieks);
    }

    public static void kaujasProcess(Pokemons p1, Pokemons p2) {
        while (p1.getDziviba() > 0 && p2.getDziviba() > 0) {
            String statuss = String.format("TAVS: %s (%d HP) vs PRETINIEKS: %s (%d HP)", 
                    p1.getVards(), p1.getDziviba(), p2.getVards(), p2.getDziviba());
            
            String[] darbibas = {"Uzbrukt", "Dziedēt", "Speciālā spēja", "Bēgt"};
            int gijiens = Metodes.raditIzveli("Cīņa", statuss, darbibas);
            
            if(gijiens == -1 || gijiens == 3) {
                Metodes.info("Tu aizbēgi no cīņas!");
                break;
            }
            
            String mansGajiens = "";
            if(gijiens == 0) mansGajiens = p1.uzbrukt(p2);
            else if(gijiens == 1) mansGajiens = p1.dziedet();
            else {
                if (p1 instanceof ElektriskaisP) mansGajiens = ((ElektriskaisP)p1).uzladet();
                else if (p1 instanceof UdensP) mansGajiens = ((UdensP)p1).nirt();
                else mansGajiens = p1.getVards() + " nav speciālo spēju!";
            }
            
            if(p2.getDziviba() <= 0) {
                Metodes.info("UZVARA!\n" + mansGajiens + "\n\n" + p2.getVards() + " tika pieveikts!");
                p1.attistit();
                break;
            }
            
            String pretiniekaGajiens = p2.uzbrukt(p1);
            Metodes.info("RAUNDA REZULTĀTS:\n" + mansGajiens + "\n\nPRETINIEKA GĀJIENS:\n" + pretiniekaGajiens);
            
            if (p1.getDziviba() <= 0) {
                Metodes.info("ZAUDĒJUMS! Tavs pokemons zaudēja samaņu.");
            }
        }
    }
}