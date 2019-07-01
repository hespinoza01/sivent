/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.QModel;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author hespinoza
 */
public class QmodelInvController implements Initializable {
    @FXML private TextField txtDemanda;
    @FXML private TextField txtPlazo;
    @FXML private TextField txtDiasHabiles;
    @FXML private TextField txtCostoPedir;
    @FXML private TextField txtCostoMantenimiento;
    @FXML private TextField txtDesviacionEstandar;
    @FXML private Spinner<Integer> txtProbAgoteInv;
    @FXML private Button btnCalcular;
    @FXML private Button btnLimpiar;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtROP;
    @FXML private TextField txtZ;
    @FXML private TextField txtDesviacionEstandarDemanda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCalcular.setOnAction(e -> {});
        
        btnLimpiar.setOnAction(e -> {});
    }    
    
}
