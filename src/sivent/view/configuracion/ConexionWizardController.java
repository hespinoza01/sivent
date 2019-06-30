/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.configuracion;

import DB.conexion;
import DB.sqlite;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import sivent.utilities.Dragged;
import sivent.utilities.SicemWindow;
import sivent.utilities.validator;
import sivent.view.LoginController;

/**
 * FXML Controller class
 *
 * @author hespinoza
 */
public class ConexionWizardController extends SicemWindow implements Initializable {

    @FXML private Pane title;
    @FXML private Label titleLbl;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContraseña;
    @FXML private TextField database;
    @FXML private TextField localhost;
    @FXML private TextField port;
    @FXML private Button testconection;
    @FXML private Button cancelar;
    @FXML private Button finalizar;
    @FXML private Label testresult;
    
    boolean value = true;
    
    public ConexionWizardController(){ super("/sivent/view/configuracion/conexionWizard.fxml"); }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    private void inicia(){
        Dragged.set(title, titleLbl);
        database.setDisable(true);
        localhost.setDisable(true);
        port.setDisable(true);
        testresult.setVisible(false);
        finalizar.setDisable(true);
        
        
        txtUsuario.textProperty().addListener((evt, Old , New) -> {
            testresult.setVisible(false);
            finalizar.setDisable(true);
        });
        
        
        txtContraseña.textProperty().addListener((evt, Old , New) -> {
            testresult.setVisible(false);
            finalizar.setDisable(true);
        });
        
        
        //Evt cancelar button
        cancelar.setOnAction(e -> { System.exit(0); });
        
        
        //Evt testconection
        testconection.setOnAction(e -> {
            testresult.setVisible(true);
            try{
                sqlite.updateCredentials(txtUsuario.getText(), txtContraseña.getText());
                conexion c = new conexion();
                c.open();
                c.close();
                
                testresult.setText("Conexión realizada con éxito");
                validator.validate(testresult, false);
                finalizar.setDisable(false);
            }catch(Exception ex){
                testresult.setText("Conexión realizada sin éxito");
                validator.validate(testresult, true);
                finalizar.setDisable(true);
            }
        });
        
        
        //Evt finalizar button
        finalizar.setOnAction(e -> {
            new LoginController().show();
            close();
        });
    }

}
