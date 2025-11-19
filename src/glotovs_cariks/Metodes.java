package glotovs_cariks;

import javax.swing.JOptionPane;

public class Metodes {
    
    public static String ievaditTekstu(String zinojums) {
        while (true) {
            String teksts = JOptionPane.showInputDialog(null, zinojums);
            
            if (teksts == null) return null;
            
            if (!teksts.trim().isEmpty()) {
                return teksts;
            }
            JOptionPane.showMessageDialog(null, "Kļūda! Lauks nedrīkst būt tukšs.");
        }
    }

    public static int ievaditSkaitliRobezas(String zinojums, int min, int max) {
        while (true) {
            try {
                String s = JOptionPane.showInputDialog(null, zinojums + "\n(No " + min + " līdz " + max + ")");
                if (s == null) return min; 
                
                int skaitlis = Integer.parseInt(s);
                
                if (skaitlis >= min && skaitlis <= max) {
                    return skaitlis;
                } else {
                    JOptionPane.showMessageDialog(null, "Lūdzu ievadiet skaitli robežās no " + min + " līdz " + max + "!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Kļūda! Ievadiet veselu skaitli.");
            }
        }
    }
    
    public static void info(String s) {
        JOptionPane.showMessageDialog(null, s);
    }
}