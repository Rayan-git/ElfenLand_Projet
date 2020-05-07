package Modele;

import java.io.Serializable;

public class FabriqueChemin implements FabriqueCase, Serializable {
    private static FabriqueChemin instance;

    private FabriqueChemin(){
    }

    public Case creer(int valx,int valy,String valeur,String terr,CaseVillage n,CaseVillage ne,CaseVillage e,
                      CaseVillage se,CaseVillage s,CaseVillage so,CaseVillage o,CaseVillage no,String str) {
        CaseChemin caseC=new CaseChemin(valx,valy,terr,n,ne,e,se, s,so,o,no,str);
        caseC.setValeur(valeur);
        return caseC;
    }

    public static FabriqueChemin getInstance(){
        if(instance==null){
            instance=new FabriqueChemin();
        }
        return instance;
    }

    public static void setInstance(FabriqueChemin instance) {
        FabriqueChemin.instance = instance;
    }
}
