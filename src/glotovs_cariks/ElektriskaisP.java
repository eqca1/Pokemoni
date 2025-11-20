package glotovs_cariks;

import java.util.Random;

public class ElektriskaisP extends Pokemons {
    private int voltaza;
    private boolean uzlade;

    public ElektriskaisP(String vards, String treneris, int maxDziviba, int uzbrukumaSpeks, int aizsardziba, int voltaza) {
        super(vards, treneris, maxDziviba, uzbrukumaSpeks, aizsardziba);
        this.voltaza = voltaza;
        this.uzlade = false;
    }

    @Override
    public String getTipaNosaukums() {
        return "Elektriskais";
    }

    @Override
    public String uzbrukt(Pokemons pretinieks) {
        Random rand = new Random();
        int pamatBojajums = this.getUzbrukumaSpeks();
        
        int voltazuBonus = this.voltaza / 10;
        int izlasesBojajums = rand.nextInt(11); 
        
        int kopejaisBojajums = pamatBojajums + voltazuBonus + izlasesBojajums;
        
        if (this.uzlade) {
            kopejaisBojajums *= 2;
            this.uzlade = false;
            pretinieks.sanemtBojajumus(kopejaisBojajums);
            return this.getVards() + " izmanto ZIBENS SPĒRIENU! KRITISKAIS TRIECIENS! Bojājumi: " + kopejaisBojajums + " HP";
        }
        
        pretinieks.sanemtBojajumus(kopejaisBojajums);
        return this.getVards() + " izmanto elektrisko triecienu! Bojājumi: " + kopejaisBojajums + " HP";
    }

    public String uzladet() {
        this.uzlade = true;
        this.voltaza += 20;
        
        if (this.voltaza > 250) {
            this.voltaza = 250;
        }
        
        return this.getVards() + " uzlādējas! Nākamais uzbrukums būs DIVREIZ spēcīgāks! Voltāža: " + voltaza + "V";
    }

    public String parslegtVoltazu() {
        Random rand = new Random();
        int jaunaVoltaza = rand.nextInt(151) + 100;
        
        this.voltaza = jaunaVoltaza;
        return this.getVards() + " pārslēdz voltāžu uz " + voltaza + "V";
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Voltāža: %dV", voltaza);
    }
}