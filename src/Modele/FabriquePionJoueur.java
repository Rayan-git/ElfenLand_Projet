package Modele;

import java.io.Serializable;

public class FabriquePionJoueur implements FabriquePion, Serializable {
    private static FabriquePionJoueur instance;

    private FabriquePionJoueur(){
    }

    @Override
    public Pion creer(String valeur) {
        PionJoueur pj=new PionJoueur();
        pj.setValeur(valeur);
        return pj;
    }

    public static FabriquePionJoueur getInstance(){
        if(instance==null){
            instance=new FabriquePionJoueur();
        }
        return instance;
    }

    public static void setInstance(FabriquePionJoueur instance) {
        FabriquePionJoueur.instance = instance;
    }
}
