package glotovs_cariks;

import java.util.Random;

public class ParastaisP extends Pokemons {
    private int attistibasPunkti;

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
        int pamatBojajums = this.getUzbrukumaSpeks();
        int izlasesBojajums = rand.nextInt(6);
        int kopejaisBojajums = pamatBojajums + izlasesBojajums;
        
        pretinieks.sanemtBojajumus(kopejaisBojajums);
        
        this.attistibasPunkti++;
        
        return this.getVards() + " veic parasto triecienu! Bojājumi: " + kopejaisBojajums + " HP";
    }

    public String atspejosaisTrieciens() {
        Random rand = new Random();
        int iespeja = rand.nextInt(100);
        
        if (iespeja < 30) {
            this.attistibasPunkti += 2;
            return this.getVards() + " veic atspēkojošu triecienu! Tiek gūta papildu pieredze!";
        } else {
            return this.getVards() + " mēģināja veikt atspēkojošu triecienu, bet tas neizdevās.";
        }
    }
    
    @Override
    public void attistit() {
        super.attistit();
        this.attistibasPunkti = 0;
    }
}