package Modele;

import java.io.Serializable;

public abstract class Pion implements Serializable {
    protected String valeur;
    protected int x;
    protected int y;

    public Pion(){
    }

    public String getValeur(){
        return valeur;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setValeur(String val){
        valeur=val;
    }

    public void setX(int v){
        x=v;
    }

    public void setY(int v){
        y=v;
    }
}
