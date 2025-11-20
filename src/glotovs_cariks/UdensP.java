package glotovs_cariks;

import java.util.Random;

// ŪDENS POKEMONS - AIZSARDZĪBAS TIPS
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
        int izlasesBojajums = rand.nextInt(8); // 0-7 papildus bojājums
        
        int kopejaisBojajums = pamatBojajums + izlasesBojajums;
        
        // Ja pokemons ir zem ūdens, bojājums palielinās
        if (this.zemUdens) {
            kopejaisBojajums += 15;
            this.zemUdens = false; // Iznirst pēc uzbrukuma
            pretinieks.sanemtBojajumus(kopejaisBojajums);
            return this.getVards() + " IZNIRST UN NEGAIDĪTI UZBRŪK! Kritiski bojājumi: " + kopejaisBojajums + " HP";
        }
        
        pretinieks.sanemtBojajumus(kopejaisBojajums);
        return this.getVards() + " izmanto ŪDENS STRŪKLA uzbrukumu! Bojājumi: " + kopejaisBojajums + " HP";
    }

    @Override
    public void sanemtBojajumus(int bojajumi) {
        Random rand = new Random();
        
        // Pārbauda izvairīšanos
        if (rand.nextInt(100) < this.izvairisanasIespeja) {
            // Izvairījās no bojājuma
            return;
        }
        
        // Ūdens pokemoni saņem mazāk bojājumu no elektriskajiem uzbrukumiem
        int realieBojajumi = bojajumi;
        
        super.sanemtBojajumus(realieBojajumi);
    }

    // SPECIĀLĀ SPĒJA - NIRŠANA
    public String nirt() {
        this.zemUdens = true;
        this.izvairisanasIespeja += 15; // Niršana palielina izvairīšanās iespēju
        
        if (this.izvairisanasIespeja > 50) {
            this.izvairisanasIespeja = 50; // Maksimālā izvairīšanās
        }
        
        return this.getVards() + " ienirst ūdenī! Izvairīšanās iespēja palielināta līdz " + izvairisanasIespeja + "%";
    }

    // SPECIĀLĀ SPĒJA - ŪDENS AIZSARDZĪBA
    public String udensAizsardziba() {
        // Pagaidām palielina aizsardzību
        return this.getVards() + " izveido ūdens aizsardzības lauku! Aizsardzība palielināta!";
    }

    @Override
    public String dziedet() {
        // Ūdens pokemoni dziedē efektīvāk
        if (this.getDziviba() >= this.getMaxDziviba()) {
            return getVards() + " ir pilnībā vesels!";
        }
        
        int atjaunosana = 35 + (getLimenis() * 8); // Vairāk dziedēšanas nekā parastajiem
        this.setDziviba(this.getDziviba() + atjaunosana);
        
        if (this.getDziviba() > this.getMaxDziviba()) {
            this.setDziviba(this.getMaxDziviba());
        }
        
        return getVards() + " izmanto ūdens dziedēšanas spējas! Dzīvība: " + getDziviba() + "/" + getMaxDziviba();
    }

    @Override
    public void attistit() {
        super.attistit();
        this.izvairisanasIespeja += 5; // Katrs līmenis palielina izvairīšanās iespēju
        if (this.izvairisanasIespeja > 60) this.izvairisanasIespeja = 60;
    }

    @Override
    public String toString() {
        String niršanasStatuss = zemUdens ? " (ZEM ŪDENS)" : "";
        return super.toString() + " | Izvairīšanās: " + izvairisanasIespeja + "%" + niršanasStatuss;
    }

    // GETTERI
    public boolean isZemUdens() { return zemUdens; }
    public int getIzvairisanasIespeja() { return izvairisanasIespeja; }
}