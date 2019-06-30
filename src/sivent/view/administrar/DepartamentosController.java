/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.administrar;

import DB.conexion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import objetos.departamento;
import sivent.utilities.Dragged;
import sivent.utilities.Popup;
import sivent.utilities.SicemWindow;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class DepartamentosController extends SicemWindow implements Initializable {

    @FXML private TextField txtID;
    @FXML private TextField txtNombre;
    @FXML private TextField txtNombreGrupo;
    @FXML private CheckBox estadoValue;
    @FXML private Button cancelar;
    @FXML private Button guardar;
    @FXML private Pane title;
    @FXML private Label titleLbl;
    
    String accionformulario = "";
    DetalleDepartamentosController detalle;
    conexion c = new conexion();
    
    
    public DepartamentosController(){ 
        super("/sivent/view/administrar/departamentos.fxml");
        accionformulario = "crear"; 
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    private void inicia(){
        guardar.setText((accionformulario.equals("crear")) ? "Guardar" : "Actualizar");
        if(accionformulario.equals("crear")) txtID.setText(Integer.toString((int) c.readerScalar("select count(*) + 1 from Departamentos", Integer.class)));
        Dragged.set(title, titleLbl);
        txtNombre.requestFocus();
        
        
        //Evt cancelar button
        cancelar.setOnAction(e -> { close(); });
        
        
        //Evt guardar button
        guardar.setOnAction(e -> {
            if(!txtNombre.getText().isEmpty()){
                boolean transac = false;
                departamento d = new departamento();
                d.setId(Integer.parseInt(txtID.getText()));
                d.setNombre(txtNombre.getText());
                d.setNombreGrupo(txtNombreGrupo.getText());
                d.setEstado((estadoValue.isSelected()) ? 1 : 0);

                if (accionformulario.equals("crear")) transac = d.Insertar();
                else transac = d.Editar();

                if(transac){ 
                    if(accionformulario.equals("editar")) detalle.setInfo(Integer.parseInt(txtID.getText())); 
                    close(); 
                }else Popup.error(
                        "Error innesperado", 
                        "Ocurrió un error al tratar de realizar la acción, por favor, intente nuevamente.");
            }else
                Popup.error(
                    "Error innesperado", 
                    "Se requiere un nombre en el departamento para poder registrarlo.");
        });
    }
    
    
    public DepartamentosController setDataView(int id, DetalleDepartamentosController dd){
        accionformulario = "editar";
        detalle = dd;
        departamento data = new departamento().Detalle(id);

        if(data != null){
            txtID.setText(Integer.toString(data.getId()));
            txtNombre.setText(data.getNombre());
            txtNombreGrupo.setText(data.getNombreGrupo());
            estadoValue.setSelected((data.getEstado()==1) ? true : false);
        }else{
            Popup.error(
                    "Error con la solicitud", 
                    "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
            close();
        }
        
        return this;
    }
    
}
