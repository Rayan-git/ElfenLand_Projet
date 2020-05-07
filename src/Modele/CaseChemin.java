package Modele;

import java.io.Serializable;

public class CaseChemin extends Case implements Serializable {
    private String terrain;
    private String sensRiviere;

    private CaseVillage villageNord;
    private CaseVillage villageNordEst;
    private CaseVillage villageEst;
    private CaseVillage villageSudEst;
    private CaseVillage villageSud;
    private CaseVillage villageSudOuest;
    private CaseVillage villageOuest;
    private CaseVillage villageNordOuest;

    private Pion transport;

    public CaseChemin(int valx,int valy,String terr,CaseVillage n,CaseVillage ne,CaseVillage e,
                      CaseVillage se,CaseVillage s,CaseVillage so,CaseVillage o,CaseVillage no,String str){
        super(valx,valy);
        terrain=terr;
        villageNord=n;
        villageNordEst=ne;
        villageEst=e;
        villageSudEst=se;
        villageSud=s;
        villageSudOuest=so;
        villageOuest=o;
        villageNordOuest=no;
        sensRiviere=str;
        boolean riviere=(sensRiviere!=null);
        if (n != null) {
            n.setChemin(this, "Sud",riviere);
        }
        if (ne != null) {
            ne.setChemin(this, "SudOuest",riviere);
        }
        if (e != null) {
            e.setChemin(this, "Ouest",riviere);
        }
        if (se != null) {
            se.setChemin(this, "NordOuest",riviere);
        }
        if (s != null) {
            s.setChemin(this, "Nord",riviere);
        }
        if (so != null) {
            so.setChemin(this, "NordEst",riviere);
        }
        if (o != null) {
            o.setChemin(this, "Est",riviere);
        }
        if (no != null) {
            no.setChemin(this, "SudEst",riviere);
        }
    }

    public String getSensRiviere(){
        return sensRiviere;
    }

    public void setSensRiviere(String str){
        sensRiviere=str;
    }

    public CaseVillage getOppose(int x, int y){
        CaseVillage oppose=null;
        if(villageNord!=null){
            if(villageNord.getX()!=x || villageNord.getY()!=y)
                oppose=villageNord;
        }
        if(villageNordEst!=null){
            if(villageNordEst.getX()!=x || villageNordEst.getY()!=y)
                oppose=villageNordEst;
        }
        if(villageEst!=null){
            if(villageEst.getX()!=x || villageEst.getY()!=y)
                oppose=villageEst;
        }
        if(villageSudEst!=null){
            if(villageSudEst.getX()!=x || villageSudEst.getY()!=y)
                oppose=villageSudEst;
        }
        if(villageSud!=null){
            if(villageSud.getX()!=x || villageSud.getY()!=y)
                oppose=villageSud;
        }
        if(villageSudOuest!=null){
            if(villageSudOuest.getX()!=x || villageSudOuest.getY()!=y)
                oppose=villageSudOuest;
        }
        if(villageOuest!=null){
            if(villageOuest.getX()!=x || villageOuest.getY()!=y)
                oppose=villageOuest;
        }
        if(villageNordOuest!=null){
            if(villageNordOuest.getX()!=x || villageNordOuest.getY()!=y)
                oppose=villageNordOuest;
        }
        return oppose;
    }

    public String getTerrain() {
        return terrain;
    }

    public CaseVillage getVillageNord() {
        return villageNord;
    }

    public CaseVillage getVillageNordEst() {
        return villageNordEst;
    }

    public CaseVillage getVillageEst() {
        return villageEst;
    }

    public CaseVillage getVillageSudEst() {
        return villageSudEst;
    }

    public CaseVillage getVillageSud() {
        return villageSud;
    }

    public CaseVillage getVillageSudOuest() {
        return villageSudOuest;
    }

    public CaseVillage getVillageOuest() {
        return villageOuest;
    }

    public CaseVillage getVillageNordOuest() {
        return villageNordOuest;
    }

    public int getX(){
        return super.x;
    }

    public int getY(){
        return super.y;
    }

    public void setTransport(Pion tp){
        transport=tp;
    }

    public void clearTransport(){
        transport=null;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public void setVillageNord(CaseVillage villageNord) {
        this.villageNord = villageNord;
    }

    public void setVillageNordEst(CaseVillage villageNordEst) {
        this.villageNordEst = villageNordEst;
    }

    public void setVillageEst(CaseVillage villageEst) {
        this.villageEst = villageEst;
    }

    public void setVillageSudEst(CaseVillage villageSudEst) {
        this.villageSudEst = villageSudEst;
    }

    public void setVillageSud(CaseVillage villageSud) {
        this.villageSud = villageSud;
    }

    public void setVillageSudOuest(CaseVillage villageSudOuest) {
        this.villageSudOuest = villageSudOuest;
    }

    public void setVillageOuest(CaseVillage villageOuest) {
        this.villageOuest = villageOuest;
    }

    public void setVillageNordOuest(CaseVillage villageNordOuest) {
        this.villageNordOuest = villageNordOuest;
    }

    public Pion getTransport() {
        return transport;
    }
}
