package glotovs_cariks;


// ABSTRAKTAIS POKEMONU KLASES PAMATS
public abstract class Pokemons {

	private String vards;
    private String treneris;
    private int limenis;
    private int dziviba;
    private int maxDziviba;
    private int uzbrukumaSpeks;
    private int aizsardziba;

    public Pokemons(String vards, String treneris, int maxDziviba, int uzbrukumaSpeks, int aizsardziba) {
        this.vards = vards;
        this.treneris = treneris;
        this.maxDziviba = maxDziviba;
        this.dziviba = maxDziviba;
        this.uzbrukumaSpeks = uzbrukumaSpeks;
        this.aizsardziba = aizsardziba;
        this.limenis = 1;
    }

    // abstraktas metodes
    public abstract String uzbrukt(Pokemons pretinieks);
    public abstract String getTipaNosaukums();

    public void sanemtBojajumus(int bojajumi) {
        int aizsardzibasSamazinas = (bojajumi * this.aizsardziba) / 100;
        int realieBojajumi = bojajumi - aizsardzibasSamazinas;

        if (realieBojajumi < 1) realieBojajumi = 1;

        this.dziviba -= realieBojajumi;
        if (this.dziviba < 0) this.dziviba = 0;
    }

    public String dziedet() {
        if (this.dziviba >= this.maxDziviba) {
            return vards + " ir pilnībā vesels!";
        }
        
        int atjaunosana = 25 + (limenis * 5);
        this.dziviba += atjaunosana;
        
        if (this.dziviba > this.maxDziviba) {
            this.dziviba = this.maxDziviba;
        }
        
        return vards + " atguva spēkus. Dzīvība: " + dziviba + "/" + maxDziviba;
    }

    public void attistit() {
        this.limenis++;
        this.maxDziviba += 20;
        this.uzbrukumaSpeks += 8;
        this.aizsardziba += 3;
        
        if (this.aizsardziba > 80) {
            this.aizsardziba = 80;
        }
        
        this.dziviba = this.maxDziviba; // Pilnīga dziedēšana pēc attīstības
    }

    public String getVards() { return vards; }
    public int getDziviba() { return dziviba; }
    public int getMaxDziviba() { return maxDziviba; }
    public int getUzbrukumaSpeks() { return uzbrukumaSpeks; }
    public String getTreneris() { return treneris; }
    public int getLimenis() { return limenis; }
    public int getAizsardziba() { return aizsardziba; }

    // set metode dzīvībai (dziedēšanai)
    public void setDziviba(int jaunaDziviba) {
        this.dziviba = jaunaDziviba;
        if (this.dziviba > this.maxDziviba) {
            this.dziviba = this.maxDziviba;
        }
        if (this.dziviba < 0) {
            this.dziviba = 0;
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Līmenis %d) | HP: %d/%d | ATK: %d | DEF: %d%% | Treneris: %s", 
                getTipaNosaukums(), vards, limenis, dziviba, maxDziviba, uzbrukumaSpeks, aizsardziba, treneris);
    }
}
