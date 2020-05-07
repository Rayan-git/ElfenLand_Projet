package Modele;

import java.io.Serializable;

public class FabriqueCarteVillage implements FabriqueCarte, Serializable {
    private static FabriqueCarteVillage instance;

    private FabriqueCarteVillage(){
    }

    public Carte creer(String str){
        CarteVillage cv=new CarteVillage();
        cv.setNom(str);
        return cv;
    }

    public static FabriqueCarteVillage getInstance(){
        if(instance==null){
            instance=new FabriqueCarteVillage();
        }
        return instance;
    }

    public static void setInstance(FabriqueCarteVillage instance) {
        FabriqueCarteVillage.instance = instance;
    }
}
