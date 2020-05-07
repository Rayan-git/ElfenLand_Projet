package Modele;

import java.io.Serializable;
import java.util.ArrayList;

public class CarteVoyage extends Carte implements Serializable {
    private ArrayList<String> terrains;
    private ArrayList<Integer> nombreDemande;
    private String typeTransport;

    public CarteVoyage(){
    }

    public void setTerrains(){
        terrains=new ArrayList<>();
        terrains.add("Plaine");
        terrains.add("Forêt");
        terrains.add("Désert");
        terrains.add("Montagne");
        terrains.add("Mer");
    }

    public ArrayList<String> getTerrains(){
        return terrains;
    }

    public void setNombreDemande(int pl,int fo,int des,int mont,int mer){
        nombreDemande=new ArrayList<>();
        nombreDemande.add(pl);
        nombreDemande.add(fo);
        nombreDemande.add(des);
        nombreDemande.add(mont);
        nombreDemande.add(mer);
    }

    public ArrayList<Integer> getNombreDemande(){
        return nombreDemande;
    }

    public void setTypeTransport(String str){
        typeTransport=str;
    }

    public String getTypeTransport(){
        return typeTransport;
    }

    public void setTerrains(ArrayList<String> terrains) {
        this.terrains = terrains;
    }

    public void setNombreDemande(ArrayList<Integer> nombreDemande) {
        this.nombreDemande = nombreDemande;
    }
}
