package glotovs_cariks;

import java.util.Random;

// ELEKTRISKAIS POKEMONS - AUGSTA UZBRUKUMA TIPS
public class ElektriskaisP extends Pokemons {
    private int voltaza;
    private boolean uzlade; // Vai pokemons ir uzlādēts

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
        
        // Voltāža palielina bojājumu
        int voltazuBonus = this.voltaza / 10;
        int izlasesBojajums = rand.nextInt(11); 
        
        int kopejaisBojajums = pamatBojajums + voltazuBonus + izlasesBojajums;
        
        // Ja pokemons ir uzlādēts, bojājums dubultojas
        if (this.uzlade) {
            kopejaisBojajums *= 2;
            this.uzlade = false; 
        }
        
        // 15% iespēja uz kritisko triecienu
        if (rand.nextInt(100) < 15) {
            kopejaisBojajums = (int)(kopejaisBojajums * 1.5);
            pretinieks.sanemtBojajumus(kopejaisBojajums);
            return this.getVards() + " izmanto ZIBENS SPĒRIENU! KRITISKAIS TRIECIENS! Bojājumi: " + kopejaisBojajums + " HP";
        }
        
        pretinieks.sanemtBojajumus(kopejaisBojajums);
        return this.getVards() + " izmanto elektrisko triecienu! Bojājumi: " + kopejaisBojajums + " HP";
    }

    // SPECIĀLĀ SPĒJA - UZLĀDĒŠANA
    public String uzladet() {
        this.uzlade = true;
        this.voltaza += 20;
        
        if (this.voltaza > 250) {
            this.voltaza = 250; // Maksimālā voltāža
        }
        
        return this.getVards() + " uzlādējas! Nākamais uzbrukums būs DIVREIZ spēcīgāks! Voltāža: " + voltaza + "V";
    }

    // METODE VOLTĀŽAS PĀRSLĒGŠANAI
    public String parslegtVoltazu() {
        Random rand = new Random();
        int jaunaVoltaza = rand.nextInt(151) + 100;
        
        this.voltaza = jaunaVoltaza;
        return this.getVards() + " pārslēdz voltāžu uz " + voltaza + "V!";
    }

    @Override
    public void attistit() {
        super.attistit();
        this.voltaza += 25; // Katrs līmenis palielina voltāžu
        if (this.voltaza > 300) this.voltaza = 300;
    }

    @Override
    public String toString() {
        String uzladesStatuss = uzlade ? " (UZLĀDĒTS)" : "";
        return super.toString() + " | Voltāža: " + voltaza + "V" + uzladesStatuss;
    }

    // GETTERI
    public int getVoltaza() { return voltaza; }
    public boolean isUzlade() { return uzlade; }
}