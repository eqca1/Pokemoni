package glotovs_cariks;

import java.util.Random;

public class UdensP extends Pokemons {
    private boolean zemUdens = false; 

    public UdensP(String vards, String treneris, int maxDziviba, int uzbrukumaSpeks, int aizsardziba) {
        super(vards, treneris, maxDziviba, uzbrukumaSpeks, aizsardziba);
    }

    @Override
    public String getTipaNosaukums() {
        return "Ūdens";
    }

    @Override
    public String uzbrukt(Pokemons pretinieks) {
        Random rand = new Random();
        int bojajumi = this.getUzbrukumaSpeks() + rand.nextInt(5);
        
        if (zemUdens) {
            bojajumi += 12; 
            zemUdens = false; 
            pretinieks.sanemtBojajumus(bojajumi);
            return getVards() + " iznirst un negaidīti uzbrūk! Kritiski bojājumi: " + bojajumi;
        } else {
            pretinieks.sanemtBojajumus(bojajumi);
            return getVards() + " izmanto ūdens strūklu. Bojājumi: " + bojajumi;
        }
    }
    
    public String nirt() {
        this.zemUdens = true;
        return getVards() + " ienirst ūdenī, gatavojoties uzbrukumam!";
    }
    
    @Override
    public String toString() {
        String statuss = zemUdens ? " (Zem ūdens)" : "";
        return super.toString() + statuss;
    }
}