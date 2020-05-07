package Modele;

import java.io.Serializable;

public class PionJoueur extends Pion implements Serializable {
    public PionJoueur(){
        super();
        super.x=35;
        super.y=18;
    }

    public int getIdCouleur() {
        String st = super.valeur;
        if (st.equals("Gris")) {
            return 0;
        } else if (st.equals("Bleu")) {
            return 1;
        } else if (st.equals("Violet")) {
            return 2;
        } else if (st.equals("Vert")) {
            return 3;
        } else if (st.equals("Jaune")) {
            return 4;
        } else {
            return 5;
        }
    }

}
