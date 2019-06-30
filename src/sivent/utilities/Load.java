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
public final class Load {
    private Load(){}
    
    public static FXMLLoader Loader(String ruta) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Load.class.getResource(ruta));
        
        return loader;
    }
    
    public static Stage Form(Scene value){
        value.setFill(Color.TRANSPARENT);
        
        Stage stage = new Stage();
        stage.setScene(value);
        stage.getIcons().add(new Image("/sivent/images/favicon.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        
        return stage;
    }
    
}
