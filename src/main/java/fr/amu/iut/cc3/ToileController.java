package fr.amu.iut.cc3;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        /*ArrayList<Node> toilePoint = new ArrayList<>();
        for (Node node : toile.getChildren()) {
            if (node instanceof Circle) {
                toilePoint.add(node);
            }
        }
        toile.getChildren().removeAll(toilePoint);*/
        viderToile();

        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField ) {
                TextField textField = (TextField) node;
                if(!textField.getText().isEmpty() ) {
                    Double value = Double.parseDouble(textField.getText());
                    if (value >= 0 && value <= 20) {
                        Circle nouveauPoint = new Circle(
                                getXRadarChart(value, Integer.parseInt(textField.getId())),
                                getYRadarChart(value, Integer.parseInt(textField.getId())),
                                5);
                        toile.getChildren().add(nouveauPoint);
                    } else {
                        Error1.setOpacity(1);
                        Error2.setOpacity(1);
                    }
                }
            }
        }

        if(this.tracer)
            tracerToile();
    }

    @FXML
    private void viderToile(){
        ArrayList<Node> toileContenue = new ArrayList<>();
        for (Node node : toile.getChildren()) {
            toileContenue.add(node);
        }
        toile.getChildren().removeAll(toileContenue);
        Error1.setOpacity(0);
        Error2.setOpacity(0);
    }

    @FXML
    private void tracerToile(){
        this.tracer = true;
        ArrayList<Circle> toilePoint = new ArrayList<>();
        for (Node node : toile.getChildren()) {
            if (node instanceof Circle) {
                toilePoint.add((Circle)node);
            }
        }
        Line line = new Line(toilePoint.get(0).getCenterX(),toilePoint.get(0).getCenterY(),
                toilePoint.get(toilePoint.size()-1).getCenterX(),toilePoint.get(toilePoint.size()-1).getCenterY());
        toile.getChildren().add(line);
        for (int i = 0 ; i < toilePoint.size()-1 ; ++i) {
            Line nouvelLine = new Line(toilePoint.get(i).getCenterX(),toilePoint.get(i).getCenterY(),
                    toilePoint.get(i+1).getCenterX(),toilePoint.get(i+1).getCenterY());
            toile.getChildren().add(nouvelLine);
        }
    }

}
