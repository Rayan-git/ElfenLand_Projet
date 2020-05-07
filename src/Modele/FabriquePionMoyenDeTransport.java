package Modele;

import java.io.Serializable;

public class FabriquePionMoyenDeTransport implements FabriquePion, Serializable {
    private static FabriquePionMoyenDeTransport instance;

    private FabriquePionMoyenDeTransport(){
    }

    @Override
    public Pion creer(String valeur) {
        PionMoyenDeTransport pm=new PionMoyenDeTransport();
        pm.setValeur(valeur);
        return pm;
    }

    public static FabriquePionMoyenDeTransport getInstance(){
        if(instance==null){
            instance=new FabriquePionMoyenDeTransport();
        }
        return instance;
    }

    public static void setInstance(FabriquePionMoyenDeTransport instance) {
        FabriquePionMoyenDeTransport.instance = instance;
    }
}
