package Modele;

import java.io.Serializable;

public abstract class Carte implements Serializable {
    protected String type;

    public Carte(){
    }

    public void setType(String tp){
        type=tp;
    }

    public String getType(){
        return type;
    }
}
