package Modele;

import java.io.Serializable;
import java.util.ArrayList;

public class FabriqueMarqueurVillage implements FabriqueMarqueur, Serializable {
    private static FabriqueMarqueurVillage instance;

    private FabriqueMarqueurVillage(){
    }

    @Override
    public ArrayList<MarqueurVillage> creer(){
        ArrayList<String> couleurs=new ArrayList<>();
        couleurs.add("Gris");
        couleurs.add("Bleu");
        couleurs.add("Violet");
        couleurs.add("Vert");
        couleurs.add("Jaune");
        couleurs.add("Rouge");
        MarqueurVillage marqueur;
        ArrayList<MarqueurVillage> marqueurs=new ArrayList<>();
        for(int cpt=0;cpt<20;cpt++) {
            for(int c=0;c<couleurs.size();c++) {
                marqueur=new MarqueurVillage();
                marqueur.setValeur(couleurs.get(c));
                marqueurs.add(marqueur);
            }
        }
        return marqueurs;
    }

    public static FabriqueMarqueurVillage getInstance(){
        if(instance==null){
            instance=new FabriqueMarqueurVillage();
        }
        return instance;
    }

    public static void setInstance(FabriqueMarqueurVillage instance) {
        FabriqueMarqueurVillage.instance = instance;
    }
}
