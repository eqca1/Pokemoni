package glotovs_cariks;

import java.util.Random;

public class ParastaisP extends Pokemons {

    public ParastaisP(String vards, String treneris, int maxDziviba, int uzbrukumaSpeks, int aizsardziba) {
        super(vards, treneris, maxDziviba, uzbrukumaSpeks, aizsardziba);
    }

    @Override
    public String getTipaNosaukums() {
        return "Parastais";
    }

    @Override
    public String uzbrukt(Pokemons pretinieks) {
        Random rand = new Random();
        int bojajumi = this.getUzbrukumaSpeks() + rand.nextInt(5); 
        pretinieks.sanemtBojajumus(bojajumi);
        return this.getVards() + " veic triecienu. Bojājumi: " + bojajumi;
    }
}