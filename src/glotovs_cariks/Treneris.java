package glotovs_cariks;

import java.util.ArrayList;

public class Treneris {
    private String vards;
    private ArrayList<Pokemons> komanda; 

    public Treneris(String vards) {
        this.vards = vards;
        this.komanda = new ArrayList<>();
    }

    public void pievienotPokemonu(Pokemons p) {
        if (komanda.contains(p)) {
            throw new IllegalArgumentException("Pokemons jau atrodas komandā!");
        }
        if (komanda.size() >= 6) { 
            throw new IllegalStateException("Komanda pilna (maksimums 6 pokemoni)!");
        }
        komanda.add(p);
    }

    public ArrayList<Pokemons> getKomanda() {
        return komanda;
    }

    public String getVards() {
        return vards;
    }
    
    public int getKomandasIzmers() {
        return komanda.size();
    }
}