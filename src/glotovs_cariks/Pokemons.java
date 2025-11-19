package glotovs_cariks;

// ABSTRAKCIJA: Šī klase definē kopīgo struktūru visiem pokemoniem
public abstract class Pokemons {

    // INKAPSULĀCIJA: Visi atribūti ir private
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

    // ABSTRAKTA METODE: Katrs pokemona tips uzbrūk citādāk
    // POLIMORFISMS: Šī metode tiks pārrakstīta apakšklasēs
    public abstract String uzbrukt(Pokemons pretinieks);
    
    // Metode tipa nosaukuma iegūšanai (teksta formātā)
    public abstract String getTipaNosaukums();

    // Metode bojājumu saņemšanai (Aizsardzība darbojas kā procenti)
    public void sanemtBojajumus(int bojajumi) {
        int blokets = (bojajumi * this.aizsardziba) / 100;
        int realiBojajumi = bojajumi - blokets;

        if (realiBojajumi < 1) realiBojajumi = 1; 

        this.dziviba -= realiBojajumi;
        if (this.dziviba < 0) this.dziviba = 0;
    }

    // Metode dzīvības atjaunošanai
    public String dziedet() {
        if (this.dziviba >= this.maxDziviba) return vards + " ir pilnībā vesels!";
        
        int atjaunosana = 25 + (limenis * 5);
        this.dziviba += atjaunosana;
        
        if (this.dziviba > this.maxDziviba) this.dziviba = this.maxDziviba;
        
        return vards + " atguva spēkus. Dzīvība: " + dziviba + "/" + maxDziviba;
    }

    // Līmeņa paaugstināšana
    public void attistit() {
        this.limenis++;
        this.maxDziviba += 15;
        this.uzbrukumaSpeks += 5;
        this.aizsardziba += 2; 
        
        if (this.aizsardziba > 90) this.aizsardziba = 90;
        
        this.dziviba = this.maxDziviba; 
    }

    // Getteri
    public String getVards() { return vards; }
    public int getDziviba() { return dziviba; }
    public int getMaxDziviba() { return maxDziviba; }
    public int getUzbrukumaSpeks() { return uzbrukumaSpeks; }
    public String getTreneris() { return treneris; }
    public int getLimenis() { return limenis; }

    @Override
    public String toString() {
        return String.format("[%s] %s (Līm. %d) | HP: %d/%d | ATK: %d | DEF: %d%% | Treneris: %s", 
                getTipaNosaukums(), vards, limenis, dziviba, maxDziviba, uzbrukumaSpeks, aizsardziba, treneris);
    }
}