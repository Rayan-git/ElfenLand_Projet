package Modele;

import java.io.Serializable;
import java.util.ArrayList;

public class FabriqueCarteResume implements FabriqueCarte, Serializable {
    private static FabriqueCarteResume instance;

    private FabriqueCarteResume(){
    }

    public CarteResume creer(){
        //Code fixe pour les données des différentes cartes du jeu. Il ne change pas donc la taille du code n'est pas importante
        CarteResume cr=new CarteResume();
        ArrayList<String> env=new ArrayList<>();
        ArrayList<String> type=new ArrayList<>();
        Integer[][] list=new Integer[8][5];

        env.add("Plaine");
        env.add("Forêt");
        env.add("Désert");
        env.add("Montagne");
        env.add("Mer");

        type.add("Cochon Géant");
        type.add("Machine Elfe");
        type.add("Nuage Magique");
        type.add("Licorne");
        type.add("Machine Troll");
        type.add("Dragon");
        type.add("Radeau courant");
        type.add("Radeau contre courant");

        list[0][0]=1;
        list[0][1]=1;
        list[1][0]=1;
        list[1][1]=1;
        list[1][3]=2;
        list[2][0]=2;
        list[2][1]=2;
        list[2][3]=1;
        list[3][1]=1;
        list[3][2]=2;
        list[3][3]=1;
        list[4][0]=1;
        list[4][1]=2;
        list[4][2]=2;
        list[4][3]=2;
        list[5][0]=1;
        list[5][1]=2;
        list[5][2]=1;
        list[5][3]=1;
        list[6][4]=1;
        list[7][4]=2;

        for(int x=0;x<list.length;x++){
            for(int y=0;y<list[0].length;y++){
                if(list[x][y]==null){
                    list[x][y]=0;
                }
            }
        }

        cr.setEnvironnement(env);
        cr.setTypeTransport(type);
        cr.setList(list);
        return cr;
    }

    public static FabriqueCarteResume getInstance(){
        if(instance==null){
            instance=new FabriqueCarteResume();
        }
        return instance;
    }

    public static void setInstance(FabriqueCarteResume instance) {
        FabriqueCarteResume.instance = instance;
    }
}
