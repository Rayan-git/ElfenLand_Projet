package Modele;

import java.io.Serializable;

public class FabriquePionObstacle implements FabriquePion, Serializable {
    private static FabriquePionObstacle instance;

    private FabriquePionObstacle(){
    }

    @Override
    public Pion creer(String valeur) {
        PionObstacle po=new PionObstacle();
        po.setValeur(valeur);
        return po;
    }

    public static FabriquePionObstacle getInstance(){
        if(instance==null){
            instance=new FabriquePionObstacle();
        }
        return instance;
    }

    public static void setInstance(FabriquePionObstacle instance) {
        FabriquePionObstacle.instance = instance;
    }
}
