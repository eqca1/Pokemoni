package glotovs_cariks;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class Pokedatnis extends JFrame { 

    static Treneris speletajs; 
    static ArrayList<Pokemons> visiPokemoni = new ArrayList<>(); 

    private JPanel kreisaPuse;      // Kreisa puse: fons/galvenais attels
    private JPanel labaPuse;        // Laba puse: pogas

    // --- Konfigūracija ---
    public Pokedatnis() {
        // Iestatījumi
        setTitle("Pokedatnis"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout()); 
        
        // Noņemam virsrakstu, lai iegūtu tīru logu
        setUndecorated(true);
        
        // Paneļu izveide
        kreisaPuse = izveidotKreisoPaneli();
        labaPuse = izveidotLeboPaneli();

        add(kreisaPuse, BorderLayout.CENTER); 
        add(labaPuse, BorderLayout.EAST);      

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel izveidotKreisoPaneli() {
        JPanel panelis = new JPanel(new BorderLayout());
        
        final int FONA_IZMERS = 600; 
        
        JLabel fonaAttels = VizualaMetodes.ieladetFonu("fons.png", FONA_IZMERS, FONA_IZMERS);
        panelis.add(fonaAttels, BorderLayout.CENTER);
        return panelis;
    }

    private JPanel izveidotLeboPaneli() {
        JPanel panelis = new JPanel();
        panelis.setLayout(new BoxLayout(panelis, BoxLayout.Y_AXIS));
        panelis.setPreferredSize(new Dimension(200, 0)); 
        panelis.setBackground(new Color(100, 100, 100)); 

        // VerticalGlue pievelk komponentes pie apakšas (vai augšas, atkarībā no izvietojuma)
        panelis.add(Box.createVerticalGlue()); 
        
        // Pogu izvietojums (Sākot no apakšas uz augšu): Izveidot, Komanda, Visi, Turnīrs, Iziet

        // 1. IZVEIDOT POKEMONU - Vismazākā pozīcija Y asī (apakšā)
        panelis.add(VizualaMetodes.izveidotAttelaPogu("Izveidot", e -> izveidotPokemonu()));
        panelis.add(Box.createVerticalStrut(10)); 

        // 2. MANA KOMANDA
        panelis.add(VizualaMetodes.izveidotAttelaPogu("Komanda", e -> paraditKomandu()));
        panelis.add(Box.createVerticalStrut(10)); 

        // 3. VISI POKEMONI
        panelis.add(VizualaMetodes.izveidotAttelaPogu("Visi", e -> paraditVisus()));
        panelis.add(Box.createVerticalStrut(10)); 

        // 4. TURNĪRS
        panelis.add(VizualaMetodes.izveidotAttelaPogu("Turnirs", e -> organizetCinu()));
        panelis.add(Box.createVerticalStrut(10)); 
        
        // 5. IZVIET - Augšējā pozīcija Y asī
        panelis.add(VizualaMetodes.izveidotAttelaPogu("Iziet", e -> System.exit(0)));

        panelis.add(Box.createVerticalStrut(20)); // Atstarpe no apakšas

        return panelis;
    }
    
    // --- Jauns metodes, lai aizvietotu sākotnējo JOptionPane ---
    public static void saktSpeli() {
        // Izveidojam jaunu, mazāku dialogu
        JDialog dialogs = new JDialog((Frame) null, "Trenera vārds", true); // true padara to modālu
        dialogs.setUndecorated(true); // Tīrs dizains arī dialogam
        dialogs.setSize(350, 150);
        dialogs.setLayout(new BorderLayout(10, 10));
        dialogs.setLocationRelativeTo(null);

        JLabel zinojums = new JLabel("Sveicināti! Ievadiet savu Trenera vārdu:", SwingConstants.CENTER);
        JTextField vardaLauks = new JTextField(20);
        JButton apstiprinat = new JButton("Apstiprināt");

        // Paneli ievades un pogu novietošanai
        JPanel apaksejaDala = new JPanel(new FlowLayout());
        apaksejaDala.add(vardaLauks);
        apaksejaDala.add(apstiprinat);

        dialogs.add(zinojums, BorderLayout.NORTH);
        dialogs.add(apaksejaDala, BorderLayout.SOUTH);
        
        // Klausītājs
        apstiprinat.addActionListener(e -> {
            String vards = vardaLauks.getText().trim();
            if (!vards.isEmpty()) {
                speletajs = new Treneris(vards);
                dialogs.dispose(); // Aizver dialogu
                Metodes.info("Sveiks, treneri " + speletajs.getVards() + "!");
                SwingUtilities.invokeLater(() -> new Pokedatnis()); // Sākam galveno GUI
            } else {
                JOptionPane.showMessageDialog(dialogs, "Vārds nedrīkst būt tukšs!");
            }
        });
        
        // Ja logs tiek aizvērts/atcelts (kas sarežģīti, jo nav rāmja), aizveram programmu
        dialogs.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        dialogs.setVisible(true); // Blokus, kamēr lietotājs neievada vārdu
        
        // Pārbaudām, vai treneris tika inicializēts
        if (speletajs == null) System.exit(0);
    }
    
    // --- Galvenā MAIN daļa ---
    public static void main(String[] args) {
        // Tiek izsaukts jaunais dialogs pirms galvenā loga ielādes
        SwingUtilities.invokeLater(() -> saktSpeli());
    }

    // --- SPĒLES LOGIKAS METODES (Bez izmaiņām, tikai atkārtojums pilnīgai kopēšanai) ---

    public static void izveidotPokemonu() {
        String[] ipasnieki = {"Man (Trenerim)", "Savvaļas (Neviena)"};
        int kamPieder = JOptionPane.showOptionDialog(null, "Kam pieder šis pokemons?", "Īpašnieks",
                0, JOptionPane.QUESTION_MESSAGE, null, ipasnieki, ipasnieki[0]);
        
        if (kamPieder == -1) return;
        
        String ipasniekaVards = (kamPieder == 0) ? speletajs.getVards() : "Savvaļas";

        String[] tipi = {"Parastais", "Elektriskais", "Ūdens"};
        int tips = JOptionPane.showOptionDialog(null, "Izvēlieties tipu:", "Izveide",
                0, JOptionPane.QUESTION_MESSAGE, null, tipi, tipi[0]);
        
        if (tips == -1) return;

        String pVards = Metodes.ievaditTekstu("Ievadiet pokemona vārdu:");
        if (pVards == null) return;

        int hp = Metodes.ievaditSkaitliRobezas("Dzīvība (HP):", 50, 200);
        int atk = Metodes.ievaditSkaitliRobezas("Uzbrukuma spēks:", 5, 50);
        int def = Metodes.ievaditSkaitliRobezas("Aizsardzība (%):", 0, 75); 

        Pokemons jaunsP = null;
        
        if (tips == 0) {
            jaunsP = new ParastaisP(pVards, ipasniekaVards, hp, atk, def);
        } else if (tips == 1) {
            int volt = Metodes.ievaditSkaitliRobezas("Spriegums (Volti):", 10, 200);
            jaunsP = new ElektriskaisP(pVards, ipasniekaVards, hp, atk, def, volt);
        } else {
            jaunsP = new UdensP(pVards, ipasniekaVards, hp, atk, def);
        }

        visiPokemoni.add(jaunsP);
        if (kamPieder == 0) {
            speletajs.pievienotPokemonu(jaunsP);
        }
        Metodes.info("Pokemons veiksmīgi izveidots: " + pVards);
    }

    public static void paraditKomandu() {
        if (speletajs.getKomanda().isEmpty()) {
            Metodes.info("Kļūda! Jūsu komandā nav neviena pokemona.\nIzveidojiet pokemonu un izvēlieties 'Man (Trenerim)'.");
            return;
        }

        StringBuilder sb = new StringBuilder("Trenera " + speletajs.getVards() + " komanda:\n\n");
        for (Pokemons p : speletajs.getKomanda()) {
            sb.append(p.toString()).append("\n");
        }
        Metodes.info(sb.toString());
    }
    
    public static void paraditVisus() {
        if (visiPokemoni.isEmpty()) {
            Metodes.info("Kļūda! Saraksts ir tukšs. Vispirms izveidojiet pokemonu.");
            return;
        }

        StringBuilder sb = new StringBuilder("Visi reģistrētie pokemoni:\n\n");
        for (Pokemons p : visiPokemoni) {
            sb.append(p.toString()).append("\n");
        }
        Metodes.info(sb.toString());
    }

    public static void organizetCinu() {
        if (speletajs.getKomandasIzmers() == 0) {
            Metodes.info("Jums nav pokemonu cīņai! Vispirms izveidojiet savu komandu.");
            return;
        }

        Pokemons mans = null;
        if (speletajs.getKomandasIzmers() == 1) {
            mans = speletajs.getKomanda().get(0);
            Metodes.info("Cīņā piedalās jūsu vienīgais pokemons: " + mans.getVards());
        } else {
            mans = izveletiesPokemonu(speletajs.getKomanda(), "Kuru pokemonu sūtīsiet cīņā?");
        }
        if (mans == null) return; 

        ArrayList<Pokemons> ienaidnieki = new ArrayList<>();
        for (Pokemons p : visiPokemoni) {
            if (!p.getTreneris().equals(speletajs.getVards())) {
                ienaidnieki.add(p);
            }
        }

        if (ienaidnieki.isEmpty()) {
            Metodes.info("Nav pieejamu pretinieku!\nVisi pokemoni pieder jums.\nIzveidojiet jaunu pokemonu ar īpašnieku 'Savvaļas'.");
            return;
        }
        
        Pokemons pretinieks = izveletiesPokemonu(ienaidnieki, "Izvēlieties pretinieku:");
        if (pretinieks == null) return;

        kaujasProcess(mans, pretinieks);
    }

    public static void kaujasProcess(Pokemons p1, Pokemons p2) {
        boolean cinaNotiek = true;
        
        while (cinaNotiek) {
            String[] darbibas = {"Uzbrukt", "Dziedēt", "Speciālā spēja", "Bēgt"};
            String statuss = String.format("JŪSU: %s (HP: %d)\nPRETINIEKS: %s (HP: %d)", 
                    p1.getVards(), p1.getDziviba(), p2.getVards(), p2.getDziviba());
            
            int gajiens = JOptionPane.showOptionDialog(null, statuss + "\n\nJūsu gājiens!", "Cīņa",
                    0, JOptionPane.PLAIN_MESSAGE, null, darbibas, darbibas[0]);

            String mansGajiensTeksts = "";
            
            if (gajiens == 3 || gajiens == -1) { 
                Metodes.info("Jūs aizbēgāt no cīņas!");
                return;
            }

            switch(gajiens) {
                case 0: mansGajiensTeksts = p1.uzbrukt(p2); break;
                case 1: mansGajiensTeksts = p1.dziedet(); break;
                case 2: 
                    if (p1 instanceof ElektriskaisP) mansGajiensTeksts = ((ElektriskaisP)p1).uzladet();
                    else if (p1 instanceof UdensP) mansGajiensTeksts = ((UdensP)p1).nirt();
                    else mansGajiensTeksts = p1.getVards() + " koncentrējas (nav spec. spēju)";
                    break;
            }

            if (p2.getDziviba() <= 0) {
                Metodes.info(mansGajiensTeksts + "\n\nUZVARA! " + p1.getVards() + " uzvarēja!");
                p1.attistit(); 
                break;
            }

            String pretiniekaGajiensTeksts = p2.uzbrukt(p1);

            if (p1.getDziviba() <= 0) {
                Metodes.info(mansGajiensTeksts + "\n" + pretiniekaGajiensTeksts + "\n\nJŪS ZAUDĒJĀT! " + p1.getVards() + " zaudēja samaņu.");
                break;
            }

            JOptionPane.showMessageDialog(null, "RAUNDA REZULTĀTS:\n\n" + mansGajiensTeksts + "\nVS\n" + pretiniekaGajiensTeksts);
        }
    }

    public static Pokemons izveletiesPokemonu(ArrayList<Pokemons> saraksts, String virsraksts) {
        String[] nosaukumi = new String[saraksts.size()];
        for (int i = 0; i < saraksts.size(); i++) {
            nosaukumi[i] = saraksts.get(i).toString();
        }
        
        String izvele = (String) JOptionPane.showInputDialog(null, virsraksts, "Izvēle", 
                JOptionPane.QUESTION_MESSAGE, null, nosaukumi, nosaukumi[0]);
        
        if (izvele == null) return null;
        
        for (Pokemons p : saraksts) {
            if (p.toString().equals(izvele)) return p;
        }
        return null;
    }
}