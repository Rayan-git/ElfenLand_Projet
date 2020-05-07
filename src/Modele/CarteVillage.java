package Modele;

import java.io.Serializable;

public class CarteVillage extends Carte implements Serializable {
    private String nom;

    public CarteVillage(){
        super();
    }

    public void setNom(String str){
        nom=str;
    }

    public String getNom(){
        return nom;
    }


}
