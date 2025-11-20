package glotovs_cariks;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// GALVENAIS SPĒLES LOGS
public class Pokedatnis extends JFrame { 

    private static Treneris speletajs; 
    private static ArrayList<Pokemons> visiPokemoni = new ArrayList<>(); 
    
    private JPanel galvenaisPanelis;
    private float caurspidigums = 0.0f;

    public Pokedatnis() {
        // LOGA IESTATĪJUMI
        setTitle("Pokedatnis - Pokemonu Cīņu Sistēma"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        setLocationRelativeTo(null);

        // GALVENAIS PANELIS AR APAĻIEM STŪRIEM
        galvenaisPanelis = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(VizualaMetodes.GALVENAIS_FONS);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                g2.setColor(VizualaMetodes.AKCENTS);
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(2, 2, getWidth()-4, getHeight()-4, 50, 50);
            }
        };
        galvenaisPanelis.setBorder(new EmptyBorder(25, 25, 25, 25));
        setContentPane(galvenaisPanelis);

        // AUGŠĒJĀ JOSLA - VIRSRKSTS UN AIZVĒRŠANAS POGA
        JPanel augsa = new JPanel(new BorderLayout());
        augsa.setOpaque(false);
        
        JLabel virsraksts = new JLabel("  POKEDATNIS - POKEMONU CĪŅU ARĒNA", SwingConstants.LEFT);
        virsraksts.setFont(VizualaMetodes.FONTS_VIRSRKSTS);
        virsraksts.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        
        JButton aizvertPoga = VizualaMetodes.izveidotStiliguPogu("X", e -> System.exit(0));
        aizvertPoga.setBackground(new Color(192, 57, 43));
        aizvertPoga.setPreferredSize(new Dimension(60, 45));

        augsa.add(virsraksts, BorderLayout.CENTER);
        augsa.add(aizvertPoga, BorderLayout.EAST);
        galvenaisPanelis.add(augsa, BorderLayout.NORTH);

        // CENTRĀLAIS SATURS
        JPanel saturs = new JPanel(new GridLayout(1, 2, 30, 0));
        saturs.setOpaque(false);
        saturs.setBorder(new EmptyBorder(30, 0, 30, 0));

        // KREISĀ PUSE - INFORMĀCIJAS PANELIS
        JPanel kreisaPuse = VizualaMetodes.izveidotApaluPaneli();
        kreisaPuse.setLayout(new BorderLayout());
        kreisaPuse.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel attels = VizualaMetodes.ieladetFonu("pokemons_fons.png", 400, 400);
        if (attels.getIcon() == null) {
            attels.setText("<html><center>Sveicināti Pokemonu Arēnā!<br><br>Trenera vārds: " + speletajs.getVards() + 
                          "<br>Pokemonu skaits: " + visiPokemoni.size() + 
                          "<br><br>Izvēlieties darbību labajā pusē!</center></html>");
            attels.setFont(VizualaMetodes.FONTS_TEKSTS);
            attels.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
            attels.setHorizontalAlignment(SwingConstants.CENTER);
        }
        kreisaPuse.add(attels, BorderLayout.CENTER);

        // LABĀ PUSE - IZVĒLŅU POGA
        JPanel labaPuse = new JPanel();
        labaPuse.setOpaque(false);
        labaPuse.setLayout(new GridLayout(6, 1, 0, 15)); 

        // GALVENĀS POGA
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Izveidot Jaunu Pokemonu", e -> izveidotPokemonu()));
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Mana Pokemonu Komanda", e -> paraditManoKomandu()));
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Visu Pokemonu Saraksts", e -> paraditVisusPokemonus()));
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Sākt Cīņu", e -> saktiesCinu()));
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Spēles Noteikumi", e -> paraditNoteikumus()));
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Iziet no Spēles", e -> System.exit(0)));

        saturs.add(kreisaPuse);
        saturs.add(labaPuse);
        galvenaisPanelis.add(saturs, BorderLayout.CENTER);

        // PANELIS APAKŠĀ - STATISTIKA
        JPanel apaksa = new JPanel(new FlowLayout(FlowLayout.CENTER));
        apaksa.setOpaque(false);
        JLabel statistika = new JLabel("Treneris: " + speletajs.getVards() + " | Komandā: " + 
                                     speletajs.getKomandasIzmers() + " pokemoni | Kopā datubāzē: " + 
                                     visiPokemoni.size() + " pokemoni");
        statistika.setForeground(VizualaMetodes.TEKSTS_PELMONS);
        statistika.setFont(VizualaMetodes.FONTS_TEKSTS);
        apaksa.add(statistika);
        galvenaisPanelis.add(apaksa, BorderLayout.SOUTH);

        // PĀRVIETOŠANAS FUNKCIJA
        VizualaMetodes.padaritParietojamu(galvenaisPanelis, this);
        
        setVisible(true);
        animacijaParadities();
    }

    // ANIMĀCIJA - LOGS PARĀDĀS LĒNI
    private void animacijaParadities() {
        setOpacity(0.0f);
        Timer timer = new Timer(25, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caurspidigums += 0.04f;
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
    
    // SPĒLES SĀKUMS - TRENERA IEVADE
    public static void saktSpeli() {
        String vards = Metodes.ievaditTekstu("Sveiks, drosmīgais Pokemonu treneri! Ievadi savu vārdu:");
        
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

    // === METODES ===
    
    // IZVEIDOT JAUNU POKEMONU
    public static void izveidotPokemonu() {
        String[] ipasnieki = {"Trenerim", "Savvaļas Pokemon"};
        int kamPiederIndex = Metodes.raditIzveli("Pokemona Īpašnieks", "Kam piederēs šis pokemons?", ipasnieki);
        if(kamPiederIndex == -1) return;
        
        String ipasnieks = (kamPiederIndex == 0) ? speletajs.getVards() : "Savvaļas";
        
        String[] tipi = {"Parastais", "Elektriskais", "Ūdens"};
        int tipsIndex = Metodes.raditIzveli("Pokemona Tips", "Izvēlies pokemona tipu:", tipi);
        if (tipsIndex == -1) return;
        
        String pokemonaVards = Metodes.ievaditTekstu("Ievadi pokemona vārdu:");
        if(pokemonaVards == null) return;
        
        int dziviba = Metodes.ievaditSkaitliRobezas("Pokemona dzīvības punkti (HP):", 50, 250);
        int uzbrukums = Metodes.ievaditSkaitliRobezas("Uzbrukuma spēks (ATK):", 10, 60);
        int aizsardziba = Metodes.ievaditSkaitliRobezas("Aizsardzības līmenis (DEF %):", 5, 40);
        
        Pokemons jaunaisPokemons = null;
        
        if(tipsIndex == 0) {
            jaunaisPokemons = new ParastaisP(pokemonaVards, ipasnieks, dziviba, uzbrukums, aizsardziba);
        } else if(tipsIndex == 1) {
            int voltaza = Metodes.ievaditSkaitliRobezas("Elektriskā pokemona voltāža (V):", 20, 250);
            jaunaisPokemons = new ElektriskaisP(pokemonaVards, ipasnieks, dziviba, uzbrukums, aizsardziba, voltaza);
        } else {
            jaunaisPokemons = new UdensP(pokemonaVards, ipasnieks, dziviba, uzbrukums, aizsardziba);
        }
        
        visiPokemoni.add(jaunaisPokemons);
        if(kamPiederIndex == 0) {
            speletajs.pievienotPokemonu(jaunaisPokemons);
            Metodes.info("Veiksmīgi izveidots un pievienots tavai komandai: " + pokemonaVards);
        } else {
            Metodes.info("Veiksmīgi izveidots savvaļas pokemons: " + pokemonaVards);
        }
    }

    // RĀDĪT MANU KOMANDU
    public static void paraditManoKomandu() {
        if (speletajs.getKomanda().isEmpty()) { 
            Metodes.info("Tava komanda ir tukša! Izveido savus pirmos pokemonus."); 
            return; 
        }

        JDialog dialogs = new JDialog((Frame)null, true);
        dialogs.setUndecorated(true);
        dialogs.setBackground(new Color(0,0,0,0));
        dialogs.setSize(650, 550);
        dialogs.setLocationRelativeTo(null);

        JPanel panelis = new JPanel(new BorderLayout());
        panelis.setBackground(VizualaMetodes.GALVENAIS_FONS);
        panelis.setBorder(BorderFactory.createLineBorder(VizualaMetodes.AKCENTS, 2));
        
        // VIRSRKSTS
        JLabel virsraksts = new JLabel("MANA POKEMONU KOMANDA - Treneris: " + speletajs.getVards(), SwingConstants.CENTER);
        virsraksts.setFont(VizualaMetodes.FONTS_VIRSRKSTS);
        virsraksts.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        virsraksts.setBorder(new EmptyBorder(20,0,20,0));
        panelis.add(virsraksts, BorderLayout.NORTH);

        // TEKSTA LAUKS AR POKEMONU INFORMĀCIJU
        JTextArea tekstaLauks = VizualaMetodes.izveidotStiliguTekstaApgabalu();
        tekstaLauks.setEditable(false);
        
        // ATJAUNOT TEKSTU
        Runnable atjaunotTekstu = () -> {
            String info = "";
            int numurs = 1;
            for (Pokemons p : speletajs.getKomanda()) {
                info += numurs + ". " + p.toString() + "\n\n";
                numurs++;
            }
            tekstaLauks.setText(info);
            tekstaLauks.setCaretPosition(0);
        };
        atjaunotTekstu.run();

        JScrollPane scroll = VizualaMetodes.izveidotStiliguScrollPane(tekstaLauks);
        scroll.setBorder(new EmptyBorder(15, 25, 15, 25));
        panelis.add(scroll, BorderLayout.CENTER);

        // APAKŠĒJAIS PANELIS AR POGA
        JPanel poguPanelis = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
        poguPanelis.setOpaque(false);

        JButton dziedetPoga = VizualaMetodes.izveidotStiliguPogu("Dziedēt Visus Pokemonus", 
            VizualaMetodes.ZALS_IESPEJAMS, e -> {
                for (Pokemons p : speletajs.getKomanda()) {
                    while(p.getDziviba() < p.getMaxDziviba()) {
                        p.dziedet();
                    }
                }
                atjaunotTekstu.run();
                Metodes.info("Visi pokemoni komandā ir pilnībā izārstēti un atguvuši spēkus!");
            });

        JButton aizvertPoga = VizualaMetodes.izveidotStiliguPogu("Aizvērt", e -> dialogs.dispose());

        poguPanelis.add(dziedetPoga);
        poguPanelis.add(aizvertPoga);
        panelis.add(poguPanelis, BorderLayout.SOUTH);

        VizualaMetodes.padaritParietojamu(panelis, dialogs);
        dialogs.add(panelis);
        dialogs.setVisible(true);
    }

    // RĀDĪT VISUS POKEMONUS
    public static void paraditVisusPokemonus() {
        if (visiPokemoni.isEmpty()) { 
            Metodes.info("Sarakstā vēl nav reģistrētu pokemonu."); 
            return; 
        }
        
        JDialog dialogs = new JDialog((Frame)null, true);
        dialogs.setUndecorated(true);
        dialogs.setBackground(new Color(0,0,0,0));
        dialogs.setSize(750, 600);
        dialogs.setLocationRelativeTo(null);

        JPanel panelis = new JPanel(new BorderLayout());
        panelis.setBackground(VizualaMetodes.GALVENAIS_FONS);
        panelis.setBorder(BorderFactory.createLineBorder(VizualaMetodes.AKCENTS, 2));
        
        JLabel virsraksts = new JLabel("VISI POKEMONI POKEDATNĪ - Kopā: " + visiPokemoni.size(), SwingConstants.CENTER);
        virsraksts.setFont(VizualaMetodes.FONTS_VIRSRKSTS);
        virsraksts.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        virsraksts.setBorder(new EmptyBorder(20,0,20,0));
        panelis.add(virsraksts, BorderLayout.NORTH);

        JTextArea tekstaLauks = VizualaMetodes.izveidotStiliguTekstaApgabalu();
        tekstaLauks.setEditable(false);
        
        String visiPokemoniInfo = "";
        int numurs = 1;
        for (Pokemons p : visiPokemoni) {
            visiPokemoniInfo += numurs + ". " + p.toString() + "\n\n";
            numurs++;
        }
        tekstaLauks.setText(visiPokemoniInfo);
        tekstaLauks.setCaretPosition(0);

        JScrollPane scroll = VizualaMetodes.izveidotStiliguScrollPane(tekstaLauks);
        scroll.setBorder(new EmptyBorder(15, 25, 15, 25));
        panelis.add(scroll, BorderLayout.CENTER);

        JButton aizvertPoga = VizualaMetodes.izveidotStiliguPogu("Aizvērt", e -> dialogs.dispose());
        JPanel poguPanelis = new JPanel();
        poguPanelis.setOpaque(false);
        poguPanelis.add(aizvertPoga);
        poguPanelis.setBorder(new EmptyBorder(0, 0, 20, 0));
        panelis.add(poguPanelis, BorderLayout.SOUTH);

        VizualaMetodes.padaritParietojamu(panelis, dialogs);
        dialogs.add(panelis);
        dialogs.setVisible(true);
    }

    // SĀKT CĪŅU
    public static void saktiesCinu() {
        if (speletajs.getKomandasIzmers() == 0) { 
            Metodes.info("Tev nav pokemonu cīņai! Vispirms izveido savu komandu."); 
            return; 
        }
        
        // IZVĒLĒTIES SAVU POKEMONU
        ArrayList<Pokemons> manaKomanda = speletajs.getKomanda();
        String[] manaKomandaVardi = new String[manaKomanda.size()];
        for (int i = 0; i < manaKomanda.size(); i++) {
            manaKomandaVardi[i] = manaKomanda.get(i).getVards() + " (HP: " + 
                                 manaKomanda.get(i).getDziviba() + "/" + 
                                 manaKomanda.get(i).getMaxDziviba() + ")";
        }
        
        int manaIzvele = Metodes.raditIzveli("Izvēlies savu cīnītāju", "Kuru pokemonu sūtīsi cīņā?", manaKomandaVardi);
        if (manaIzvele == -1) return;
        
        Pokemons mansPokemons = manaKomanda.get(manaIzvele);
        
        // IZVĒLĒTIES PRETINIEKU
        ArrayList<Pokemons> pieejamiePretinieki = new ArrayList<>();
        for (Pokemons p : visiPokemoni) {
            if (!p.getTreneris().equals(speletajs.getVards()) && p.getDziviba() > 0) {
                pieejamiePretinieki.add(p);
            }
        }
        
        if (pieejamiePretinieki.isEmpty()) {
            Metodes.info("Šobrīd nav pieejamu pretinieku! Izveido savvaļas pokemonus.");
            return;
        }
        
        String[] pretiniekuVardi = new String[pieejamiePretinieki.size()];
        for (int i = 0; i < pieejamiePretinieki.size(); i++) {
            pretiniekuVardi[i] = pieejamiePretinieki.get(i).getVards() + " - " + 
                                pieejamiePretinieki.get(i).getTipaNosaukums() + " (HP: " + 
                                pieejamiePretinieki.get(i).getDziviba() + ")";
        }
        
        int pretiniekaIzvele = Metodes.raditIzveli("Izvēlies pretinieku", "Pretinieks cīņai:", pretiniekuVardi);
        if (pretiniekaIzvele == -1) return;
        
        Pokemons pretinieks = pieejamiePretinieki.get(pretiniekaIzvele);
        
        // SĀKT CĪŅU
        kaujasProcess(mansPokemons, pretinieks);
    }

    // KAUJAS PROCESS
    public static void kaujasProcess(Pokemons mansPokemons, Pokemons pretinieks) {
        int raunds = 1;
        
        while (mansPokemons.getDziviba() > 0 && pretinieks.getDziviba() > 0) {
            String statuss = String.format("=== RAUNDS %d ===\n\nTAVS: %s (%d/%d HP)\nPRETINIEKS: %s (%d/%d HP)", 
                    raunds, mansPokemons.getVards(), mansPokemons.getDziviba(), mansPokemons.getMaxDziviba(),
                    pretinieks.getVards(), pretinieks.getDziviba(), pretinieks.getMaxDziviba());
            
            String[] darbibas = {"Uzbrukt", "Dziedēt", "Speciālā Spēja", "Bēgt no Cīņas"};
            int gajiens = Metodes.raditIzveli("Cīņas Raunds " + raunds, statuss, darbibas);
            
            if(gajiens == -1 || gajiens == 3) {
                Metodes.info("Tu aizbēgi no cīņas! " + pretinieks.getVards() + " uzvarēja.");
                break;
            }
            
            String mansGajiens = "";
            if(gajiens == 0) {
                mansGajiens = mansPokemons.uzbrukt(pretinieks);
            } else if(gajiens == 1) {
                mansGajiens = mansPokemons.dziedet();
            } else {
                // SPECIĀLĀ SPĒJA
                if (mansPokemons instanceof ElektriskaisP) {
                    mansGajiens = ((ElektriskaisP)mansPokemons).uzladet();
                } else if (mansPokemons instanceof UdensP) {
                    mansGajiens = ((UdensP)mansPokemons).nirt();
                } else {
                    mansGajiens = mansPokemons.getVards() + " nav speciālo spēju! Automātisks uzbrukums.";
                    mansGajiens = mansPokemons.uzbrukt(pretinieks);
                }
            }
            
            // PĀRBAUDĪT VAI PRETINIEKS IR ZAUDĒJIS
            if(pretinieks.getDziviba() <= 0) {
                Metodes.info("=== UZVARA! ===\n" + mansGajiens + "\n\n" + 
                           pretinieks.getVards() + " tika pieveikts!\n\n" +
                           mansPokemons.getVards() + " saņēma pieredzi un attīstījās!");
                mansPokemons.attistit();
                break;
            }
            
            // PRETINIEKA GĀJIENS
            String pretiniekaGajiens = pretinieks.uzbrukt(mansPokemons);
            
            // PĀRBAUDĪT VAI SPĒLĒTĀJS IR ZAUDĒJIS
            if (mansPokemons.getDziviba() <= 0) {
                Metodes.info("=== ZAUDĒJUMS! ===\n" + mansGajiens + "\n\nPRETINIEKA GĀJIENS:\n" + 
                           pretiniekaGajiens + "\n\nTavs pokemons " + mansPokemons.getVards() + " zaudēja samaņu.");
                break;
            }
            
            // RAUNDA REZULTĀTS
            Metodes.info("=== RAUNDA " + raunds + " REZULTĀTS ===\n" + 
                        "Tavs gājiens:\n" + mansGajiens + "\n\n" +
                        "Pretinieka gājiens:\n" + pretiniekaGajiens + "\n\n" +
                        "=== PĒC RAUNDA ===\n" +
                        "Tavs pokemons: " + mansPokemons.getDziviba() + "/" + mansPokemons.getMaxDziviba() + " HP\n" +
                        "Pretinieks: " + pretinieks.getDziviba() + "/" + pretinieks.getMaxDziviba() + " HP");
            
            raunds++;
        }
    }

    // RĀDĪT NOTEIKUMUS
    public static void paraditNoteikumus() {
        String noteikumi = "=== POKEMONU CĪŅU NOTEIKUMI ===\n\n" +
                          "1. KATRS POKEMONS IR SAVA TIPA\n" +
                          "   • Parastais - vienkārši uzbrukumi\n" +
                          "   • Elektriskais - augsta voltāža, spēcīgi uzbrukumi\n" +
                          "   • Ūdens - var nirt, negaidīti uzbrukt\n\n" +
                          "2. CĪŅAS MECHANIKA\n" +
                          "   • Katrs pokemons ir HP, ATK, DEF\n" +
                          "   • Aizsardzība samazina saņemto bojājumu\n" +
                          "   • Speciālās spējas dod papildu priekšrocības\n\n" +
                          "3. ATTĪSTĪBA\n" +
                          "   • Uzvarot cīņās, pokemoni iegūst līmeni\n" +
                          "   • Katrs līmenis palielina HP, ATK, DEF\n\n" +
                          "4. STRATĒĢIJA\n" +
                          "   • Izmanto dziedēšanu, kad HP zems\n" +
                          "   • Speciālās spējas dod taktisko priekšrocību\n" +
                          "   • Veido dažādu tipu komandu!";
        
        VizualaMetodes.paraditIzklaidesLogu("Spēles Noteikumi", noteikumi);
    }
}