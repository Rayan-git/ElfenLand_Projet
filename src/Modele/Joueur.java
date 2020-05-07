package Modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Joueur implements Serializable {
    private IA ordinateur;
    private boolean ia;
    public static int numero;
    private int id;
    private Partie partie;
    private Pion pionJoueur;
    private CarteResume carteResume;
    private ArrayList<CarteVoyage> mainCarte;
    private ArrayList<Pion> mainPion;
    private ArrayList<MarqueurVillage> marqueurs;
    private ArrayList<Pion> mainPionVisible;
    private PionObstacle obstacle;
    private Plateau plateau;

    public Joueur(Pion pj, CarteResume res,Partie partie,Plateau plat,boolean ia){
        obstacle=null;
        carteResume=res;
        pionJoueur=pj;
        id=numero++;
        mainCarte=new ArrayList<>();
        mainPion=new ArrayList<>();
        marqueurs=new ArrayList<>();
        mainPionVisible=new ArrayList<>();
        this.partie=partie;
        plateau=plat;
        this.ia=ia;
        ordinateur = new IA(this);
    }

    public int getNbCarte(String type){
        int cpt=0;
        for(int i=0;i<mainCarte.size();i++){
            if(mainCarte.get(i).getTypeTransport().equals(type)){
                cpt++;
            }
        }
        return cpt;
    }

    public void piocherCarte(CarteVoyage cv){
        mainCarte.add(cv);
    }

    public void addObstacle(PionObstacle obs){
        obstacle=obs;
    }

    public boolean piocherPionPremier(Pion pmt){
        if(mainPionVisible.size()+mainPion.size()>=5){
            System.out.println("Main pleine !");
            return false;
        }else {
            mainPion.add(pmt);
            return true;
        }
    }

    public void piocherPionDeuxieme(int choix){
        ArrayList<Pion> visible=partie.getMoyenDeTransportVisible();
        ArrayList<Pion> cache=partie.getMoyenDeTransport();
        if(mainPionVisible.size()+mainPion.size()>=5){
            System.out.println("Main pleine !");
        }else {
            if(choix > 0){
                mainPionVisible.add(visible.remove(choix));
                visible.add(cache.remove(0));
                partie.setMoyenDeTransportVisible(visible);
                partie.setMoyenDeTransport(cache);
            }else{
                mainPionVisible.add(cache.remove(0));
                partie.setMoyenDeTransport(cache);
            }
        }
    }

    public void deplacerPion(String direction){
        partie.deplacerPionJoueur(this,direction);
    }

    public boolean poserPion(String pioche,int pion,int x,int y){
        boolean b=false;
        if(pioche.equals("Visible")){
            b=partie.placerPion(this,mainPionVisible.get(pion),x,y,pioche);
        }else{
            b=partie.placerPion(this,mainPion.get(pion),x,y,pioche);
        }
        return b;
    }

    public void setMainPion(ArrayList<Pion> m){
        mainPion=m;
    }

    public void setMainPionVisible(ArrayList<Pion> m){
        mainPionVisible=m;
    }

    public void selectionnerChemin(){
        //Besoin de la vue
    }

    public void ramasserMarqueur(){
        MarqueurVillage[][][] marq=partie.getPlateau().getMarqueur();
        int x=pionJoueur.getX(),y=pionJoueur.getY(),couleur=((PionJoueur)pionJoueur).getIdCouleur();
        if(marq[x][y][couleur]!=null){
            marqueurs.add(marq[x][y][couleur]);
            marq[x][y][couleur]=null;
            partie.plateau.setMarqueurs(marq);
            CaseVillage ca=(CaseVillage)partie.plateau.getCases()[pionJoueur.getX()][pionJoueur.getY()];
            ca.removeMarqueur(pionJoueur.getValeur());
        }
    }

    public boolean finirTour(){
        Scanner sc=new Scanner(System.in);
        String finir;
        boolean b;
        do{
            System.out.print("Joueur "+pionJoueur.getValeur()+" souhaitez-vous terminer votre tour ? (Oui/Non) : ");
            finir=sc.nextLine();
            if(!finir.equals("Oui") && !finir.equals("Non")){
                System.out.println("Données erronées");
            }
        }while(!finir.equals("Oui") && !finir.equals("Non"));
        if(finir.equals("Oui")){
            b=true;
        }else{
            b=false;
        }
        return b;
    }

    public boolean jouerImpossible(){
        boolean fini=true;
        CarteResume res=carteResume;
        Pion[][][] pions=plateau.getPions();
        int cochon=0,dragon=0,machineElfe=0,machineTroll=0,nuage=0,unicorne=0,radeau=0;
        for(int i=0;i<mainCarte.size();i++){
            if(mainCarte.get(i).getTypeTransport().equals("Cochon géant")){
                cochon++;
            }else if(mainCarte.get(i).getTypeTransport().equals("Dragon")){
                dragon++;
            }else if(mainCarte.get(i).getTypeTransport().equals("Machine Elfe")){
                machineElfe++;
            }else if(mainCarte.get(i).getTypeTransport().equals("Machine Troll")){
                machineTroll++;
            }else if(mainCarte.get(i).getTypeTransport().equals("Nuage Magique")){
                nuage++;
            }else if(mainCarte.get(i).getTypeTransport().equals("Licorne")){
                unicorne++;
            }else if(mainCarte.get(i).getTypeTransport().equals("Radeau")){
                radeau++;
            }
        }
        //Faire les tests pour voir si on a assez de carte pour pouvoir jouer
        CaseVillage actuelle=(CaseVillage)partie.getPlateau().getCases()[pionJoueur.getX()][pionJoueur.getY()];
        ArrayList<CaseChemin> chemins=new ArrayList<>();
        int besoin;
        for(int i=0;i<=15;i++){
            if(actuelle.getChemin(i)!=null && actuelle.getChemin(i).getTransport() !=null){
                chemins.add(actuelle.getChemin(i));
            }
        }
        for(int i=0;i<chemins.size();i++) {
            besoin=carteResume.getBesoin(chemins.get(i).getTerrain(), chemins.get(i).getTransport().getValeur());
            if(chemins.get(i).getTransport().equals("Cochon géant")){
                if(besoin <= cochon){
                    fini = false;
                }
            }else if(chemins.get(i).getTransport().equals("Dragon")){
                if(besoin <= dragon){
                    fini = false;
                }
            }else if(chemins.get(i).getTransport().equals("Machine Elfe")){
                if(besoin <= machineElfe){
                    fini = false;
                }
            }else if(chemins.get(i).getTransport().equals("Machine Troll")){
                if(besoin <= machineTroll){
                    fini = false;
                }
            }else if(chemins.get(i).getTransport().equals("Nuage Magique")){
                if(besoin <= nuage){
                    fini = false;
                }
            }else if(chemins.get(i).getTransport().equals("Licorne")){
                if(besoin <= unicorne){
                    fini = false;
                }
            }else if(chemins.get(i).getTransport().equals("Radeau")){
                if(besoin <= radeau){
                    fini = false;
                }
            }
        }
        return fini;
    }

    public int getId(){
        return id;
    }

    public static int getNumero() {
        return numero;
    }

    public PionObstacle utiliserObstacle(){
        PionObstacle po=obstacle;
        obstacle=null;
        return po;
    }

    public ArrayList<Pion> rendrePions(String type,int indice){
        ArrayList<Pion> rendu=new ArrayList<>();
        Pion garde;
        if(type.equals("Visible")){
            garde=mainPionVisible.remove(indice);
        }else{
            garde=mainPion.remove(indice);
        }
        for(int i=0;i<mainPion.size();i++){
                rendu.add(mainPion.remove(0));
        }
        for(int i=0;i<mainPionVisible.size();i++){
                rendu.add(mainPionVisible.remove(0));
        }
        if(type.equals("Visible")){
            mainPionVisible.add(garde);
        }else{
            mainPion.add(garde);
        }
        while(mainCarte.size()>4){
            partie.getCarteVoyage().add(mainCarte.remove(0));
        }
        return rendu;
    }

    public PionObstacle getObstacle(){
        return obstacle;
    }

    public Pion getPionJoueur() {
        return pionJoueur;
    }

    public ArrayList<Pion> getMainPionVisible() {
        return mainPionVisible;
    }

    public Carte getCarteResume() {
        return carteResume;
    }

    public ArrayList<CarteVoyage> getMainCarte() {
        return mainCarte;
    }

    public ArrayList<Pion> getMainPion() {
        return mainPion;
    }

    public ArrayList<MarqueurVillage> getMarqueurs() {
        return marqueurs;
    }

    public static void setNumero(int numero) {
        Joueur.numero = numero;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public void setPionJoueur(Pion pionJoueur) {
        this.pionJoueur = pionJoueur;
    }

    public void setCarteResume(CarteResume carteResume) {
        this.carteResume = carteResume;
    }

    public void setMainCarte(ArrayList<CarteVoyage> mainCarte) {
        this.mainCarte = mainCarte;
    }

    public void setMarqueurs(ArrayList<MarqueurVillage> marqueurs) {
        this.marqueurs = marqueurs;
    }

    public void setObstacle(PionObstacle obstacle) {
        this.obstacle = obstacle;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }
    public IA getOrdinateur() {
        return ordinateur;
    }

    public void setOrdinateur(IA ordinateur) {
        this.ordinateur = ordinateur;
    }

    public boolean isIa() {
        return ia;
    }

    public void setIa(boolean ia) {
        this.ia = ia;
    }
}
