package Modele;

import java.io.Serializable;

public class FabriqueCarteVoyage implements FabriqueCarte, Serializable {
    private static FabriqueCarteVoyage instance;

    private FabriqueCarteVoyage(){
    }

    public Carte creer(String type,int plaine,int foret,int desert,int montagne,int mer){
        CarteVoyage cv=new CarteVoyage();
        cv.setTerrains();
        cv.setTypeTransport(type);
        cv.setNombreDemande(plaine,foret,desert,montagne,mer);
        return cv;
    }

    public static FabriqueCarteVoyage getInstance(){
        if(instance==null){
            instance=new FabriqueCarteVoyage();
        }
        return instance;
    }

    public static void setInstance(FabriqueCarteVoyage instance) {
        FabriqueCarteVoyage.instance = instance;
    }
}
