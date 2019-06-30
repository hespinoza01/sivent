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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import objetos.categoria;
import sivent.utilities.Dragged;
import sivent.utilities.Popup;
import sivent.utilities.SicemWindow;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class CategoriaController extends SicemWindow implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtId;
    @FXML private TextArea txtDescripcion;
    @FXML private Button cancelar;
    @FXML private Button guardar;
    @FXML private CheckBox estadoValor;
    @FXML private Pane title;
    @FXML private Label titleLbl;
    
    String accionformulario="";
    DetalleCategoriaController detalle;
    conexion c = new conexion();
    
    
    public CategoriaController(){ 
        super("/sivent/view/administrar/categoria.fxml");
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
        if(accionformulario.equals("crear")) txtId.setText(Integer.toString((int) c.readerScalar("select count(*) + 1 from Categoria", Integer.class)));
        Dragged.set(title, titleLbl);
        
        
        //Evt Cancelar button
        cancelar.setOnAction(e -> { close(); });
        
        
        //Evt guardar button
        guardar.setOnAction(e -> {
            if(!txtNombre.getText().isEmpty()){
                categoria ca = new categoria();
                ca.setId(Integer.parseInt(txtId.getText()));
                ca.setNombre(txtNombre.getText());
                ca.setDescripcion(txtDescripcion.getText());
                ca.setEstado((estadoValor.isSelected()) ? 1 : 0);

                boolean transac = (accionformulario.equals("crear")) ? ca.Insertar() : ca.Editar();

                if(transac){ 
                    if(accionformulario.equals("editar")) detalle.setInfo(Integer.parseInt(txtId.getText())); 
                    close(); 
                }else Popup.error(
                        "Error innesperado", 
                        "Ocurrió un error al tratar de realizar la acción, por favor, intente nuevamente.");
            }else
                Popup.error(
                    "Error innesperado", 
                    "Se requiere un nombre en la categoría para poder registrarla.");
        });
    }
    
    
    public CategoriaController setDataView(int id, DetalleCategoriaController dc){
        accionformulario = "editar";
        detalle = dc;
        categoria data = new categoria().Detalle(id);

        if(data != null){
            txtId.setText(Integer.toString(data.getId()));
            txtNombre.setText(data.getNombre());
            txtDescripcion.setText(data.getDescripcion());
            estadoValor.setSelected((data.getEstado() == 1));
        }else{
            Popup.error(
                    "Error con la solicitud", 
                    "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
            close();
        }

        return this;
    }
    
}
