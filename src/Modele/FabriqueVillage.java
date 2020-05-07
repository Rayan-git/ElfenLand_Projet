package Modele;

import java.io.Serializable;

public class FabriqueVillage implements FabriqueCase, Serializable {
    private static FabriqueVillage instance;

    private FabriqueVillage(){
    }

    public Case creer(String valeur,int valx,int valy) {
        CaseVillage caseV=new CaseVillage(valx,valy);
        caseV.setValeur(valeur);
        return caseV;
    }

    public static FabriqueVillage getInstance(){
        if(instance==null){
            instance=new FabriqueVillage();
        }
        return instance;
    }

    public static void setInstance(FabriqueVillage instance) {
        FabriqueVillage.instance = instance;
    }
}
