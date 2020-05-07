package Modele;

import java.io.Serializable;
import java.util.ArrayList;

public class CaseVillage extends Case implements Serializable {
    private CaseChemin cheminNord;
    private CaseChemin cheminNordEst;
    private CaseChemin cheminEst;
    private CaseChemin cheminSudEst;
    private CaseChemin cheminSud;
    private CaseChemin cheminSudOuest;
    private CaseChemin cheminOuest;
    private CaseChemin cheminNordOuest;

    private CaseChemin riviereNord;
    private CaseChemin riviereNordEst;
    private CaseChemin riviereEst;
    private CaseChemin riviereSudEst;
    private CaseChemin riviereSud;
    private CaseChemin riviereSudOuest;
    private CaseChemin riviereOuest;
    private CaseChemin riviereNordOuest;

    private ArrayList<MarqueurVillage> marqueurs;
    private Pion transport=null;

    public CaseVillage(int valx, int valy){
        super(valx,valy);
        marqueurs=new ArrayList<>();
    }

    public void setChemin(CaseChemin c,String pos,boolean riviere){
        if(!riviere) {
            if (pos.equals("Nord")) {
                cheminNord = c;
            } else if (pos.equals("NordEst")) {
                cheminNordEst = c;
            } else if (pos.equals("Est")) {
                cheminEst = c;
            } else if (pos.equals("SudEst")) {
                cheminSudEst = c;
            } else if (pos.equals("Sud")) {
                cheminSud = c;
            } else if (pos.equals("SudOuest")) {
                cheminSudOuest = c;
            } else if (pos.equals("Ouest")) {
                cheminOuest = c;
            } else {
                cheminNordOuest = c;
            }
        }else{
            if (pos.equals("Nord")) {
                riviereNord = c;
            } else if (pos.equals("NordEst")) {
                riviereNordEst = c;
            } else if (pos.equals("Est")) {
                riviereEst = c;
            } else if (pos.equals("SudEst")) {
                riviereSudEst = c;
            } else if (pos.equals("Sud")) {
                riviereSud = c;
            } else if (pos.equals("SudOuest")) {
                riviereSudOuest = c;
            } else if (pos.equals("Ouest")) {
                riviereOuest = c;
            } else {
                riviereNordOuest = c;
            }
        }
    }

    public CaseChemin getChemin(String pos){
        if(pos.equals("Nord")) {
            return cheminNord;
        }else if(pos.equals("NordEst")) {
            return cheminNordEst;
        }else if(pos.equals("Est")) {
            return cheminEst;
        }else if(pos.equals("SudEst")){
            return cheminSudEst;
        }else if(pos.equals("Sud")){
            return cheminSud;
        }else if(pos.equals("SudOuest")){
            return cheminSudOuest;
        }else if(pos.equals("Ouest")){
            return cheminOuest;
        }else if(pos.equals("NordOuest")){
            return cheminNordOuest;
        }else{
            System.out.println("Erreur dans la saisie de direction !");
            return null;
        }
    }

    public CaseChemin getChemin(int i){
        if(i==0) {
            return cheminNord;
        }else if(i==1) {
            return cheminNordEst;
        }else if(i==2) {
            return cheminEst;
        }else if(i==3){
            return cheminSudEst;
        }else if(i==4){
            return cheminSud;
        }else if(i==5){
            return cheminSudOuest;
        }else if(i==6){
            return cheminOuest;
        }else if(i==7){
            return cheminNordOuest;
        }else if(i==8){
            return riviereNord;
        }else if(i==9){
            return riviereNordEst;
        }else if(i==10){
            return riviereEst;
        }else if(i==11){
            return riviereSudEst;
        }else if(i==12){
            return riviereSud;
        }else if(i==13){
            return riviereSudOuest;
        }else if(i==14){
            return riviereOuest;
        }else if(i==15){
            return riviereNordOuest;
        } else{
            //System.out.println("Erreur dans la saisie de direction !");
            return null;
        }
    }

    public int getX(){
        return super.x;
    }

    public int getY(){
        return super.y;
    }

    public CaseChemin getRiviereNord() {
        return riviereNord;
    }

    public CaseChemin getRiviereNordEst() {
        return riviereNordEst;
    }

    public CaseChemin getRiviereEst() {
        return riviereEst;
    }

    public CaseChemin getRiviereSudEst() {
        return riviereSudEst;
    }

    public CaseChemin getRiviereSud() {
        return riviereSud;
    }

    public CaseChemin getRiviereSudOuest() {
        return riviereSudOuest;
    }

    public CaseChemin getRiviereOuest() {
        return riviereOuest;
    }

    public CaseChemin getRiviereNordOuest() {
        return riviereNordOuest;
    }

    public ArrayList<MarqueurVillage> getMarqueurs() {
        return marqueurs;
    }

    public CaseChemin getCheminNord() {
        return cheminNord;
    }

    public void setCheminNord(CaseChemin cheminNord) {
        this.cheminNord = cheminNord;
    }

    public CaseChemin getCheminNordEst() {
        return cheminNordEst;
    }

    public void setCheminNordEst(CaseChemin cheminNordEst) {
        this.cheminNordEst = cheminNordEst;
    }

    public CaseChemin getCheminEst() {
        return cheminEst;
    }

    public void setCheminEst(CaseChemin cheminEst) {
        this.cheminEst = cheminEst;
    }

    public CaseChemin getCheminSudEst() {
        return cheminSudEst;
    }

    public void setCheminSudEst(CaseChemin cheminSudEst) {
        this.cheminSudEst = cheminSudEst;
    }

    public CaseChemin getCheminSud() {
        return cheminSud;
    }

    public void setCheminSud(CaseChemin cheminSud) {
        this.cheminSud = cheminSud;
    }

    public CaseChemin getCheminSudOuest() {
        return cheminSudOuest;
    }

    public void setCheminSudOuest(CaseChemin cheminSudOuest) {
        this.cheminSudOuest = cheminSudOuest;
    }

    public CaseChemin getCheminOuest() {
        return cheminOuest;
    }

    public void setCheminOuest(CaseChemin cheminOuest) {
        this.cheminOuest = cheminOuest;
    }

    public CaseChemin getCheminNordOuest() {
        return cheminNordOuest;
    }

    public void setCheminNordOuest(CaseChemin cheminNordOuest) {
        this.cheminNordOuest = cheminNordOuest;
    }

    public void setMarqueurs(ArrayList<MarqueurVillage> marqueurs) {
        this.marqueurs = marqueurs;
    }

    public void addMarqueur(MarqueurVillage marqueur){
        marqueurs.add(marqueur);
    }

    public void removeMarqueur(String couleur){
        for(int i=0;i<marqueurs.size();i++){
            if(marqueurs.get(i).getValeur().equals(couleur)){
                marqueurs.remove(i);
            }
        }
    }

    public Pion getTransport() {
        return transport;
    }

    public void setTransport(Pion transport) {
        this.transport = transport;
    }

    public void clearTransport(){ }
}
