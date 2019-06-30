/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;

import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author hespinoza
 */
public final class Dragged {
    private Dragged(){}
    
    public static void set(Pane node1, Label node2){
        set(node1);
        set(node2);
    }
    
    public static void set(Pane node){
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        
        node.setOnMousePressed(e -> {
            Stage stage = (Stage) node.getScene().getWindow();
            xOffset.set(stage.getX() - e.getScreenX());
            yOffset.set(stage.getY() - e.getScreenY());
        });
        
        node.setOnMouseDragged(e -> {
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setX(e.getScreenX() + xOffset.get());
            stage.setY(e.getScreenY() + yOffset.get());
            node.setStyle("-fx-cursor: CLOSED_HAND;");
        });
        
        node.setOnMouseReleased(e -> node.setStyle("-fx-cursor: DEFAULT;"));
    }
    
    public static void set(Label node){
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        
        node.setOnMousePressed(e -> {
            Stage stage = (Stage) node.getScene().getWindow();
            xOffset.set(stage.getX() - e.getScreenX());
            yOffset.set(stage.getY() - e.getScreenY());
        });
        
        node.setOnMouseDragged(e -> {
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setX(e.getScreenX() + xOffset.get());
            stage.setY(e.getScreenY() + yOffset.get());
            node.setStyle("-fx-cursor: CLOSED_HAND;");
        });
        
        node.setOnMouseReleased(e -> node.setStyle("-fx-cursor: DEFAULT;"));
    }
}
