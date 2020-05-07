package Modele;

import java.io.Serializable;

public class CarteTourDeJeu extends Carte implements Serializable {
    private int numero;

    public CarteTourDeJeu(){
        super();
    }

    public void setNumero(int x){
        numero=x;
    }

    public int getNumero(){
        return numero;
    }


}
