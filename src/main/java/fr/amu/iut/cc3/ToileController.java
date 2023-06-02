package fr.amu.iut.cc3;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.BLACK;

public class ToileController implements Initializable {

    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    private boolean tracer = false;

    @FXML
    private GridPane gridPane;
    @FXML
    private Label Error1;
    @FXML
    private Label Error2;
    @FXML
    private Pane toile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    int getXRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur + Math.cos(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    int getYRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur - Math.sin(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    @FXML
    private void dessinerPoint(){
        viderToile(); // on vide la toile pour mettre a jour dynamiqement les points

        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField ) {
                TextField textField = (TextField) node;
                if(!textField.getText().isEmpty() ) {
                    Double value = Double.parseDouble(textField.getText()); // récupération des valeurs
                    if (value >= 0 && value <= 20) { // test de la validité des valeurs si ok ajout du point
                        Circle nouveauPoint = new Circle(
                                getXRadarChart(value, Integer.parseInt(textField.getId())),
                                getYRadarChart(value, Integer.parseInt(textField.getId())),
                                5);
                        toile.getChildren().add(nouveauPoint);
                    } else { // si invalide on affiche le msg d'erreur
                        Error1.setOpacity(1);
                        Error2.setOpacity(1);
                    }
                }
            }
        }

        if(this.tracer) // on retrace les lignes si bouton tracer a déjà été cliquer
            tracerToile();
    }

    @FXML
    private void viderToile(){ // est appeler quand btn vider est clicker
        ArrayList<Node> toileContenue = new ArrayList<>();
        for (Node node : toile.getChildren()) { // récupération des points et lignes
            toileContenue.add(node);
        }
        toile.getChildren().removeAll(toileContenue); // suppréssion des points et lignes
        Error1.setOpacity(0); // on cache le msg d'erreur
        Error2.setOpacity(0);
    }

    @FXML
    private void tracerToile(){ // appeler lors du click du btn tracer
        try {
            ArrayList<Circle> toilePoint = new ArrayList<>();
            for (Node node : toile.getChildren()) { // on récup les points
                if (node instanceof Circle) {
                    toilePoint.add((Circle) node);
                }
            }
            // cas particulier des points de début et fin de liste gérer a part
            Line line = new Line(toilePoint.get(0).getCenterX(), toilePoint.get(0).getCenterY(),
                    toilePoint.get(toilePoint.size() - 1).getCenterX(), toilePoint.get(toilePoint.size() - 1).getCenterY());
            line.setStyle("-fx-stroke: BLACK");
            toile.getChildren().add(line);
            // création et ajout des lignes
            for (int i = 0; i < toilePoint.size() - 1; ++i) {
                Line nouvelLine = new Line(toilePoint.get(i).getCenterX(), toilePoint.get(i).getCenterY(),
                        toilePoint.get(i + 1).getCenterX(), toilePoint.get(i + 1).getCenterY());
                nouvelLine.setStyle("-fx-stroke: BLACK");
                toile.getChildren().add(nouvelLine);
            }
            this.tracer = true;
        } catch (IllegalAccessError e){
            //Pas de point ou pas assez de ppoint
        }
    }

}
