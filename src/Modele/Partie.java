package Modele;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Partie implements Serializable {
    protected int nbJoueur;
    protected int nbIA;

    protected FabriqueMarqueurVillage fabriqueMarqueurVillage;
    protected FabriqueCarteTourDeJeu fabriqueCarteTourDeJeu;
    protected FabriqueCarteVoyage fabriqueCarteVoyage;
    protected FabriquePionMoyenDeTransport fabriquePionMoyenDeTransport;
    protected FabriquePionObstacle fabriquePionObstacle;
    protected FabriquePionJoueur fabriquePionJoueur;
    protected FabriqueCarteResume fabriqueCarteResume;

    protected ArrayList<MarqueurVillage> marqueurs;
    protected Plateau plateau;
    protected ArrayList<Pion> pionJoueurs;
    protected ArrayList<Pion> pionObstacle;
    protected ArrayList<Pion> moyenDeTransport;
    protected ArrayList<Pion> moyenDeTransportVisible;
    protected ArrayList<Carte> carteTourDeJeu;
    protected ArrayList<CarteResume> carteResume;
    protected CarteResume resume;
    protected ArrayList<Carte> carteVoyage;
    protected ArrayList<Carte> defausseVoyage;

    protected ArrayList<Joueur> joueurs;

    public Partie(int nbJ,int nbia){
        nbJoueur=nbJ;
        nbIA=nbia;
        plateau=new Plateau();
        //Initialisation des fabriques
        fabriqueMarqueurVillage=fabriqueMarqueurVillage.getInstance();
        fabriqueCarteTourDeJeu=fabriqueCarteTourDeJeu.getInstance();
        fabriqueCarteVoyage=fabriqueCarteVoyage.getInstance();
        fabriquePionJoueur=fabriquePionJoueur.getInstance();
        fabriquePionMoyenDeTransport=fabriquePionMoyenDeTransport.getInstance();
        fabriquePionObstacle=fabriquePionObstacle.getInstance();
        fabriqueCarteResume=fabriqueCarteResume.getInstance();
        defausseVoyage=new ArrayList<>();

        //Génération des marqueurs
        marqueurs=fabriqueMarqueurVillage.creer();
        MarqueurVillage marqueur;
        CaseVillage ca;
        for(int i=0;i<46;i++){
            for(int j=0;j<33;j++){
                if (plateau.getCases()[i][j] instanceof CaseVillage) {
                    ca=(CaseVillage) plateau.getCases()[i][j];
                    if(!ca.getValeur().equals("Elvenhold")) {
                        for (int k = 0; k < 6; k++) {
                            marqueur=marqueurs.remove(0);
                            plateau.addMarqueur(i, j, marqueur);
                            ca.addMarqueur(marqueur);
                        }
                    }
                }
            }
        }

        //Création des pions de joueur
        pionJoueurs=new ArrayList<>();
        pionJoueurs.add(0,fabriquePionJoueur.creer("Bleu"));
        pionJoueurs.add(1,fabriquePionJoueur.creer("Jaune"));
        pionJoueurs.add(2,fabriquePionJoueur.creer("Vert"));
        pionJoueurs.add(3,fabriquePionJoueur.creer("Rouge"));
        pionJoueurs.add(4,fabriquePionJoueur.creer("Gris"));
        pionJoueurs.add(5,fabriquePionJoueur.creer("Violet"));

        //Génération des pions obstacle
        //Génération des cartes de tour de jeu
        pionObstacle=new ArrayList<>();
        carteTourDeJeu=new ArrayList<>();
        carteResume=new ArrayList<>();
        for(int i=0;i<6;i++){
            pionObstacle.add(i,fabriquePionObstacle.creer("Tronc d'arbre"));
            carteTourDeJeu.add(i,fabriqueCarteTourDeJeu.creer(i+1));
            carteResume.add(i,fabriqueCarteResume.creer());
        }
        resume=fabriqueCarteResume.creer();
        //Génération des pions de moyen de transport
        moyenDeTransport=new ArrayList<>();
        moyenDeTransportVisible=new ArrayList<>(5);
        String[] noms={"Cochon Géant","Machine Elfe","Nuage Magique","Licorne","Machine Troll","Dragon"};
        for(int j=0;j<6;j++){
            for(int k=0;k<8;k++){
                moyenDeTransport.add(j+k,fabriquePionMoyenDeTransport.creer(noms[j]));
            }
        }

        //Génération des cartes de voyage
        resume=fabriqueCarteResume.creer();
        carteVoyage=new ArrayList<>();
        CarteResume carteRes=carteResume.get(0);
        for(int i=0;i<7;i++){
            if(i==6){
                for(int k=0;k<12;k++){
                    carteVoyage.add(i*10+k,fabriqueCarteVoyage.creer("Radeau",carteRes.getValeur(i,0),carteRes.getValeur(i,1),
                            carteRes.getValeur(i,2), carteRes.getValeur(i,3),carteRes.getValeur(i,4)));
                }
            }else {
                for (int j = 0; j < 10; j++) {
                    carteVoyage.add(i*10+j,fabriqueCarteVoyage.creer(noms[i],carteRes.getValeur(i,0),carteRes.getValeur(i,1),
                            carteRes.getValeur(i,2), carteRes.getValeur(i,3),carteRes.getValeur(i,4)));
                }
            }
        }

        //Pour finir, génération des joueurs
        joueurs=new ArrayList<>();
        for(int i=0;i<nbJoueur-nbIA;i++){
            Joueur nv=new Joueur(pionJoueurs.remove(0),carteResume.remove(0),this,plateau,false);
            joueurs.add(nv);
            plateau.setPions(35,18,((PionJoueur)nv.getPionJoueur()),null,null);
        }
        for(int i=0;i<nbIA;i++){
            Joueur nv=new Joueur(pionJoueurs.remove(0),carteResume.remove(0),this,plateau,true);
            joueurs.add(nv);
            plateau.setPions(35,18,((PionJoueur)nv.getPionJoueur()),null,null);
        }
        System.out.println("Génération de la partie effectuée avec succés !");
    }

    public void melanger(){
        //Utilisation de la méthode optimisée de mélange vue en algorithmie avancée
        Melange<Pion> melangePionTransport=new Melange<>(moyenDeTransport);
        Melange<Carte> melangeCarte=new Melange<>(carteVoyage);
        melangePionTransport.riffleShuffle(200);
        melangeCarte.riffleShuffle(200);
        moyenDeTransport=melangePionTransport.getValues();
        carteVoyage=melangeCarte.getValues();
    }

    public void distribuerCarte(){
        //Distribue dans l'ordre de la pioche les différentes cartes aux joueurs
        for(int i=0;i<joueurs.size();i++){
            for(int j=0;j<8;j++) {
                if(joueurs.get(i).getMainCarte().size()<8) {
                    joueurs.get(i).piocherCarte((CarteVoyage) carteVoyage.remove(0));
                }
            }
        }
    }

    public void distribuerObstacles(){
        for(int i=0;i<joueurs.size();i++){
            joueurs.get(i).addObstacle((PionObstacle)pionObstacle.remove(0));
        }
    }

    public void distribuerPionPremier(){
        //Distribue dans l'ordre de la pioche les différents pions aux joueurs
        for(int i=0;i<joueurs.size();i++){
            if(joueurs.get(i).piocherPionPremier(moyenDeTransport.get(0))){
                moyenDeTransport.remove(0);
            }
        }
    }

    public void piocherPionDeuxieme(){
        for(int i=0;i<5;i++){
            moyenDeTransportVisible.add(moyenDeTransport.remove(0));
        }
        Scanner sc=new Scanner(System.in);
        int choix;
        while(!checkPionsOK()) {
            for (int i = 0; i < joueurs.size(); i++) {
                if (joueurs.get(i).getMainPion().size() + joueurs.get(i).getMainPionVisible().size() < 5) {
                    if(!joueurs.get(i).isIa()) {
                        do {
                            System.out.print("Joueur " + joueurs.get(i).getPionJoueur().getValeur() + " veuillez sélectionner un pion à piocher (entier de 0 à 5) : ");
                            choix = sc.nextInt(); //0 pour caché et le reste pour visible
                            sc.nextLine();
                            if (choix < 0 || choix > 5) {
                                System.out.println("Valeurs entrées erronées !");
                            }
                        } while (choix < 0 || choix > 5);
                    }else{
                        choix=joueurs.get(i).getOrdinateur().piocherPion();
                    }
                    joueurs.get(i).piocherPionDeuxieme(choix);
                } else {
                    System.out.println("Votre main de moyens de transport est pleine, vous ne piochez donc pas.");
                }
            }
        }
    }

    public boolean checkPionsOK(){
        boolean ok=true;
        for(int i=0;i<nbJoueur;i++){
            if(joueurs.get(i).getMainPionVisible().size()<3){
                ok=false;
            }
        }
        return ok;
    }

    public void deplacerPionJoueur(Joueur player,String direction){
        int besoin,possede;
        //Permettre de déplacer les pions de joueur et de transport en s'assurant que les cases sont libres ou utilisables
        PionJoueur pj=(PionJoueur)player.getPionJoueur();
        CaseVillage ca=(CaseVillage) plateau.getCases()[pj.getX()][pj.getY()];
        CaseChemin cc=ca.getChemin(direction);
        Pion moyenTP=plateau.getPions()[cc.getX()][cc.getY()][0];
        if(moyenTP==null){
            System.out.println("La chemin ne possède pas de moyen de transport !");
        }else if(cc==null){
            System.out.println("Il n'existe pas de chemin dans la direction que vous avez donné");
        }else if(cc.getTerrain().equals("Mer")){
            //Code pour gérer le cas de la mer à modifier pour pouvoir utiliser les rivieres egalement
            if(cc.getSensRiviere()==null){
                if(player.getNbCarte(moyenTP.getValeur())>=1) {
                    move(player,direction,1);
                }
            }else if(!cc.getSensRiviere().equals(direction) && player.getNbCarte(moyenTP.getValeur())>=2){
                move(player,direction,2);
            } else if(cc.getSensRiviere().equals(direction) && player.getNbCarte(moyenTP.getValeur())>=1){
                move(player,direction,1);
            } else{
                    System.out.println("Vous n'avez pas de carte radeau !");
            }
        } else{
            if(plateau.getPions()[pj.getX()][pj.getY()][0]!=null){
                besoin=resume.getBesoin(cc.getTerrain(),moyenTP.getValeur())+1;
            }else{
                besoin=resume.getBesoin(cc.getTerrain(),moyenTP.getValeur());
            }
            possede=player.getNbCarte(moyenTP.getValeur());
            if(besoin<=possede){
                move(player,direction,besoin);
            }else{
                System.out.println("Vous n'avez pas suffisamment de carte de voyage pour passer sur le chemin !");
            }
        }
    }

    public void move(Joueur j,String direction,int nb){
        PionJoueur pj=(PionJoueur)j.getPionJoueur();
        CaseVillage ca=(CaseVillage) plateau.getCases()[pj.getX()][pj.getY()];
        CaseChemin cc=ca.getChemin(direction);
        int xPrec=pj.getX();
        int yPrec=pj.getY();
        Pion moyenTP=plateau.getPions()[cc.getX()][cc.getY()][0];
        int cpt=0;
        for (int z = 0; z < j.getMainCarte().size(); z++) {
            if (j.getMainCarte().get(z).getTypeTransport().equals(moyenTP.getValeur()) && cpt<nb) {
                defausseVoyage.add(j.getMainCarte().remove(z));
                cpt++;
            }
        }
        pj.setX(cc.getOppose(xPrec,yPrec).getX());
        pj.setY(cc.getOppose(xPrec,yPrec).getY());
        plateau.setPions(pj.getX(),pj.getY(),pj,null,null);
        plateau.removePionJoueur(xPrec,yPrec,pj);
    }

    public boolean placerPion(Joueur j, Pion pion, int x, int y, String visibilite){
        //Permettra de placer un pion qui n'est pas sur le plateau en s'assurant que la case est libre ou utilisable
        boolean b=false;
        Case [][] cases=plateau.getCases();
        ArrayList<Pion> pionsV=j.getMainPionVisible();
        ArrayList<Pion> pionsC=j.getMainPion();
        if(cases[x][y]==null){
            System.out.println("La case est vide !");
        }else if(cases[x][y] instanceof CaseVillage){
            System.out.println("Vous ne pouvez pas poser de pion sur une ville !");
        }else{
            Pion[][][] pions=plateau.getPions();
            CaseChemin cc=(CaseChemin)cases[x][y];
            if(pion instanceof PionObstacle){
                if(pions[x][y][0]!=null && pions[x][y][1]==null && j.getObstacle()!=null) {
                    pion.setX(x);
                    pion.setY(y);
                    j.utiliserObstacle();
                    plateau.setPions(x,y,null,null,(PionObstacle)pion);
                    b=true;
                }else{
                    System.out.println("Impossible de placer un obstacle !");
                }
            }else{
                if(pions[x][y][0]!=null){
                    System.out.println("Il y a déjà un moyen de transport à cet emplacement !");
                }else{
                    if(verifTerrain((PionMoyenDeTransport)pion,cc)){
                        pion.setX(x);
                        pion.setY(y);
                        plateau.setPions(x,y,null,(PionMoyenDeTransport) pion,null);
                        b=true;
                    }else{
                        System.out.println("Le moyen de transport n'est pas adapté au terrain !");
                    }
                }
            }
        }
        return b;
    }

    public void swapPremier(){
        Joueur tmp=joueurs.remove(0);
        joueurs.add(tmp);
    }

    //Fonction vérifiant si le terrain peut accueillir un pion donné
    public boolean verifTerrain(PionMoyenDeTransport pion,CaseChemin ca){
        return(resume.getBesoin(ca.getTerrain(),pion.getValeur())!=0);
    }

    public ArrayList<Pion> getMoyenDeTransport(){
        return moyenDeTransport;
    }

    public ArrayList<Pion> getMoyenDeTransportVisible(){
        return moyenDeTransportVisible;
    }

    public void setMoyenDeTransport(ArrayList<Pion> list){
        moyenDeTransport=list;
    }

    public void setMoyenDeTransportVisible(ArrayList<Pion> list){
        moyenDeTransportVisible=list;
    }

    public void recupererPions(){
        Scanner sc=new Scanner(System.in);
        Pion[][][] pions=plateau.getPions();
        for(int i=0;i<46;i++){
            for(int j=0;j<33;j++){
                pions[i][j][1]=null;
                try {
                    plateau.getCases()[i][j].clearTransport();
                }catch(Exception e){}
                if(pions[i][j][0]!=null){
                    moyenDeTransport.add(pions[i][j][0]);
                    pions[i][j][0]=null;
                }
            }
        }
        plateau.setPions(pions);
        String typePion;
        int indice=-1;
        for(int i=0;i<nbJoueur;i++){
            if(!joueurs.get(i).isIa()) {
                do {
                    typePion = demandeTypePion(i);
                    if (typePion.equals("Visible") && joueurs.get(i).getMainPionVisible().size() > 0) {
                        indice = demandeIndicePionVisible(i);
                    } else if (typePion.equals("Caché") && joueurs.get(i).getMainPion().size() > 0) {
                        indice = demandeIndicePion(i);
                    } else {
                        System.out.println("Vous ne disposez pas de ce type de pion !");
                    }
                } while (indice == -1);
                moyenDeTransport.addAll(joueurs.get(i).rendrePions(typePion, indice));
            }else{
                joueurs.get(i).getOrdinateur().garderPion();
                moyenDeTransport.addAll(joueurs.get(i).rendrePions(joueurs.get(i).getOrdinateur().getTypeGarder(), joueurs.get(i).getOrdinateur().getIndiceGarder()));
            }
        }
        for(int i=0;i<moyenDeTransportVisible.size();i++){
            moyenDeTransport.add(moyenDeTransport.remove(0));
        }
    }

    public String demandeTypePion(int x){
        Scanner sc=new Scanner(System.in);
        String typePion;
        do{
            System.out.print("Joueur "+joueurs.get(x).getPionJoueur().getValeur()+" entrez le type de pion (Visible ou Caché) : ");
            typePion=sc.nextLine();
            if(!typePion.equals("Visible") && !typePion.equals("Caché") || joueurs.get(x).getMainPionVisible().size()==0 && !typePion.equals("Visible")){
                System.out.println("Valeurs entrées erronées !");
            }
        }while(!typePion.equals("Visible") && !typePion.equals("Caché") || joueurs.get(x).getMainPionVisible().size()==0 && !typePion.equals("Visible"));
        return typePion;
    }

    public int demandeIndicePionVisible(int x){
        Scanner sc=new Scanner(System.in);
        int indice;
        do {
            System.out.print("Joueur " + joueurs.get(x).getPionJoueur().getValeur() + " entrez l'indice du pion (0 à "+(joueurs.get(x).getMainPionVisible().size()-1)+") : ");
            indice = sc.nextInt();
            sc.nextLine();
            if (indice < 0 || indice > joueurs.get(x).getMainPionVisible().size()-1) {
                System.out.println("Valeurs entrées erronées !");
            }
        } while (indice < 0 || indice > joueurs.get(x).getMainPionVisible().size()-1);
        return indice;
    }

    public int demandeIndicePion(int x){
        Scanner sc=new Scanner(System.in);
        int indice;
        do {
            System.out.print("Joueur " + joueurs.get(x).getPionJoueur().getValeur() + " entrez l'indice du pion (0 à "+(joueurs.get(x).getMainPion().size()-1)+") : ");
            indice = sc.nextInt();
            sc.nextLine();
            if (indice < 0 || indice > joueurs.get(x).getMainPion().size()-1) {
                System.out.println("Valeurs entrées erronées !");
            }
        } while (indice < 0 || indice > joueurs.get(x).getMainPion().size()-1);
        return indice;
    }

    public int demandeIndice(int x,String nom,int min,int max){
        Scanner sc=new Scanner(System.in);
        int indice;
        do {
            System.out.print("Joueur " + joueurs.get(x).getPionJoueur().getValeur() + " entrez l'indice "+nom+" du pion à poser (0 à 45) : ");
            indice = sc.nextInt();
            sc.nextLine();
            if (indice < min || indice > max) {
                System.out.println("Valeurs entrées erronées !");
            }
        } while (indice < min || indice > max);
        return indice;
    }

    public String demandeDirection(int x){
        Scanner sc=new Scanner(System.in);
        String direction;
        CaseVillage village;
        do {
            do {
                System.out.print("Joueur " + joueurs.get(x).getPionJoueur().getValeur() + " entrez la direction vers laquelle vous souhaitez vous déplacer (points cardinaux) : ");
                direction = sc.nextLine();
                if (!direction.equals("Nord") && !direction.equals("NordEst") && !direction.equals("Est") && !direction.equals("SudEst") && !direction.equals("Sud")
                        && !direction.equals("SudOuest") && !direction.equals("Ouest") && !direction.equals("NordOuest")) {
                    System.out.println("Valeurs entrées erronées !");
                }
            } while (!direction.equals("Nord") && !direction.equals("NordEst") && !direction.equals("Est") && !direction.equals("SudEst") && !direction.equals("Sud")
                    && !direction.equals("SudOuest") && !direction.equals("Ouest") && !direction.equals("NordOuest"));
            village=(CaseVillage)(plateau.getCases()[joueurs.get(x).getPionJoueur().getX()][joueurs.get(x).getPionJoueur().getY()]);
            if(village.getChemin(direction)==null){
                System.out.println("Il n'existe pas de tel chemin pour ce village !");
            }
        }while(village.getChemin(direction)==null);
        return direction;
    }

    public Plateau getPlateau(){
        return plateau;
    }

    public void lancerModeNormal(){
        //Doit récupérer les données de la vue pour lancer le partie
        System.out.println("Lancement de la partie !!!");
        plateau.afficher(null);
        boolean fini=false;
        Scanner sc=new Scanner(System.in);
        distribuerObstacles();
        for(int i=0;i<4;i++){
            if(!fini) {
                for (int j = 0; j < nbJoueur; j++) {
                    if (joueurs.get(j).getMarqueurs().size() == 20) {
                        fini = true;
                    }
                }
                //Phase de melange et distribution
                System.out.println("Phase préparatoire !");
                melanger();
                distribuerCarte();
                distribuerPionPremier();
                piocherPionDeuxieme();
                //Phase de préparation des routes
                System.out.println("Phase de préparation des routes !");
                String type;
                int pion,pionDebut;
                for(int j=0;j<joueurs.size();j++){
                    plateau.afficher(joueurs.get(j));
                    //pionDebut=joueurs.get(j).getMainPionVisible().size()+joueurs.get(j).getMainPion().size();
                    boolean changement=false;
                    if(!joueurs.get(j).isIa()) {
                        do {
                            type = demandeTypePion(j);
                            if (type.equals("Visible") && joueurs.get(j).getMainPionVisible().size() > 0) {
                                pion = demandeIndicePionVisible(j);
                                changement = joueurs.get(j).poserPion(type, pion, demandeIndice(j, "X", 0, 45), demandeIndice(j, "Y", 0, 32));
                                if (changement) {
                                    joueurs.get(j).getMainPionVisible().remove(pion);
                                }
                            } else if (type.equals("Caché") && joueurs.get(j).getMainPion().size() > 0) {
                                pion = demandeIndicePion(j);
                                changement = joueurs.get(j).poserPion(type, pion, demandeIndice(j, "X", 0, 45), demandeIndice(j, "Y", 0, 32));
                                if (changement) {
                                    joueurs.get(j).getMainPion().remove(pion);
                                }
                            } else {
                                System.out.println("Impossible vous n'avez pas de pions de ce type !");
                            }
                        } while (!changement); //Devrait ici pouvoir jouer plusieurs fois d'affilé
                    }else{
                        joueurs.get(j).getOrdinateur().placerMoyenTransport();
                    }
                }
                //Phase de Déplacement
                System.out.println("Phase de déplacement !");
                String direction;
                boolean arret;
                String commande;
                for(int k=0;k<joueurs.size();k++){
                    arret=false;
                    System.out.println("Au tour du joueur : "+joueurs.get(k).getPionJoueur().getValeur());
                    plateau.afficher(joueurs.get(k));
                    do {
                        if(!joueurs.get(k).isIa()) {
                            do {
                                System.out.print("Que souhaitez vous faire ? (Déplacer / Ramasser / Finir) ");
                                commande = sc.nextLine();
                            } while (!commande.equals("Déplacer") && !commande.equals("Ramasser") && !commande.equals("Finir"));
                            if (commande.equals("Déplacer")) {
                                direction = demandeDirection(k);
                                joueurs.get(k).deplacerPion(direction);
                            } else if (commande.equals("Ramasser")) {
                                joueurs.get(k).ramasserMarqueur();
                            } else if (commande.equals("Finir")) {
                                arret = true;
                            }
                        }else{
                            arret=joueurs.get(k).getOrdinateur().deplacement();
                        }
                        plateau.afficher(joueurs.get(k));
                    }while(!arret && !joueurs.get(k).jouerImpossible());
                }
                recupererPions();
                swapPremier();
                System.out.println("Nouveau tour !");
            }
        }

        System.out.println("Fin du jeu !");
        int maxMarqueurs=joueurs.get(0).getMarqueurs().size();
        Joueur jmax=joueurs.get(0);
        for(int i=1;i<nbJoueur;i++){
            if(joueurs.get(i).getMarqueurs().size()>maxMarqueurs){
                maxMarqueurs=joueurs.get(i).getMarqueurs().size();
                jmax=joueurs.get(i);
            }else if(joueurs.get(i).getMarqueurs().size()==maxMarqueurs && joueurs.get(i).getMainCarte().size()>jmax.getMainCarte().size()){
                maxMarqueurs=joueurs.get(i).getMarqueurs().size();
                jmax=joueurs.get(i);
            }
        }
        System.out.println("Le gagnant est le joueur : "+jmax.getPionJoueur().getValeur());
    }

    public void lancerVariante(){
        //Fera l'objet de notre reflexion une fois la version normale terminee
    }

    public FabriqueMarqueurVillage getFabriqueMarqueurVillage() {
        return fabriqueMarqueurVillage;
    }

    public void setFabriqueMarqueurVillage(FabriqueMarqueurVillage fabriqueMarqueurVillage) {
        this.fabriqueMarqueurVillage = fabriqueMarqueurVillage;
    }

    public FabriqueCarteTourDeJeu getFabriqueCarteTourDeJeu() {
        return fabriqueCarteTourDeJeu;
    }

    public void setFabriqueCarteTourDeJeu(FabriqueCarteTourDeJeu fabriqueCarteTourDeJeu) {
        this.fabriqueCarteTourDeJeu = fabriqueCarteTourDeJeu;
    }

    public FabriqueCarteVoyage getFabriqueCarteVoyage() {
        return fabriqueCarteVoyage;
    }

    public void setFabriqueCarteVoyage(FabriqueCarteVoyage fabriqueCarteVoyage) {
        this.fabriqueCarteVoyage = fabriqueCarteVoyage;
    }

    public FabriquePionMoyenDeTransport getFabriquePionMoyenDeTransport() {
        return fabriquePionMoyenDeTransport;
    }

    public void setFabriquePionMoyenDeTransport(FabriquePionMoyenDeTransport fabriquePionMoyenDeTransport) {
        this.fabriquePionMoyenDeTransport = fabriquePionMoyenDeTransport;
    }

    public FabriquePionObstacle getFabriquePionObstacle() {
        return fabriquePionObstacle;
    }

    public void setFabriquePionObstacle(FabriquePionObstacle fabriquePionObstacle) {
        this.fabriquePionObstacle = fabriquePionObstacle;
    }

    public FabriquePionJoueur getFabriquePionJoueur() {
        return fabriquePionJoueur;
    }

    public void setFabriquePionJoueur(FabriquePionJoueur fabriquePionJoueur) {
        this.fabriquePionJoueur = fabriquePionJoueur;
    }

    public FabriqueCarteResume getFabriqueCarteResume() {
        return fabriqueCarteResume;
    }

    public void setFabriqueCarteResume(FabriqueCarteResume fabriqueCarteResume) {
        this.fabriqueCarteResume = fabriqueCarteResume;
    }

    public ArrayList<MarqueurVillage> getMarqueurs() {
        return marqueurs;
    }

    public void setMarqueurs(ArrayList<MarqueurVillage> marqueurs) {
        this.marqueurs = marqueurs;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public ArrayList<Pion> getPionJoueurs() {
        return pionJoueurs;
    }

    public void setPionJoueurs(ArrayList<Pion> pionJoueurs) {
        this.pionJoueurs = pionJoueurs;
    }

    public ArrayList<Pion> getPionObstacle() {
        return pionObstacle;
    }

    public void setPionObstacle(ArrayList<Pion> pionObstacle) {
        this.pionObstacle = pionObstacle;
    }

    public ArrayList<Carte> getCarteTourDeJeu() {
        return carteTourDeJeu;
    }

    public void setCarteTourDeJeu(ArrayList<Carte> carteTourDeJeu) {
        this.carteTourDeJeu = carteTourDeJeu;
    }

    public ArrayList<CarteResume> getCarteResume() {
        return carteResume;
    }

    public void setCarteResume(ArrayList<CarteResume> carteResume) {
        this.carteResume = carteResume;
    }

    public CarteResume getResume() {
        return resume;
    }

    public void setResume(CarteResume resume) {
        this.resume = resume;
    }

    public ArrayList<Carte> getCarteVoyage() {
        return carteVoyage;
    }

    public void setCarteVoyage(ArrayList<Carte> carteVoyage) {
        this.carteVoyage = carteVoyage;
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(ArrayList<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public int getNbJoueur() {
        return nbJoueur;
    }

    public void setNbJoueur(int nbJoueur) {
        this.nbJoueur = nbJoueur;
    }

    public ArrayList<Carte> getDefausseVoyage() {
        return defausseVoyage;
    }

    public void setDefausseVoyage(ArrayList<Carte> defausseVoyage) {
        this.defausseVoyage = defausseVoyage;
    }

    public static void main(String[] args){
        int nbJ=0,nbIA=0;
        Scanner sc=new Scanner(System.in);
        do {
            System.out.print("Entrez le nombre de joueur : (2 à 6) ");
            nbJ = sc.nextInt();
        }while(nbJ<2 || nbJ>6);
        do {
            System.out.print("Entrez le nombre d'IA : (0 à "+(6-nbJ)+") ");
            nbIA = sc.nextInt();
        }while(nbIA<0 || nbIA>6-nbJ);
        Partie partie=new Partie(nbJ,nbIA);
        //Plateau vide puisqu'il reste à reproduire en dur le plateau sous forme d'une matrice
        //Test d'affichage rapide
        partie.fabriqueCarteResume.creer().afficher();
        partie.lancerModeNormal();
    }
}
