/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 *
 * @author espinoza
 */
public class dialogos {
    Alert alerta;
    tipo t;
    
    public dialogos(String titulo, String content, tipo tp){
        this.t = tp;
        
        switch(tp){
            case confir:
                this.alerta = new Alert(AlertType.CONFIRMATION);
                break; 
                
            case error:
                this.alerta = new Alert(AlertType.ERROR);
                break;
                
            case info:
                this.alerta = new Alert(AlertType.INFORMATION);
                break;
                
            case warning:
                this.alerta = new Alert(AlertType.WARNING);
                break;
        }
        
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(content);
        alerta.initStyle(StageStyle.UNDECORATED);
    }
    
    public enum tipo{
        confir, error, info, warning;
    }
    
    public boolean show(){
        boolean respuesta = false;
        
        if(this.t == tipo.confir)
            respuesta = (alerta.showAndWait().get() == ButtonType.OK) ? true : false;
        else
            alerta.showAndWait();
        
        return respuesta;
    }
    
}
