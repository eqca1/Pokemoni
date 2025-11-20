package glotovs_cariks;

import java.util.Random;

// PARASTAIS POKEMONS - PAMATA TIPS
public class ParastaisP extends Pokemons {
    private int attistibasPunkti; // Papildu parametrs attīstībai

    public ParastaisP(String vards, String treneris, int maxDziviba, int uzbrukumaSpeks, int aizsardziba) {
        super(vards, treneris, maxDziviba, uzbrukumaSpeks, aizsardziba);
        this.attistibasPunkti = 0;
    }

    @Override
    public String getTipaNosaukums() {
        return "Parastais";
    }

    @Override
    public String uzbrukt(Pokemons pretinieks) {
        Random rand = new Random();
        // Parastajam pokemonam vienkāršs uzbrukums ar nelielu izlases bojājumu
        int pamatBojajums = this.getUzbrukumaSpeks();
        int izlasesBojajums = rand.nextInt(6); // 0-5 papildus bojājums
        int kopejaisBojajums = pamatBojajums + izlasesBojajums;
        
        pretinieks.sanemtBojajumus(kopejaisBojajums);
        
        // Palielina attīstības punktus par veikto uzbrukumu
        this.attistibasPunkti++;
        
        return this.getVards() + " veic parasto triecienu! Bojājumi: " + kopejaisBojajums + " HP";
    }

    // SPECIĀLĀ SPĒJA - ATSPĒKOŠS TRIECIENS
    public String atspejosaisTrieciens() {
        Random rand = new Random();
        int iespeja = rand.nextInt(100);
        
        if (iespeja < 30) { // 30% iespēja uz atspēkošu triecienu
            this.attistibasPunkti += 2;
            return this.getVards() + " veic atspēkošu triecienu! Nākamais uzbrukums būs divreiz spēcīgāks!";
        } else {
            return this.getVards() + " mēģina veikt atspēkošu triecienu, bet neveiksmīgi!";
        }
    }

    @Override
    public void attistit() {
        super.attistit();
        this.attistibasPunkti = 0; // Resetē punktus pēc attīstības
    }

    @Override
    public String toString() {
        return super.toString() + " | Attīstības punkti: " + attistibasPunkti;
    }
}