package Modele;

import java.io.Serializable;
import java.util.ArrayList;

public class CarteResume extends Carte implements Serializable {
    private ArrayList<String> environnement;
    private ArrayList<String> typeTransport;
    private Integer[][] list;

    public CarteResume(){
        super();
    }

    public void setEnvironnement(ArrayList<String> env){
        environnement=env;
    }

    public ArrayList<String> getEnvironnement(){
        return environnement;
    }

    public void setTypeTransport(ArrayList<String> type){
        typeTransport=type;
    }

    public int getBesoin(String env,String type){
        int x=0,y=0;
        for(int i=0;i<environnement.size();i++){
            if(environnement.get(i).equals(env)){
                x=i;
            }
        }
        for(int i=0;i<typeTransport.size();i++){
            if(typeTransport.get(i).equals(type)){
                y=i;
            }
        }
        return list[y][x];
    }

    public ArrayList<String> getTypeTransport(){
        return typeTransport;
    }

    public void setList(Integer[][] l){
        list=l;
    }

    public Integer[][] getList(){
        return list;
    }

    public int getValeur(int x,int y){
        return list[x][y];
    }

    public void afficher(){
        if(environnement!=null && typeTransport!=null && list!=null) {
            String affichage;
            affichage = "| X | " + environnement.get(0) + " | " + environnement.get(1) + " | " + environnement.get(2) + " | " + environnement.get(3) + " | " + environnement.get(4) + " |\n";
            for (int i = 0; i < typeTransport.size()    ; i++) {
                affichage = affichage + "| " + typeTransport.get(i) + " | ";
                for (int j = 0; j < list[i].length; j++) {
                    affichage = affichage + list[i][j] + " | ";
                }
                affichage = affichage + "\n";
            }
            System.out.print(affichage);
        }
    }


}
