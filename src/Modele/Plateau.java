package Modele;

import java.io.Serializable;

public class Plateau implements Serializable {
    public Case[][] cases;
    private Pion[][][] pions;
    private FabriqueVillage fabriqueVillage;
    private FabriqueChemin fabriqueChemin;
    private MarqueurVillage[][][] marqueurs;

    public Plateau(){
        cases=new Case[46][33];
        //Indice 0 --> PionTP
        //Indice 1 --> PionObstacle
        //Indice 2-7 --> Pion Joueur
        pions=new Pion[46][33][8];
        marqueurs=new MarqueurVillage[46][33][6];
        fabriqueChemin=fabriqueChemin.getInstance();
        fabriqueVillage=fabriqueVillage.getInstance();
        //Insérer à partir d'ici le code modélisant le plateau (génération des villes et chemins)

        //Création des villages
        cases[3][5]=creerCaseVillage(3,5,"Usselen");
        cases[2][12]=creerCaseVillage(2,12,"Yttar");
        cases[2][21]=creerCaseVillage(2,21,"Grangor");
        cases[2][28]=creerCaseVillage(2,28,"Mah'Davikia");
        cases[10][1]=creerCaseVillage(10,1,"Wylhién");
        cases[10][10]=creerCaseVillage(10,10,"Parundia");
        cases[10][18]=creerCaseVillage(10,18,"Kihromah");
        cases[18][3]=creerCaseVillage(18,3,"Jaccaranda");
        cases[16][13]=creerCaseVillage(16,13,"Al'Baran");
        cases[16][20]=creerCaseVillage(16,20,"Dag'Amura");
        cases[15][28]=creerCaseVillage(15,28,"Jxara");
        cases[27][7]=creerCaseVillage(27,7,"Throtmanni");
        cases[24][15]=creerCaseVillage(24,15,"Feodor");
        cases[25][22]=creerCaseVillage(25,22,"Lapphalya");
        cases[28][29]=creerCaseVillage(28,29,"Virst");
        cases[35][4]=creerCaseVillage(35,4,"Tichih'");
        cases[33][11]=creerCaseVillage(33,11,"Rivinia");
        cases[35][18]=creerCaseVillage(35,18,"Elvenhold");
        cases[37][27]=creerCaseVillage(37,27,"Strykhaven");
        cases[42][11]=creerCaseVillage(42,11,"Erg'Erén");
        cases[43][23]=creerCaseVillage(43,23,"Beata");

        //Création des chemins
        cases[5][2]=creerCaseChemin(5,2,"U-W","Plaine",null,(CaseVillage)cases[10][1],null,null,null,(CaseVillage)cases[3][5],null,null,null);
        cases[9][6]=creerCaseChemin(9,6,"P-W","Plaine",(CaseVillage)cases[10][1],null,null,null,(CaseVillage)cases[10][10],null,null,null,null);
        cases[6][8]=creerCaseChemin(6,8,"U-P","Forêt",null,null,null,(CaseVillage)cases[10][10],null,null,null,(CaseVillage)cases[3][5],null);
        cases[2][9]=creerCaseChemin(2,9,"Y-U","Forêt", (CaseVillage)cases[3][5],null,null,null,(CaseVillage)cases[2][12],null,null,null,null);
        cases[2][17]=creerCaseChemin(2,17,"G-Y","Montagne",(CaseVillage)cases[2][12],null,null,null,(CaseVillage)cases[2][21],null,null,null,null);
        cases[1][25]=creerCaseChemin(1,25,"M-G","Montagne",(CaseVillage)cases[2][21],null,null,null,(CaseVillage)cases[2][28],null,null,null,null);
        cases[3][18]=creerCaseChemin(3,18,"G-Y","Mer",(CaseVillage)cases[2][12],null,null,null,(CaseVillage)cases[2][21],null,null,null,null);
        cases[6][17]=creerCaseChemin(6,17,"G-P","Mer",null,(CaseVillage)cases[10][10],null,null,null,(CaseVillage)cases[2][21],null,null,null);
        cases[6][12]=creerCaseChemin(6,12,"Y-P","Mer",null,(CaseVillage)cases[10][10],null,null,null,(CaseVillage)cases[2][12],null,null,null);
        cases[16][2]=creerCaseChemin(16,2,"W-J","Montagne",null,null,(CaseVillage)cases[18][3],null,null,null,(CaseVillage)cases[10][1],null,null);
        cases[15][7]=creerCaseChemin(15,7,"W-A","Désert",null,null,null,(CaseVillage)cases[16][13],null,null,null,(CaseVillage)cases[10][1],null);
        cases[14][11]=creerCaseChemin(14,11,"P-A","Plaine",(CaseVillage)cases[10][1],null,null,null,(CaseVillage)cases[10][10],null,null,null,null);
        cases[17][17]=creerCaseChemin(17,17,"D-A","Désert",(CaseVillage)cases[16][13],null,null,null,(CaseVillage)cases[16][20],null,null,null,null);
        cases[13][19]=creerCaseChemin(13,19,"K-D","Forêt",null,null,(CaseVillage)cases[16][20],null,null,null,(CaseVillage)cases[10][18],null,null);
        cases[10][24]=creerCaseChemin(10,24,"M-D","Montagne",null,(CaseVillage)cases[16][20],null,null,null,(CaseVillage)cases[2][28],null,null,null);
        cases[10][29]=creerCaseChemin(10,29,"M-J","Montagne",null,null,(CaseVillage)cases[15][28],null,null,null,(CaseVillage)cases[2][28],null,null);
        cases[16][24]=creerCaseChemin(16,24,"J-D","Forêt",(CaseVillage)cases[16][20],null,null,null,(CaseVillage) cases[15][28],null,null,null,null);
        cases[27][3]=creerCaseChemin(27,3,"J-T", "Montagne",null,null,(CaseVillage)cases[35][4],null,null,null,(CaseVillage)cases[18][3],null,null);
        cases[22][6]=creerCaseChemin(22,6,"J-T","Montagne",null,null,null,(CaseVillage)cases[27][7],null,null,null,(CaseVillage)cases[18][3],null);
        cases[21][10]=creerCaseChemin(21,10,"A-T","Désert",null,(CaseVillage)cases[27][7],null,null,null,(CaseVillage)cases[16][13],null,null,null);
        cases[21][13]=creerCaseChemin(21,13,"A-F","Désert",null,null,(CaseVillage)cases[24][15],null,null,null,(CaseVillage)cases[16][13],null,null);
        cases[20][17]=creerCaseChemin(20,17,"D-F","Désert",null,(CaseVillage)cases[24][15],null,null,null,(CaseVillage)cases[16][20],null,null,null);
        cases[20][22]=creerCaseChemin(20,22,"D-L","Forêt",null,null,(CaseVillage)cases[25][22],null,null,null,(CaseVillage)cases[16][20],null,null);
        cases[21][26]=creerCaseChemin(21,26,"J-L","Forêt",null,(CaseVillage)cases[25][22],null,null,null,(CaseVillage)cases[15][28],null,null,null);
        cases[20][30]=creerCaseChemin(20,30,"J-V","Plaine",null,null,(CaseVillage)cases[28][29],null,null,null,(CaseVillage)cases[15][28],null,null);
        cases[32][6]=creerCaseChemin(32,6,"T-T","Plaine",null,(CaseVillage)cases[35][4],null,null,null,(CaseVillage)cases[27][7],null,null,null);
        cases[24][12]=creerCaseChemin(24,12,"F-T","Désert",(CaseVillage)cases[27][7],null,null,null,(CaseVillage)cases[24][15],null,null,null,null);
        cases[26][19]=creerCaseChemin(26,19,"L-F","Forêt",(CaseVillage)cases[24][15],null,null,null,(CaseVillage)cases[25][22],null,null,null,null);
        cases[25][26]=creerCaseChemin(25,16,"L-V","Plaine",(CaseVillage)cases[25][22],null,null,null,(CaseVillage)cases[28][29],null,null,null,null);
        cases[39][9]=creerCaseChemin(39,9,"T-E", "Forêt",null,null,null,(CaseVillage)cases[42][11],null,null,null,(CaseVillage)cases[35][4],null);
        cases[30][9]=creerCaseChemin(30,9,"T-R","Forêt",null,null,null,(CaseVillage)cases[33][11],null,null,null,(CaseVillage)cases[27][7],null);
        cases[28][13]=creerCaseChemin(28,13,"F-R","Forêt",null,(CaseVillage)cases[33][11],null,null,null,(CaseVillage)cases[24][15],null,null,null);
        cases[29][16]=creerCaseChemin(29,16,"L-R","Forêt",null,(CaseVillage)cases[33][11],null,null,null,(CaseVillage)cases[25][22],null,null,null);
        cases[30][21]=creerCaseChemin(30,21,"L-E","Plaine",null,null,(CaseVillage)cases[35][18],null,null,null,(CaseVillage)cases[25][22],null,null);
        cases[32][24]=creerCaseChemin(32,24,"V-E","Mer",null,(CaseVillage)cases[35][18],null,null,null,(CaseVillage)cases[28][29],null,null,null);
        cases[33][27]=creerCaseChemin(33,27,"V-S","Mer",null,null,(CaseVillage)cases[37][27],null,null,null,(CaseVillage)cases[28][29],null,null);
        cases[34][31]=creerCaseChemin(34,31,"V-S","Montagne",null,null,(CaseVillage)cases[37][27],null,null,null,(CaseVillage)cases[28][29],null,null);
        cases[41][15]=creerCaseChemin(41,15,"E-E","Forêt",null,(CaseVillage)cases[42][11],null,null,null,(CaseVillage)cases[35][18],null,null,null);
        cases[40][21]=creerCaseChemin(40,21,"E-B","Plaine",null,null,null,(CaseVillage)cases[43][23],null,null,null,(CaseVillage)cases[35][18],null);
        cases[36][24]=creerCaseChemin(36,24,"E-S","Mer",(CaseVillage)cases[35][18],null,null,null,(CaseVillage)cases[37][27],null,null,null,null);
        cases[41][27]=creerCaseChemin(41,27,"S-B","Plaine",null,null,(CaseVillage)cases[43][23],null,null,null,(CaseVillage)cases[37][27],null,null);
        cases[7][5]=creerCaseChemin(7,5,"U-W","Mer",null,(CaseVillage)cases[10][1],null,null,null,(CaseVillage)cases[3][5],null,null,"SudOuest");
        cases[3][25]=creerCaseChemin(3,25,"M-G", "Mer",(CaseVillage)cases[2][21],null,null,null,(CaseVillage)cases[2][28],null,null,null,"Nord");
        cases[10][31]=creerCaseChemin(10,31,"M-J","Mer",null,null,(CaseVillage)cases[15][28],null,null,null,(CaseVillage)cases[2][28],null,"Ouest");
        cases[20][31]=creerCaseChemin(20,31,"J-V","Mer",null,null,(CaseVillage)cases[28][29],null,null,null,(CaseVillage)cases[15][28],null,"Ouest");
        cases[35][8]=creerCaseChemin(27,8,"R-T","Mer",(CaseVillage) cases[35][4],null,null,null,(CaseVillage)cases[33][11],null,null,null,"Nord");
        cases[36][14]=creerCaseChemin(36,14,"E-R","Mer",(CaseVillage)cases[33][11],null,null,null,(CaseVillage) cases[35][18],null,null,null,"Nord");
        cases[41][20]=creerCaseChemin(41,20,"E-B","Mer",null,null,null,(CaseVillage)cases[43][23],null,null,null,(CaseVillage)cases[35][18],"NordOuest");
    }

    public Case creerCaseChemin(int x,int y,String valeur,String terr,CaseVillage n,CaseVillage ne,CaseVillage e,
                                CaseVillage se,CaseVillage s,CaseVillage so,CaseVillage o,CaseVillage no,String str){
        return fabriqueChemin.creer(x,y,valeur,terr,n,ne,e,se,s,so,o,no,str);
    }

    public Case creerCaseVillage(int x,int y,String val){
        return fabriqueVillage.creer(val,x,y);
    }

    public void afficher(Joueur j){
        if(j==null) {
            for (int l = 0; l < cases[0].length; l++) {
                for (int c = 0; c < cases.length; c++) {
                    if (cases[c][l] == null) {
                        System.out.print("| * ");
                    } else if (cases[c][l] instanceof CaseVillage) {
                        System.out.print("| " + cases[c][l].valeur.charAt(0) + " ");
                    } else if (cases[c][l] instanceof CaseChemin) {
                        System.out.print("|" + cases[c][l].valeur);
                    }
                }
                System.out.print("|\n");
            }
        }else{
            int couleur=((PionJoueur) j.getPionJoueur()).getIdCouleur();
            for (int l = 0; l < cases[0].length; l++) {
                for (int c = 0; c < cases.length; c++) {
                    if (cases[c][l] == null) {
                        System.out.print("| * ");
                    } else if (cases[c][l] instanceof CaseVillage) {
                        if(pions[c][l][couleur+2]!=null){
                            System.out.print("| X ");
                        }else if(marqueurs[c][l][couleur]!=null){
                            System.out.print("| M ");
                        }else {
                            System.out.print("| " + cases[c][l].valeur.charAt(0) + " ");
                        }
                    } else if (cases[c][l] instanceof CaseChemin) {
                        System.out.print("|" + cases[c][l].valeur);
                    }
                }
                System.out.print("|\n");
            }
            System.out.print("\n");
        }
    }

    public Case[][] getCases(){
        return cases;
    }

    public void setCases(Case[][] ca){
        cases=ca;
    }

    public Pion[][][] getPions(){
        return pions;
    }

    public void setPions(Pion[][][] p){
        pions=p;
    }

    public void addMarqueur(int x,int y,MarqueurVillage m){
        if(m.getValeur().equals("Gris")){
            marqueurs[x][y][0]=m;
        }else if(m.getValeur().equals("Bleu")){
            marqueurs[x][y][1]=m;
        }else if(m.getValeur().equals("Violet")){
            marqueurs[x][y][2]=m;
        }else if(m.getValeur().equals("Vert")){
            marqueurs[x][y][3]=m;
        }else if(m.getValeur().equals("Jaune")){
            marqueurs[x][y][4]=m;
        }else if(m.getValeur().equals("Rouge")){
            marqueurs[x][y][5]=m;
        }
    }

    public MarqueurVillage getMarqueur(int x,int y,String couleur){
            if (couleur.equals("Gris")) {
                return marqueurs[x][y][0];
            } else if (couleur.equals("Bleu")) {
                return marqueurs[x][y][1];
            } else if (couleur.equals("Violet")) {
                return marqueurs[x][y][2];
            } else if (couleur.equals("Vert")) {
                return marqueurs[x][y][3];
            } else if (couleur.equals("Jaune")) {
                return marqueurs[x][y][4];
            } else {
                return marqueurs[x][y][5];
            }
    }

    public MarqueurVillage[][][] getMarqueur(){
        return marqueurs;
    }

    public void setMarqueurs(MarqueurVillage[][][] m){
        marqueurs=m;
    }

    public void setPions(int x,int y,PionJoueur p,PionMoyenDeTransport ptp,PionObstacle po){
        if(p != null){
            if(p.getValeur().equals("Gris")){
                pions[x][y][2]=p;
            }else if(p.getValeur().equals("Bleu")){
                pions[x][y][3]=p;
            }else if(p.getValeur().equals("Violet")){
                pions[x][y][4]=p;
            }else if(p.getValeur().equals("Vert")){
                pions[x][y][5]=p;
            }else if(p.getValeur().equals("Jaune")){
                pions[x][y][6]=p;
            }else if(p.getValeur().equals("Rouge")){
                pions[x][y][7]=p;
            }
        }if(ptp != null){
            pions[x][y][0]=ptp;
        }if(po != null){
            pions[x][y][1]=po;
            cases[x][y].setTransport(po);
        }
    }

    public void removePionJoueur(int x,int y,PionJoueur p){
        if(p.getValeur().equals("Gris")){
            pions[x][y][2]=null;
        }else if(p.getValeur().equals("Bleu")){
            pions[x][y][3]=null;
        }else if(p.getValeur().equals("Violet")){
            pions[x][y][4]=null;
        }else if(p.getValeur().equals("Vert")){
            pions[x][y][5]=null;
        }else if(p.getValeur().equals("Jaune")){
            pions[x][y][6]=null;
        }else if(p.getValeur().equals("Rouge")){
            pions[x][y][7]=null;
        }
    }

    public FabriqueVillage getFabriqueVillage() {
        return fabriqueVillage;
    }

    public void setFabriqueVillage(FabriqueVillage fabriqueVillage) {
        this.fabriqueVillage = fabriqueVillage;
    }

    public FabriqueChemin getFabriqueChemin() {
        return fabriqueChemin;
    }

    public void setFabriqueChemin(FabriqueChemin fabriqueChemin) {
        this.fabriqueChemin = fabriqueChemin;
    }

    public MarqueurVillage[][][] getMarqueurs() {
        return marqueurs;
    }
}
