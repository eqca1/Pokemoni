package glotovs_cariks;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Pokedatnis extends JFrame { 

    private static Treneris speletajs; 
    private static ArrayList<Pokemons> visiPokemoni = new ArrayList<>(); 
    
    private JPanel galvenaisPanelis;
    private float caurspidigums = 0.0f;
    
    private static final int DEFAULT_HP = 150;
    private static final int DEFAULT_ATK = 35;
    private static final int DEFAULT_DEF = 20;
    private static final int DEFAULT_VOLTAZA = 120;

    public Pokedatnis() {
        setTitle("Pokedatnis - Pokemonu Cīņu Sistēma"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        setLocationRelativeTo(null);

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

        JPanel augsa = new JPanel(new BorderLayout());
        augsa.setOpaque(false);
        
        JLabel virsraksts = new JLabel("  POKEDATNIS - POKEMONU CĪŅU ARĒNA", SwingConstants.LEFT);
        virsraksts.setFont(VizualaMetodes.FONTS_VIRSRKSTS);
        virsraksts.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        
        JButton aizvertPoga = VizualaMetodes.izveidotStiliguPogu("X", VizualaMetodes.SARKANS_AIZVERT, e -> System.exit(0));
        aizvertPoga.setPreferredSize(new Dimension(60, 45));

        augsa.add(virsraksts, BorderLayout.CENTER);
        augsa.add(aizvertPoga, BorderLayout.EAST);
        galvenaisPanelis.add(augsa, BorderLayout.NORTH);

        JPanel saturs = new JPanel(new GridLayout(1, 2, 30, 0));
        saturs.setOpaque(false);
        saturs.setBorder(new EmptyBorder(30, 0, 30, 0));

        JPanel kreisaPuse = VizualaMetodes.izveidotApaluPaneli();
        kreisaPuse.setLayout(new BorderLayout());
        kreisaPuse.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel attels = new JLabel("VIETA GIF/ANIMĀCIJAI (400x400)", SwingConstants.CENTER); 
        attels.setFont(VizualaMetodes.FONTS_VIRSRKSTS);
        attels.setForeground(VizualaMetodes.TEKSTS_GALVENAIS);
        attels.setPreferredSize(new Dimension(400, 400));
        
        kreisaPuse.add(attels, BorderLayout.CENTER);

        
        JPanel labaPuse = new JPanel();
        labaPuse.setOpaque(false);
        labaPuse.setLayout(new GridLayout(6, 1, 0, 15)); 

        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Izveidot Jaunu Pokemonu", e -> izveidotPokemonu()));
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Mana Pokemonu Komanda", e -> paraditManoKomandu()));
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Visu Pokemonu Saraksts", e -> paraditVisusPokemonus()));
        labaPuse.add(VizualaMetodes.izveidotStiliguPogu("Sākt Cīņu", e -> saktiesCinu()));

        saturs.add(kreisaPuse);
        saturs.add(labaPuse);
        galvenaisPanelis.add(saturs, BorderLayout.CENTER);

        VizualaMetodes.padaritParietojamu(galvenaisPanelis, this);
        
        setVisible(true);
        animacijaParadities();
    }

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

    
    public static void izveidotPokemonu() {
        String[] ipasnieki = {"Trenerim", "Savvaļas Pokemon"};
        int kamPiederIndex = Metodes.raditIzveli("Pokemona Īpašnieks", "Kam piederēs šis pokemons?", ipasnieki);
        if(kamPiederIndex == -1) return;
        
        String ipasnieks = (kamPiederIndex == 0) ? speletajs.getVards() : "Savvaļas";
        
        String[] tipi = {"Parastais", "Elektriskais", "Ūdens"};
        int tipsIndex = Metodes.raditIzveli("Pokemona Tips", "Izvēlies pokemona tipu:", tipi);
        if (tipsIndex == -1) return;
        
        String pokemonaVards = Metodes.ievaditTekstu("Ievadi pokemona vārdu (vai atstāj tukšu, lai izmantotu noklusējuma vārdu):");
        if(pokemonaVards == null) return;
        
        if(pokemonaVards.trim().isEmpty()) {
            pokemonaVards = "Default" + tipi[tipsIndex];
        }
        
        int dziviba = Metodes.ievaditSkaitliRobezas("Pokemona dzīvības punkti (HP):", 50, 250);
        if (dziviba == -1) dziviba = DEFAULT_HP;
        
        int uzbrukums = Metodes.ievaditSkaitliRobezas("Uzbrukuma spēks (ATK):", 10, 60);
        if (uzbrukums == -1) uzbrukums = DEFAULT_ATK;
        
        int aizsardziba = Metodes.ievaditSkaitliRobezas("Aizsardzības līmenis (DEF %):", 5, 40);
        if (aizsardziba == -1) aizsardziba = DEFAULT_DEF;
        
        Pokemons jaunaisPokemons = null;
        
        if(tipsIndex == 0) {
            jaunaisPokemons = new ParastaisP(pokemonaVards, ipasnieks, dziviba, uzbrukums, aizsardziba);
        } else if(tipsIndex == 1) {
            int voltaza = Metodes.ievaditSkaitliRobezas("Elektriskā pokemona voltāža (V):", 20, 250);
            if (voltaza == -1) voltaza = DEFAULT_VOLTAZA;
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

    public static void paraditManoKomandu() {
        if (speletajs.getKomanda().isEmpty()) {
            Metodes.info("Tavā komandā nav neviena pokemona! Izveido jaunu!");
            return;
        }

        String komandaInfo = "";
        int numurs = 1;
        for (Pokemons p : speletajs.getKomanda()) {
            komandaInfo += numurs + ". " + p.toString() + "\n\n";
            numurs++;
        }
        
        VizualaMetodes.raditDialogu("Tava Pokemonu Komanda - Kopā: " + speletajs.getKomandasIzmers(), komandaInfo);
    }

    public static void paraditVisusPokemonus() {
        if (visiPokemoni.isEmpty()) {
            Metodes.info("Pokedatnī nav neviena pokemona. Sāc spēli, izveidojot dažus!");
            return;
        }
        
        String visiPokemoniInfo = "";
        int numurs = 1;
        for (Pokemons p : visiPokemoni) {
            visiPokemoniInfo += numurs + ". " + p.toString() + "\n\n";
            numurs++;
        }
        
        VizualaMetodes.raditDialogu("VISI POKEMONI POKEDATNĪ - Kopā: " + visiPokemoni.size(), visiPokemoniInfo);
    }
    
    public static void saktiesCinu() {
        if (speletajs.getKomanda().isEmpty()) {
            Metodes.info("Tev vispirms jāizveido vismaz viens pokemons, lai sāktu cīņu!");
            return;
        }
        
        if (visiPokemoni.size() < 2) {
             Metodes.info("Pokedatnī nav pietiekami daudz pokemonu. Izveido vismaz divus, lai varētu sākt cīņu!");
            return;
        }
        
        Pokemons mansPokemons = speletajs.getKomanda().get(0); 
        Pokemons pretinieks = null;
        
        for (Pokemons p : visiPokemoni) {
            if (p.getTreneris().equals("Savvaļas")) {
                pretinieks = p;
                break;
            }
        }
        
        if (pretinieks == null) {
            Metodes.info("Nav neviena 'Savvaļas' pokemona, ar ko cīnīties. Izveido jaunu 'Savvaļas' pokemonu!");
            return;
        }
        
        Metodes.info("SĀKAS CĪŅA!\n" + 
                     mansPokemons.getVards() + " (Tavs) pret " + 
                     pretinieks.getVards() + " (Savvaļas)");
                     
        int raunds = 0;
        while(mansPokemons.getDziviba() > 0 && pretinieks.getDziviba() > 0) {
            raunds++;
            String mansGajiens;
            
            String[] opcijas = {"Uzbrukt", "Dziedēt"};
            if (mansPokemons instanceof ElektriskaisP) {
                opcijas = new String[]{"Uzbrukt", "Dziedēt", "Uzlādēt"};
            } else if (mansPokemons instanceof UdensP) {
                opcijas = new String[]{"Uzbrukt", "Dziedēt", "Nirt Zem Ūdens"};
            } else if (mansPokemons instanceof ParastaisP) {
                 opcijas = new String[]{"Uzbrukt", "Dziedēt", "Atspēkošs Trieciens"};
            }
            
            int gajiens = Metodes.raditIzveli("CĪŅAS GAJIENS - Raunds " + raunds, 
                                             "Ko darīs " + mansPokemons.getVards() + "?\nHP: " + mansPokemons.getDziviba() + "/" + mansPokemons.getMaxDziviba() + 
                                             " | Pretinieks HP: " + pretinieks.getDziviba() + "/" + pretinieks.getMaxDziviba(),
                                             opcijas);
                                             
            if(gajiens == -1) {
                Metodes.info("Cīņa apturēta!");
                return;
            }

            if(gajiens == 0) {
                mansGajiens = mansPokemons.uzbrukt(pretinieks);
            } else if(gajiens == 1) {
                mansGajiens = mansPokemons.dziedet();
            } else {
                if (mansPokemons instanceof ElektriskaisP) {
                    mansGajiens = ((ElektriskaisP)mansPokemons).uzladet();
                } else if (mansPokemons instanceof UdensP) {
                    mansGajiens = ((UdensP)mansPokemons).nirtZemUdens();
                } else if (mansPokemons instanceof ParastaisP) {
                    mansGajiens = ((ParastaisP)mansPokemons).atspejosaisTrieciens();
                } else {
                    mansGajiens = "Kļūda! Automātisks uzbrukums.";
                    mansGajiens = mansPokemons.uzbrukt(pretinieks);
                }
            }
            
            if(pretinieks.getDziviba() <= 0) {
                Metodes.info("=== UZVARA! ===\n" + mansGajiens + "\n\n" + 
                           pretinieks.getVards() + " tika pieveikts!\n\n" +
                           mansPokemons.getVards() + " saņēma pieredzi un attīstījās!");
                mansPokemons.attistit();
                break;
            }
            
            String pretiniekaGajiens = pretinieks.uzbrukt(mansPokemons);
            
            if (mansPokemons.getDziviba() <= 0) {
                Metodes.info("=== ZAUDĒJUMS! ===\n" + mansGajiens + "\n\nPRETINIEKA GĀJIENS:\n" + 
                           pretiniekaGajiens + "\n\nTavs pokemons " + mansPokemons.getVards() + " zaudēja samaņu.");
                break;
            }
            
            Metodes.info("=== RAUNDA " + raunds + " REZULTĀTS ===\n" + 
                        "Tavs gājiens:\n" + mansGajiens + "\n\n" +
                        "Pretinieka gājiens:\n" + pretiniekaGajiens + "\n\n" +
                        mansPokemons.getVards() + " HP: " + mansPokemons.getDziviba() + "/" + mansPokemons.getMaxDziviba() + "\n" +
                        pretinieks.getVards() + " HP: " + pretinieks.getDziviba() + "/" + pretinieks.getMaxDziviba());
        }
    }
}