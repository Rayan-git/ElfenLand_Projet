package Controleur;

import org.postgresql.ds.PGSimpleDataSource;
import java.sql.*;
import java.util.ArrayList;
import Modele.*;

public class BDDControleur {

    private String serverName ;     //Adresse du serveur (ex: localhost)
    private String databaseName;    //Nom de la base de données (ex: postgres)
    private String user;            //Nom du compte utilisateur
    private String password;        //Mot de passe du compte utilisateur


    //Constructeur du BDDControleur

    public BDDControleur(String sn, String dn, String user, String pass){
        this.serverName = sn;
        this.databaseName = dn;
        this.user = user;
        this.password = pass;
    }


/* A venir
    public void Sauvegarder(...) throws SQLException{

    }
*/

/* A venir
    public Partie Charger(int idsave) throws SQLExeption{

    }
*/


    //Permet la récupération du nom de toutes les sauvegardes
    //pour en afficher la liste

    public ArrayList<String> getAllSauvegarde() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName(serverName);
        ds.setDatabaseName(databaseName);
        ds.setUser(user);
        ds.setPassword(password);
        Connection con = null;
        ArrayList<String> res = new ArrayList<>();
        try {
            con = ds.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select nom from Sauvegarde;");
            while (rs.next()) {
                res.add(rs.getString("nom"));
            }
        }
        finally {
            if (con!=null) {
                con.close();
            }
        }
        return res;
    }


    //Permet la récupération de l'id d'une sauvegarde depuis son nom

    public int getIdSauvegarde(String nom) throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName(serverName);
        ds.setDatabaseName(databaseName);
        ds.setUser(user);
        ds.setPassword(password);
        Connection con = null;
        int res = 0;
        try {
            con = ds.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select idSauvegarde from Sauvegarde where nom='"+nom+"';");
            while (rs.next()) {
                res = rs.getInt("idSauvegarde");
            }
        }
        finally {
            if (con!=null) {
                con.close();
            }
        }
        return res;
    }


    //Permet de récupérer la liste joueurs d'une partie donnée

    public ArrayList<String> getJoueurs(int idPartie) throws SQLException{
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName(serverName);
        ds.setDatabaseName(databaseName);
        ds.setUser(user);
        ds.setPassword(password);
        Connection con = null;
        ArrayList<String> res = new ArrayList<>();
        try {
            con = ds.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select nom from joueur\n" +
                    "join Pion on Pion.idJoueur = Joueur.idJoueur\n" +
                    "join Pioche on Pioche.idPioche = Pion.idPioche\n" +
                    "join Partie on Pioche.idPartie = Partie.idPartie\n" +
                    "where Partie.idPartie = '"+idPartie+"';");
            while (rs.next()) {
                res.add(rs.getString("nom"));
            }
        }
        finally {
            if (con!=null) {
                con.close();
            }
        }
        return res;
    }


    //Permet de récupérer l'id de la Partie à partir de l'id de la Sauvegarde

    public int getIdPartie(int idSauvegarde) throws SQLException{
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName(serverName);
        ds.setDatabaseName(databaseName);
        ds.setUser(user);
        ds.setPassword(password);
        Connection con = null;
        int res = 0;
        try {
            con = ds.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select idPartie from Sauvegarde where idSauvegarde='"+idSauvegarde+"';");
            while (rs.next()) {
                res = rs.getInt("idPartie");
            }
        }
        finally {
            if (con!=null) {
                con.close();
            }
        }
        return res;
    }


    //Permet de récupérer n'importe quel donnée de la Partie
    //depuis le nom de la table et de la colonne

    public ArrayList<String> getValueOf(String table,String colonne, int idPartie) throws SQLException{
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName(serverName);
        ds.setDatabaseName(databaseName);
        ds.setUser(user);
        ds.setPassword(password);
        Connection con = null;
        if (table.equals("Case")){
            table = "Cases";
        }
        ArrayList<String> res = new ArrayList<>();
        try {
            con = ds.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select "+table+"."+colonne+" as t from Partie\n" +
                    "join Plateau on Partie.idPartie = Plateau.idPartie\n" +
                    "join Cases on Cases.idPlateau = Plateau.idPlateau\n" +
                    "join Marqueur on Cases.idCases = Marqueur.village\n" +
                    "join Joueur on Marqueur.idJoueur = Joueur.idJoueur\n" +
                    "join Carte on Carte.idJoueur = Joueur.idJoueur\n" +
                    "join Pion on Pion.idJoueur = Joueur.idJoueur\n" +
                    "join Pioche on Pioche.idPioche = Pion.idPioche\n" +
                    "where Partie.idPartie = "+idPartie+"\n" +
                    "group by "+table+".id"+table+";");
            while (rs.next()) {
                res.add(rs.getString("t"));
            }
        }
        finally {
            if (con!=null) {
                con.close();
            }
        }
        return res;
    }


    //Permet la récupération du temps de jeu écoulé sur la sauvegarde

    public int getTempsJeu(int idSauvegarde) throws SQLException{
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName(serverName);
        ds.setDatabaseName(databaseName);
        ds.setUser(user);
        ds.setPassword(password);
        Connection con = null;
        int res = 0;
        try {
            con = ds.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select tempsJeu from Sauvegarde\n" +
                    "join DatePartie on Sauvegarde.idSauvegarde = DatePartie.idSauvegarde\n" +
                    "where Sauvegarde.idSauvegarde = "+idSauvegarde+";");
            while (rs.next()) {
                res = rs.getInt("tempsJeu");
            }
        }
        finally {
            if (con!=null) {
                con.close();
            }
        }
        return res;
    }


    //Permet de récupérer l'id d'un jouer à partir de son nom

    public int getIdJoueur(String nom,int idPartie) throws SQLException{
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName(serverName);
        ds.setDatabaseName(databaseName);
        ds.setUser(user);
        ds.setPassword(password);
        Connection con = null;
        int res = 0;
        try {
            con = ds.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select Joueur.idJoueur from Partie\n" +
                    "join Pioche on Pioche.idPartie = Partie.idPartie\n" +
                    "join Pion on Pion.idPioche = Pioche.idPioche\n" +
                    "join Joueur on Joueur.idJoueur = Pion.idJoueur\n" +
                    "where Joueur.nom = '"+nom+"' and Partie.idPartie = "+idPartie+"");
            while (rs.next()) {
                res = rs.getInt("idJoueur");
            }
        }
        finally {
            if (con!=null) {
                con.close();
            }
        }
        return res;
    }




}
