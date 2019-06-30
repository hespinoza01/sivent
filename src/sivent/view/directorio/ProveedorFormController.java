/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.directorio;

import DB.conexion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import objetos.proveedor;
import sivent.utilities.Dragged;
import sivent.utilities.Items;
import sivent.utilities.Popup;
import sivent.utilities.SicemWindow;

/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class ProveedorFormController extends SicemWindow implements Initializable {

    @FXML private Button cancelar;
    @FXML private Button guardar;
    @FXML private TextField txtNombre;
    @FXML private TextField txtID;
    @FXML private TextField txtNombreContacto;
    @FXML private TextField txtTituloContacto;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTel;
    @FXML private TextArea txtDireccion;
    @FXML private ComboBox<String> txtCiudad;
    @FXML private CheckBox EstadoValue;
    @FXML private Pane title;
    @FXML private Label titleLbl;
    
    String accionformulario;
    int _id;
    conexion c = new conexion();
    DetalleProveedorController detalle;
    
    
    public ProveedorFormController(){
        super("/sivent/view/directorio/proveedorForm.fxml");
        accionformulario = "crear"; 
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    public void inicia(){
        guardar.setText((accionformulario.equals("crear")) ? "Guardar" : "Actualizar");
        txtCiudad.setItems(Items.ciudad());
        txtCiudad.getSelectionModel().select(0);
        Dragged.set(title, titleLbl);
        
        if(accionformulario.equals("crear")) loadID();
        
        
        // Evento botón cancelar
        cancelar.setOnAction((e) -> { close(); });
        
        
        // Evento botón guardar
        guardar.setOnAction((e) -> {
            proveedor p = new proveedor();
            p.setId(Integer.parseInt(txtID.getText()));
            p.setNombre(txtNombre.getText());
            p.setNombreContacto(txtNombreContacto.getText());
            p.setTituloContacto(txtTituloContacto.getText());
            p.setDomicilio(txtDireccion.getText());
            p.setCiudad(txtCiudad.getSelectionModel().getSelectedItem());
            p.setEmail(txtEmail.getText());
            p.setTelefono(txtTel.getText());
            p.setEstado((EstadoValue.isSelected()) ? 1 : 0);

            boolean transac = (accionformulario.equals("crear")) ? p.Insertar() : p.Editar();

            if(transac){ 
                if(accionformulario.equals("editar")) detalle.setInfo(Integer.parseInt(txtID.getText())); 
                close(); 
            }else Popup.error(
                    "Error innesperado", 
                    "Ocurrió un error al tratar de realizar la acción, por favor, intente nuevamente.");
        });
    }
    
    
    public ProveedorFormController setDataView(int id, DetalleProveedorController dp){
        accionformulario = "editar";
        detalle = dp;
        proveedor p = new proveedor().Detalle(id);
        if(p != null){
            txtID.setText(Integer.toString(p.getId()));
            txtNombre.setText(p.getNombre());
            txtNombreContacto.setText(p.getNombreContacto());
            txtTituloContacto.setText(p.getTituloContacto());
            txtCiudad.getSelectionModel().select(p.getCiudad());
            txtEmail.setText(p.getEmail());
            txtTel.setText(p.getTelefono());
            txtDireccion.setText(p.getDomicilio());
            EstadoValue.setSelected(p.getEstado() == 1);
        }else{
            Popup.error(
                    "Error con la solicitud", 
                    "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
            close();
        }
        
        return this;
    }
    
    public void loadID(){
        int value = (int) c.readerScalar("select count(*) + 1 from Proveedor", Integer.class);
        txtID.setText(Integer.toString(value));
    }
    
}
