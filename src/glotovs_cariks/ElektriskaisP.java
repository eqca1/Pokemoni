package glotovs_cariks;

import java.util.Random;

public class ElektriskaisP extends Pokemons {
    private int voltaza;

    public ElektriskaisP(String vards, String treneris, int maxDziviba, int uzbrukumaSpeks, int aizsardziba, int voltaza) {
        super(vards, treneris, maxDziviba, uzbrukumaSpeks, aizsardziba);
        this.voltaza = voltaza;
    }

    @Override
    public String getTipaNosaukums() {
        return "Elektriskais";
    }

    @Override
    public String uzbrukt(Pokemons pretinieks) {
        Random rand = new Random();
        int bojajumi = this.getUzbrukumaSpeks() + (voltaza / 8) + rand.nextInt(6);
        pretinieks.sanemtBojajumus(bojajumi);
        return this.getVards() + " izmanto zibens spērienu! Bojājumi: " + bojajumi;
    }
    
    public String uzladet() {
        this.voltaza += 15;
        if (this.voltaza > 200) this.voltaza = 200;
        return getVards() + " uzlādē enerģiju! Voltāža: " + voltaza;
    }

    @Override
    public String toString() {
        return super.toString() + " | Voltāža: " + voltaza + "V";
    }
}