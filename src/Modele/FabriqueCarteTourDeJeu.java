package Modele;

import java.io.Serializable;

public class FabriqueCarteTourDeJeu implements FabriqueCarte, Serializable {
    private static FabriqueCarteTourDeJeu instance;

    private FabriqueCarteTourDeJeu(){
    }

    public Carte creer(int x){
        CarteTourDeJeu ctj=new CarteTourDeJeu();
        ctj.setNumero(x);
        return ctj;
    }

    public static FabriqueCarteTourDeJeu getInstance(){
        if(instance==null){
            instance=new FabriqueCarteTourDeJeu();
        }
        return instance;
    }

    public static void setInstance(FabriqueCarteTourDeJeu instance) {
        FabriqueCarteTourDeJeu.instance = instance;
    }
}
