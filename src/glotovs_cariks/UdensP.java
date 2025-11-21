package glotovs_cariks;

import java.util.Random;

public class UdensP extends Pokemons {
    private boolean zemUdens;
    private int izvairisanasIespeja;

    public UdensP(String vards, String treneris, int maxDziviba, int uzbrukumaSpeks, int aizsardziba) {
        super(vards, treneris, maxDziviba, uzbrukumaSpeks, aizsardziba);
        this.zemUdens = false;
        this.izvairisanasIespeja = 20; 
    }

    @Override
    public String getTipaNosaukums() {
        return "Ūdens";
    }

    @Override
    public String uzbrukt(Pokemons pretinieks) {
        Random rand = new Random();
        int pamatBojajums = this.getUzbrukumaSpeks();
        int izlasesBojajums = rand.nextInt(8);
        
        int kopejaisBojajums = pamatBojajums + izlasesBojajums;
        
        if (this.zemUdens) {
            kopejaisBojajums += 15;
            this.zemUdens = false;
            pretinieks.sanemtBojajumus(kopejaisBojajums);
            return this.getVards() + " IZNIRST UN NEGAIDĪTI UZBRŪK! Kritiski bojājumi: " + kopejaisBojajums + " HP";
        }
        
        pretinieks.sanemtBojajumus(kopejaisBojajums);
        return this.getVards() + " izmanto ŪDENS STRŪKLA uzbrukumu! Bojājumi: " + kopejaisBojajums + " HP";
    }

    public void sanemtBojajumus(int bojajumi) {
        Random rand = new Random();
        int izvairisanasIespeja = this.izvairisanasIespeja;
        
        if (this.zemUdens) {
            izvairisanasIespeja += 15; // zem ūdens bonus
        }
        
        if (rand.nextInt(100) < izvairisanasIespeja) {
            return; 
        }
        
        if (this.zemUdens) {
            bojajumi = (int) (bojajumi * 0.75);
        }
        
        super.sanemtBojajumus(bojajumi);
    }
    
    public String nirtZemUdens() {
        this.zemUdens = true;
        return this.getVards() + " ienirst zem ūdens! Nākamais uzbrukums būs spēcīgāks!";
    }

    public String udensAizsardziba() {
        return this.getVards() + " izveido ūdens aizsardzības lauku! Aizsardzība palielināta!";
    }

    @Override
    public String dziedet() {
        if (this.getDziviba() >= this.getMaxDziviba()) {
            return getVards() + " ir pilnībā vesels!";
        }
        
        int atjaunosana = 35 + (getLimenis() * 8);
        this.setDziviba(this.getDziviba() + atjaunosana);
        
        if (this.getDziviba() > this.getMaxDziviba()) {
            this.setDziviba(this.getMaxDziviba());
        }
        
        return getVards() + " izmanto ūdens dziedēšanas spējas! Dzīvība: " + getDziviba() + "/" + getMaxDziviba();
    }

    @Override
    public void attistit() {
        super.attistit();
        this.izvairisanasIespeja += 5;
        if (this.izvairisanasIespeja > 45) {
            this.izvairisanasIespeja = 45;
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Izvairīšanās: %d%%", izvairisanasIespeja);
    }
}