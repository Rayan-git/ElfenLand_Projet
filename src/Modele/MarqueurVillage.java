package Modele;

import java.io.Serializable;

public class MarqueurVillage implements Serializable {
    private String valeur;

    public MarqueurVillage(){
    }

    public String getValeur(){
        return valeur;
    }

    public void setValeur(String val){
        valeur=val;
    }


}
