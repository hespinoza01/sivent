/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.directorio;

import DB.sqlite;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import objetos.cliente;
import objetos.permisos;
import sivent.utilities.Popup;

/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class DetalleClienteController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtID;
    @FXML private TextField txtNombreContacto;
    @FXML private TextField txtTituloContacto;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTel;
    @FXML private TextArea txtDireccion;
    @FXML private Button editar;
    @FXML private TextField txtCiudad;
    @FXML private Label EstadoValue;
    @FXML private Label labelFechaModificacion;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    public void inicia(){
        EstadoValue.setText("");
        labelFechaModificacion.setText("");
        editar.setVisible(false);
        
        
        // Evt botón editar
        editar.setOnAction(e -> {
            new ClienteFormController()
                    .load(new ClienteFormController())
                    .setDataView(txtID.getText(), this).showAfterLoad();
        });
    }
    
    
    public void setInfo(String id){
        cliente data = new cliente().Detalle(id);
        permisos p = new permisos().get(sqlite.getUser());

        if(data != null){
            editar.setVisible(true);
            
            txtID.setText(data.getId());
            txtNombre.setText(data.getNombre());
            txtNombreContacto.setText(data.getNombreContacto());
            txtTituloContacto.setText(data.getTituloContacto());
            txtDireccion.setText(data.getDomicilio());
            txtCiudad.setText(data.getCiudad());
            txtTel.setText(data.getTelefono());
            txtEmail.setText(data.getEmail());
            EstadoValue.setText((data.getEstado() == 1) ? "Habilitado" : "Deshabilitado");
            labelFechaModificacion.setText(data.getFechaModificacion());
            
            if(p.getDeditar() == 0) editar.setVisible(false);
        }else{
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
        }
    }
    
}
