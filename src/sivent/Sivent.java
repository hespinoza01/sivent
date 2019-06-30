/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent;

import DB.conexion;
import javafx.application.Application;
import javafx.stage.Stage;
import sivent.view.LoginController;
import sivent.view.configuracion.ConexionWizardController;

/**
 *
 * @author hespinoza
 */
public class Sivent extends Application {
    
    private void inicia(){
        try{
            conexion c = new conexion();
            c.open();
            new LoginController().show();
            c.close();
        }catch(Exception ex){
            new ConexionWizardController().show();
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        inicia();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
