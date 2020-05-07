package Modele;

import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public abstract class Case implements Serializable {
    protected String valeur;
    protected int x;
    protected int y;

    public Case(int valx,int valy){
        x=valx;
        y=valy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getValeur(){
        return  valeur;
    }

    public void setValeur(String str){
        valeur=str;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract void setTransport(Pion tp);
    public abstract void clearTransport();
}
