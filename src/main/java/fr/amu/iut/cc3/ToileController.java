package fr.amu.iut.cc3;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ToileController implements Initializable {

    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField comp1;

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
        /*Circle nouveauPoint =
                new Circle(getXRadarChart(Double.parseDouble(comp1.getText()),1),
                        getYRadarChart(Double.parseDouble(comp1.getText()),1),5);
        toile.getChildren().add(nouveauPoint);*/

        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                String cat = textField.getId();
                int pop = Integer.parseInt(textField.getText());
                try {
                    Circle nouveauPoint =
                            new Circle(getXRadarChart(Double.parseDouble(textField.getText()),
                                            Integer.parseInt(textField.getId())),
                                       getYRadarChart(Double.parseDouble(textField.getText()),
                                            Integer.parseInt(textField.getId())),5);
                    toile.getChildren().add(nouveauPoint);
                } catch (NumberFormatException e) {
                    // La valeur saisie n'est pas un entier valide
                }
            }
        }
    }
}
