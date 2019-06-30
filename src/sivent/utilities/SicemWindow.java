/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author hespinoza
 */
public abstract class SicemWindow {
    private final String FXMLLocation;
    public static Stage stage;
    
    public String getFXMLLocation(){ return this.FXMLLocation; }
    
    public SicemWindow(String _FXMLLocation){
        this.FXMLLocation = _FXMLLocation;
        stage = new Stage();
    }
    
    public void show(){
        try{
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(this.FXMLLocation)));
            scene.setFill(Color.TRANSPARENT);
            
            stage.setScene(scene);
            stage.getIcons().add(new Image("/sivent/images/favicon.png"));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        }catch(IOException e){
            System.err.println("Error in show window: "+e.toString());
        }
    }
    
    public void showAndWait(){
        try{
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(this.FXMLLocation)));
            scene.setFill(Color.TRANSPARENT);
            
            stage.setScene(scene);
            stage.getIcons().add(new Image("/sivent/images/favicon.png"));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.showAndWait();
        }catch(IOException e){
            System.err.println("Error in show window: "+e.toString());
        }
    }
    
    public <T> T load(T classObject){
        T form;
        try{
            FXMLLoader loader = Load.Loader(this.FXMLLocation);
            Scene scene= new Scene(loader.load());
            form = loader.getController();

            scene.setFill(Color.TRANSPARENT);
            
            stage.setScene(scene);
            stage.getIcons().add(new Image("/sivent/images/favicon.png"));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);
        }catch(IOException ex){
            System.out.println("Error SicemWindow Load: "+ex.toString());
            form = null;
        }
        
        return form;
    }
    
    public void showAfterLoad(){
        stage.showAndWait();
    }
    
    public void showAfterLoadSingle(){
        stage.show();
    }
    
    public void close(){
        stage.close();
    }
}
