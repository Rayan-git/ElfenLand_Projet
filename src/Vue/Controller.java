package Vue;

import Controleur.PartieControleur;
import Modele.Partie;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Controller{
    Partie partie;
    @FXML Spinner spinner;
    @FXML
    GridPane gridPane= new GridPane();

    @FXML GridPane card = new GridPane();

    @FXML Button annul = new Button();

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }


    //Lancement de la seconde page
    public void pressButton(ActionEvent event) throws IOException {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        Stage primaryStage2 = new Stage();
        primaryStage2.setTitle("ElfenLand");
        primaryStage2.setScene(new Scene(root, 600, 400));
        primaryStage2.show();




    }

    //ferme le stage actuellement ouvert.
    public void annul(ActionEvent event) throws  IOException{
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage)source.getScene().getWindow();
        stage.close();

    }

    public void alert(ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lancement de la partie");
        alert.setHeaderText("Êtes vous sûr de vouloir lancer la partie ?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get()==ButtonType.OK) {
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage)source.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("sample3.fxml"));
            Stage primaryStage2 = new Stage();
            primaryStage2.setTitle("ElfenLand");
            primaryStage2.setScene(new Scene(root, 1920, 1080));
            primaryStage2.show();





            //Test d'un début de lancement de partie pour véirifer si les gets fonctionnent bien.
            Partie partie=new Partie((int)spinner.getValue(),0); // A MODIFIER POUR LE NOMBRE D'IA
            partie.setNbJoueur((int)spinner.getValue());
            System.out.println(partie.getNbJoueur());



            //Plateau vide puisqu'il reste à reproduire en dur le plateau sous forme d'une matrice
            //Test d'affichage rapide
            boolean fini=false;
            partie.distribuerObstacles();
            partie.piocherPionDeuxieme();

            for(int i=0;i<4;i++) {
                if (!fini) {
                    for (int j = 0; j < (int) spinner.getValue(); j++) {
                        if (partie.getJoueurs().get(j).getMarqueurs().size() == 20) {
                            fini = true;
                        }
                    }
                    //Phase de melange et distribution
                    System.out.println("Phase préparatoire !");
                    partie.melanger();
                    partie.distribuerCarte();
                    partie.distribuerPionPremier();

                    System.out.println("Phase de préparation des routes !");
                    String type;
                    int pion, pionDebut;
                    for (int j = 0; j < partie.getJoueurs().size(); j++) {
                        partie.getPlateau().afficher(partie.getJoueurs().get(j));
                        pionDebut = partie.getJoueurs().get(j).getMainPionVisible().size() + partie.getJoueurs().get(j).getMainPion().size();
                        boolean changement = false;
                        do {
                            type = partie.demandeTypePion(j);
                            if (type.equals("Visible") && partie.getJoueurs().get(j).getMainPionVisible().size() > 0) {
                                pion = partie.demandeIndicePionVisible(j);
                                changement = partie.getJoueurs().get(j).poserPion(type, pion, partie.demandeIndice(j, "X", 0, 45), partie.demandeIndice(j, "Y", 0, 32));
                                if (changement) {
                                    partie.getJoueurs().get(j).getMainPionVisible().remove(pion);
                                }
                            } else if (type.equals("Caché") && partie.getJoueurs().get(j).getMainPion().size() > 0) {
                                pion = partie.demandeIndicePion(j);
                                changement = partie.getJoueurs().get(j).poserPion(type, pion, partie.demandeIndice(j, "X", 0, 45), partie.demandeIndice(j, "Y", 0, 32));
                                if (changement) {
                                    partie.getJoueurs().get(j).getMainPion().remove(pion);
                                }
                            } else {
                                System.out.println("Impossible vous n'avez pas de pions de ce type !");
                            }
                        } while (!changement);
                    }
                }
                //Phase de Déplacement
                System.out.println("Phase de déplacement !");
                String direction;
                boolean arret;
                String commande;
                for (int k = 0; k < partie.getJoueurs().size(); k++) {
                    arret = false;
                    System.out.println("Au tour du joueur : " + partie.getJoueurs().get(k).getPionJoueur().getValeur());
                    partie.getPlateau().afficher(partie.getJoueurs().get(k));

                }}}

        else
            alert.close();

    }





    //quitter la partie
    public void alertQuit(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter la partie");
        alert.setHeaderText("Êtes vous sûr de vouloir quitter?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get()==ButtonType.OK){
            Platform.exit();}
        else
            alert.close();
    }

    //sauvegarde
    public void saver(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sauvegarde de la partie");
        alert.setHeaderText("Êtes vous sûr de vouloir sauvegarder la partie ?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get()==ButtonType.OK){

            PartieControleur partieControleur = new PartieControleur();
            partieControleur.sauvegarder(partie,"partie.ser");
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Sauvegarde de la partie");
            alert1.setHeaderText("Partie sauvegardée avec succès !");
            Optional<ButtonType> option2 = alert1.showAndWait();
            if (option2.get()==ButtonType.OK){

                alert1.close();
                alert.close();}
            else
                alert1.close();
        }


        else
            alert.close();
    }


    public void drag(DragEvent event){
        event.acceptTransferModes(TransferMode.ANY);
    }

    public void drop(DragEvent event){
        List<File> files = event.getDragboard().getFiles();

    }


}
