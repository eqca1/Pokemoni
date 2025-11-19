package glotovs_cariks;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Pokedatnis {

    static Treneris speletajs; 
    static ArrayList<Pokemons> visiPokemoni = new ArrayList<>(); 

    public static void main(String[] args) {
        
        String vards = Metodes.ievaditTekstu("Sveicināti Pokemonu pasaulē!\nIevadiet savu Trenera vārdu:");
        
        if (vards == null) {
            System.exit(0);
        }
        
        speletajs = new Treneris(vards);
        Metodes.info("Sveiks, treneri " + speletajs.getVards() + "!");

        boolean darbaRezims = true;
        while (darbaRezims) {
            String[] izvelne = {"Izveidot Pokemonu", "Mana komanda", "Visi pasaules pokemoni", "TURNĪRS (Cīņa)", "Iziet"};
            int izvele = JOptionPane.showOptionDialog(null, "Izvēlne. Treneris: " + speletajs.getVards(), "Pokedatnis",
                    0, JOptionPane.INFORMATION_MESSAGE, null, izvelne, izvelne[0]);

            switch (izvele) {
                case 0: izveidotPokemonu(); break;
                case 1: paraditKomandu(); break;
                case 2: paraditVisus(); break;
                case 3: organizetCinu(); break;
                case 4: case -1: darbaRezims = false; break;
            }
        }
    }

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
        int def = Metodes.ievaditSkaitliRobezas("Aizsardzība (%):", 0, 30); 

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