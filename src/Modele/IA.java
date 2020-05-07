package Modele;

import java.io.Serializable;
import java.util.ArrayList;

public class IA implements Serializable {
    private Joueur joueur;//Vérifier que l'instance est bien synchronisée avec le joueur possédant l'IA
    private int distanceMin;
    private CaseVillage villageProche;
    private ArrayList<CaseVillage> cheminOpti;
    private CaseChemin piege; //Chemin cible à piéger
    private Joueur best;
    protected FabriqueCarteResume fabResume;
    private String typeGarder;
    private int indiceGarder;

    public IA(Joueur j){
        joueur=j;
        distanceMin=-1;
        fabResume=fabResume.getInstance();
    }

    public void garderPion(){
        typeGarder="";
        indiceGarder=-1;
        CaseVillage depart=(CaseVillage)joueur.getPartie().getPlateau().getCases()[joueur.getPionJoueur().getX()][joueur.getPionJoueur().getY()];
        getCheminVillePlusProche(depart,getVillePlusProche());
        ArrayList<Pion> mainVisible=joueur.getMainPionVisible();
        CarteResume resume=fabResume.creer();
        for(int i=0;i<cheminOpti.size()-1;i++){ //1er essai on cherche un pion visible de besoin 1
            if(typeGarder.equals("") && indiceGarder==-1) {
                for (int j = 0; j < mainVisible.size(); j++) {
                    if (resume.getBesoin(getChemin(cheminOpti.get(i), cheminOpti.get(i + 1)).getTerrain(), mainVisible.get(j).getValeur()) == 1) {
                        typeGarder = "Visible";
                        indiceGarder = i;
                    }
                }
            }
        }
        if(typeGarder.equals("") && indiceGarder==-1) {
            for (int i = 0; i < cheminOpti.size() - 1; i++) { //2eme essai on cheche un besoin 2
                if(typeGarder.equals("") && indiceGarder==-1) {
                    for (int j = 0; j < mainVisible.size(); j++) {
                        if (resume.getBesoin(getChemin(cheminOpti.get(i), cheminOpti.get(i + 1)).getTerrain(), mainVisible.get(j).getValeur()) == 2) {
                            typeGarder = "Visible";
                            indiceGarder = i;
                        }
                    }
                }
            }
        }
        if(typeGarder.equals("") && indiceGarder==-1){ //Si on a pas trouvé dans les visibles on prend dans les cachés
            typeGarder="Caché";
            indiceGarder=(int)(Math.random()*(joueur.getMainPion().size()-1));
        }
    }

    public boolean deplacement(){
        boolean arret=false;
        String res="";
        if(!joueur.jouerImpossible()){
            int direction=0;
            for(int i=0;i<7;i++) {
                try {
                    if (cheminOpti.get(0).getChemin(i).getOppose(cheminOpti.get(0).getX(), cheminOpti.get(0).getY()) == cheminOpti.get(1)) {
                        direction = i;
                    }
                }catch(Exception e){}
            }
            if(direction==0) {
                res="Nord";
            }else if(direction==1) {
                res="NordEst";
            }else if(direction==2) {
                res="Est";
            }else if(direction==3){
                res="SudEst";
            }else if(direction==4){
                res="Sud";
            }else if(direction==5){
                res="SudOuest";
            }else if(direction==6){
                res="Ouest";
            }else if(direction==7){
                res="NordOuest";
            }
            joueur.deplacerPion(res);
            cheminOpti.remove(0);
        }
        if(joueur.getPartie().getPlateau().getMarqueur()[joueur.getPionJoueur().getX()][joueur.getPionJoueur().getY()][((PionJoueur) joueur.getPionJoueur()).getIdCouleur()]!=null){
            joueur.ramasserMarqueur();
        }
        return joueur.jouerImpossible() || cheminOpti.size()==1;
    }

    public void placerMoyenTransport(){
        //Initialise la ville cible et le chemin
        updateBest();
        CaseVillage depart=(CaseVillage)joueur.getPartie().getPlateau().getCases()[joueur.getPionJoueur().getX()][joueur.getPionJoueur().getY()];
        getCheminVillePlusProche(depart,getVillePlusProche());
        CaseChemin chemin=null;
        CarteResume resume=fabResume.creer();
        int i=1,indice=0;
        while(i<cheminOpti.size() && chemin==null) {
            //System.out.println("OK");
            if (getChemin(cheminOpti.get(i - 1), cheminOpti.get(i)).getTransport() == null) {
                for(int j=0;j<joueur.getMainPionVisible().size();j++){
                    if(resume.getBesoin(getChemin(cheminOpti.get(i - 1), cheminOpti.get(i)).getTerrain(),joueur.getMainPionVisible().get(j).getValeur())!=0){
                        chemin=getChemin(cheminOpti.get(i - 1), cheminOpti.get(i));
                        indice=j;
                    }
                }
            }
            i++;
        }
        if(chemin!=null){
            joueur.poserPion("Visible",indice, chemin.getX(), chemin.getY());
            joueur.getMainPionVisible().remove(indice);
        }else if(chemin==null && joueur.getObstacle()!=null){//On piège
            best.getOrdinateur().getCheminVillePlusProche((CaseVillage)best.getPartie().getPlateau().getCases()[best.getPionJoueur().getX()][best.getPionJoueur().getY()],best.getOrdinateur().getVillePlusProche());
            //Initialise le chemin cible du joueur à piéger
            for(int j=1;j<best.getOrdinateur().getCheminOpti().size();j++){
                CaseChemin ch= getChemin(best.getOrdinateur().getCheminOpti().get(j-1),best.getOrdinateur().getCheminOpti().get(j));
                if(ch.getTransport() == null && joueur.getObstacle()!=null){
                    ch.setTransport(joueur.getObstacle());
                    joueur.setObstacle(null);
                }
            }
        }else{//On passe
            joueur.getMainPionVisible().add(joueur.getMainPion().remove(0));
        }
    }

    public void updateBest(){
        //Détermine le joueur à piéger
        ArrayList<Joueur> joueurs=joueur.getPartie().getJoueurs();
        int max=-1;
        for(int i=0;i<joueurs.size();i++){
            if(joueurs.get(i).getMarqueurs().size()>max && joueurs.get(i)!=joueur){
                max=joueurs.get(i).getMarqueurs().size();
                best=joueurs.get(i);
            }
        }
    }

    public int piocherPion(){
        //Initialise la ville cible et le chemin
        updateBest();
        CaseVillage depart=(CaseVillage)joueur.getPartie().getPlateau().getCases()[joueur.getPionJoueur().getX()][joueur.getPionJoueur().getY()];
        getCheminVillePlusProche(depart,getVillePlusProche());
        ArrayList<String> transports=new ArrayList<>();
        CarteResume resume=fabResume.creer();
        for(int i=1;i<cheminOpti.size();i++){
            transports.add(getChemin(cheminOpti.get(i-1),cheminOpti.get(i)).getTerrain());
        }
        int indicePion=-1,i=0,j,k;
        ArrayList<Pion> inventaire=joueur.getMainPionVisible();
        ArrayList<Pion> pionsVisibles=joueur.getPartie().getMoyenDeTransportVisible();
        while(i<transports.size() && indicePion==-1){
            j=0;
            while(j<pionsVisibles.size() && indicePion==-1){
                if(resume.getBesoin(transports.get(i),pionsVisibles.get(j).getValeur())==1){
                    indicePion=j;
                }
                j++;
            }
            k=0;
            while(k<pionsVisibles.size() && indicePion==-1){
                if(resume.getBesoin(transports.get(i),pionsVisibles.get(k).getValeur())==2 && inventaire.contains(pionsVisibles.get(k))){
                    indicePion=j;
                }
                k++;
            }
            i++;
        }
        //System.out.println(indicePion);
        return indicePion;
    }

    public CaseChemin getChemin(CaseVillage dep,CaseVillage arr){// A VOIR AVEC LES CHANGEMENTS DE RIVIERES
        CaseChemin chemin=null;
        int i=0;
        while(i<=15 && chemin == null){
            try {
                if (dep.getChemin(i).getOppose(dep.getX(), dep.getY()).getValeur().equals(arr.getValeur())) {
                    chemin = dep.getChemin(i);
                }
            }catch(Exception e){}
            i++;
        }
        return chemin;
    }

    public CaseVillage getVillePlusProche(){ //Algorithme de parcours de graphe en largeur
        PionJoueur pj=(PionJoueur)joueur.getPionJoueur();
        CaseVillage actuelle=(CaseVillage)joueur.getPartie().getPlateau().getCases()[pj.getX()][pj.getY()];
        CaseVillage resultat=actuelle;
        try {
            if (joueur.getPartie().getPlateau().getMarqueur()[pj.getX()][pj.getY()][pj.getIdCouleur()] != null) {
                return resultat;
            }else{
                resultat=getPlusProche(actuelle);
            }
        }catch(Exception e){
            resultat=getPlusProche(actuelle);
        }
        //System.out.println(actuelle.getValeur()+" --> "+resultat.getValeur());
        return resultat;
    }

    public CaseVillage getPlusProche(CaseVillage actuelle){
        ArrayList<CaseVillage> visites = new ArrayList<>();
        ArrayList<CaseVillage> reste = new ArrayList<>();
        ArrayList<CaseVillage> voisins;
        ArrayList<CaseVillage> res = new ArrayList<>();
        ArrayList<Integer> nombres = new ArrayList<>();
        ArrayList<String> villes = new ArrayList<>();
        CaseVillage courant;
        reste.add(actuelle);
        PionJoueur pj=(PionJoueur) joueur.getPionJoueur();
        int minimum;
        CaseVillage resultat;
        while (visites.size() < 21 && res.size() < 1) {
            courant = reste.remove(0);
            voisins = getVoisins(courant);
            for (int i = 0; i < voisins.size(); i++) {
                if (!visites.contains(voisins.get(i))) {
                    villes.add(voisins.get(i).getValeur());
                    visites.add(voisins.get(i));
                    if (!visites.contains(courant)) { //Si visite ne contient pas courant c'est que courant est celui initial
                        nombres.add(getCarteAUtiliser(getChemin(courant,voisins.get(i))));
                    } else {
                        nombres.add(getCarteAUtiliser(getChemin(courant,voisins.get(i))) + nombres.get(visites.indexOf(courant)));
                    }
                    try {
                        if (joueur.getPartie().getPlateau().getMarqueur()[voisins.get(i).getX()][voisins.get(i).getY()][pj.getIdCouleur()] != null) {
                            minimum = 10;
                            try {
                                for (int j = 0; j < res.size(); j++) {
                                    if (nombres.get(visites.indexOf(res.get(j))) < minimum) {
                                        minimum = nombres.get(visites.indexOf(res.get(j))); //On cherche le minimum dans res
                                    }
                                }
                            }catch(Exception e){}
                            if (nombres.get(visites.indexOf(voisins.get(i))) <= minimum) {
                                res.add(voisins.get(i));    //On cumul les villages opti dans res
                            }
                        }
                    } catch (Exception e) { }
                    reste.add(voisins.get(i));
                }
            }
        }
        if (res.size() <= 1) {
            resultat = res.remove(0);
            distanceMin = nombres.get(villes.indexOf(resultat.getValeur()));
            villageProche=resultat;
        } else {
            int min = 10;
            for (int i = 0; i < res.size(); i++) {
                if (nombres.get(visites.indexOf(res.get(i))) <= min) {
                    min = nombres.get(visites.indexOf(res.get(i))); //Cherche le minimum dans res
                }
            }
            for (int i = 0; i < res.size(); i++) {
                if (nombres.get(visites.indexOf(res.get(i))) > min) {
                    res.remove(i); //Retire les village > min
                }
            }
            int rand = (int) (Math.random() * (res.size()-1));
            resultat = res.get(rand);
            if(villageProche==null){ // Initialisation du best
                distanceMin=min;
                villageProche=resultat;
            }else{
                if(res.contains(villageProche)){
                    resultat=villageProche; // On garde le village qu'on a déjà
                }else{
                    villageProche=resultat; // On prend le nouveau best
                }
            }
        }
        return resultat;
    }

    public int getCarteAUtiliser(CaseChemin cc){ //Retourne le nombre minimum de cartes à poser pour utiliser le chemin
        int minimum=10;
        try{
            if(cc.getTransport()==null) {
                return 1;
            }else{
                ArrayList<CarteVoyage> cartes=joueur.getMainCarte();
                CarteResume cr=new CarteResume();
                int indiceT=cr.getEnvironnement().indexOf(cc.getTerrain());
                int nb=0;
                for(int i=0;i<cr.getList()[indiceT].length;i++){
                    String tp=cr.getTypeTransport().get(i);
                    for(int j=0;j<cartes.size();j++){
                        if(cartes.get(i).equals(tp)){
                            nb++;
                        }
                    }
                    if(cr.getList()[indiceT][i] < minimum && cr.getList()[indiceT][i] <= nb){
                        minimum=nb;
                    }
                }
            }
        }catch(Exception e){
            minimum=1;
        }
        return minimum;
    }

    //Recherche Nord->Nord-Est ... récurisvement connaissant la distanceMin
    public void getCheminVillePlusProche(CaseVillage courant,CaseVillage arrivee){
        cheminOpti=null;
        ArrayList<CaseVillage> voisins=getVoisins(courant);
        ArrayList<CaseVillage> chemin;
        while(cheminOpti==null && voisins.size()>0){
            chemin=new ArrayList<>();
            chemin.add(courant);
            chemin.add(voisins.remove(0));
            getCheminVillePlusProcheAux(chemin,getCarteAUtiliser(getChemin(courant,chemin.get(1))),arrivee);
        }

        if(cheminOpti==null){
            System.out.println("Chemin non trouvé ...\n"+courant.getValeur()+" --> "+arrivee.getValeur()+" - "+distanceMin);
        }
        while(cheminOpti==null){}
    }

    public void getCheminVillePlusProcheAux(ArrayList<CaseVillage> chemin,int poids,CaseVillage arrivee){
        ArrayList<CaseVillage> ch=chemin;
        ArrayList<CaseVillage> voisins=getVoisins(ch.get(ch.size()-1));
        for(int i=0;i<voisins.size();i++){
            if(poids+getCarteAUtiliser(getChemin(ch.get(ch.size()-2),ch.get(ch.size()-1)))<distanceMin && !ch.contains(voisins.get(i))){
                ArrayList<CaseVillage> next=ch;
                next.add(voisins.get(i));
                getCheminVillePlusProcheAux(next,poids+getCarteAUtiliser(getChemin(next.get(next.size()-2),next.get(next.size()-1))),arrivee);
            }
        }
        if(poids <= distanceMin && chemin.get(chemin.size()-1)==arrivee){
            cheminOpti=chemin;
        }
    }

    public ArrayList<CaseVillage> getVoisins(CaseVillage c){ //Retourne l'ensemble des voisins
        ArrayList<CaseVillage> list=new ArrayList<>();
        try{
            if(c.getCheminNord()!=null){
                list.add(c.getCheminNord().getVillageNord());
            }
        }catch(Exception e){ }
        try {
            if (c.getCheminNordEst() != null) {
                list.add(c.getCheminNordEst().getVillageNordEst());
            }
        }catch(Exception e){ }
        try {
            if (c.getCheminEst() != null) {
                list.add(c.getCheminEst().getVillageEst());
            }
        }catch(Exception e){ }
        try {
            if (c.getCheminSudEst() != null) {
                list.add(c.getCheminSudEst().getVillageSudEst());
            }
        }catch(Exception e){ }
        try {
            if (c.getCheminSud() != null) {
                list.add(c.getCheminSud().getVillageSud());
            }
        }catch(Exception e){ }
        try {
            if (c.getCheminSudOuest() != null) {
                list.add(c.getCheminSudOuest().getVillageSudOuest());
            }
        }catch(Exception e){ }
        try {
            if (c.getCheminOuest() != null) {
                list.add(c.getCheminOuest().getVillageOuest());
            }
        }catch(Exception e){ }
        try {
            if (c.getCheminNordOuest() != null) {
                list.add(c.getCheminNordOuest().getVillageNordOuest());
            }
        }catch(Exception e){ }
        try {
            if (c.getRiviereNord() != null) {
                list.add(c.getRiviereNord().getVillageNord());
            }
        }catch(Exception e){ }
        try {
            if (c.getRiviereNordEst() != null) {
                list.add(c.getRiviereNordEst().getVillageNordEst());
            }
        }catch(Exception e){ }
        try {
            if (c.getRiviereEst() != null) {
                list.add(c.getRiviereEst().getVillageEst());
            }
        }catch(Exception e){ }
        try {
            if (c.getRiviereSudEst() != null) {
                list.add(c.getRiviereSudEst().getVillageSudEst());
            }
        }catch(Exception e){ }
        try {
            if (c.getRiviereSud() != null) {
                list.add(c.getRiviereSud().getVillageSud());
            }
        }catch(Exception e){ }
        try {
            if (c.getRiviereSudOuest() != null) {
                list.add(c.getRiviereSudOuest().getVillageSudOuest());
            }
        }catch(Exception e){ }
        try {
            if (c.getRiviereOuest() != null) {
                list.add(c.getRiviereOuest().getVillageOuest());
            }
        }catch(Exception e){ }
        try {
            if (c.getRiviereNordOuest() != null) {
                list.add(c.getRiviereNordOuest().getVillageNordOuest());
            }
        }catch(Exception e){ }
        return list;
    }

    public boolean marqueurVoisin(CaseVillage cv){ //Retourne true si un voisin possède un marqueur et false sinon
        PionJoueur pj=(PionJoueur)joueur.getPionJoueur();
        int idCouleur=pj.getIdCouleur();
        return cv.getCheminNord().getVillageNord().getMarqueurs().get(idCouleur) !=null ||
                cv.getCheminNordEst().getVillageNordEst().getMarqueurs().get(idCouleur) !=null ||
                cv.getCheminEst().getVillageEst().getMarqueurs().get(idCouleur) !=null ||
                cv.getCheminSudEst().getVillageSudEst().getMarqueurs().get(idCouleur) !=null ||
                cv.getCheminSud().getVillageSud().getMarqueurs().get(idCouleur) !=null ||
                cv.getCheminSudOuest().getVillageSudOuest().getMarqueurs().get(idCouleur) !=null ||
                cv.getCheminOuest().getVillageOuest().getMarqueurs().get(idCouleur) !=null ||
                cv.getCheminNordOuest().getVillageNordOuest().getMarqueurs().get(idCouleur) !=null;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public int getDistanceMin() {
        return distanceMin;
    }

    public void setDistanceMin(int distanceMin) {
        this.distanceMin = distanceMin;
    }

    public ArrayList<CaseVillage> getCheminOpti() {
        return cheminOpti;
    }

    public void setCheminOpti(ArrayList<CaseVillage> cheminOpti) {
        this.cheminOpti = cheminOpti;
    }

    public CaseChemin getPiege() {
        return piege;
    }

    public void setPiege(CaseChemin piege) {
        this.piege = piege;
    }

    public Joueur getBest() {
        return best;
    }

    public FabriqueCarteResume getFabResume() {
        return fabResume;
    }

    public String getTypeGarder() {
        return typeGarder;
    }

    public int getIndiceGarder() {
        return indiceGarder;
    }
}
