package Controleur;

import Modele.Partie;

import java.io.*;

public class PartieControleur {

    public void sauvegarder(Partie p,String s) {
        ObjectOutputStream oos = null;
        try {
            final FileOutputStream fichier = new FileOutputStream(s);
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(p);
            oos.flush();
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Partie reader(String s){
        ObjectInputStream ois = null;
        Partie partie=null;
        try {
            final FileInputStream fichier = new FileInputStream(s);
            ois = new ObjectInputStream(fichier);
             partie= (Partie) ois.readObject();
            //System.out.println("Nom : " + personne.getNom());
            //System.out.println("List : " + personne.getList());
            //partie.fabriqueCarteResume.creer().afficher();
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
        return partie;
    }



}
