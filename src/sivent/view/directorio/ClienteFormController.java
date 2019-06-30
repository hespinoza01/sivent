/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.directorio;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
import objetos.cliente;
import sivent.utilities.Dragged;
import sivent.utilities.Items;
import sivent.utilities.Popup;
import sivent.utilities.SicemWindow;

/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class ClienteFormController extends SicemWindow implements Initializable {

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
    
    String accionformulario = "";
    String _id = "";
    LocalTime fat = LocalTime.now();
    LocalDate fad = LocalDate.now();
    DetalleClienteController detalle;
    
    
    public ClienteFormController(){ 
        super("/sivent/view/directorio/clienteForm.fxml");
        accionformulario="crear"; 
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    public void inicia(){
        Dragged.set(title, titleLbl);
        guardar.setText((accionformulario.equals("crear")) ? "Guardar" : "Actualizar");
        txtCiudad.setItems(Items.ciudad());
        if(accionformulario.equals("crear")){
            createID();
            txtCiudad.getSelectionModel().select(0);
        }
        
        
        // Evt cambio de texto txtNombre
        txtNombre.textProperty().addListener((evt, Old, New) -> { if(accionformulario.equals("crear")) createID(); });
        
        
        // Evt botón cancelar
        cancelar.setOnAction(e -> { close(); });

        
        // Evt boton guadar
        guardar.setOnAction(e -> {
            cliente c = new cliente();
            c.setId(txtID.getText());
            c.setNombre(txtNombre.getText());
            c.setNombreContacto(txtNombreContacto.getText());
            c.setTituloContacto(txtTituloContacto.getText());
            c.setDomicilio(txtDireccion.getText());
            c.setCiudad(txtCiudad.getSelectionModel().getSelectedItem());
            c.setEmail(txtEmail.getText());
            c.setTelefono(txtTel.getText());
            c.setEstado((EstadoValue.isSelected()) ? 1 : 0);

            boolean transac = (accionformulario.equals("crear")) ? c.Insertar() : c.Editar();

            if(transac){ 
                if(accionformulario.equals("editar")) detalle.setInfo(txtID.getText()); 
                close(); 
            }else Popup.error(
                    "Error innesperado", 
                    "Ocurrió un error al tratar de realizar la acción, por favor, intente nuevamente.");
        });
    }
    
    
    public ClienteFormController setDataView(String id, DetalleClienteController dc){
        accionformulario = "editar";
        detalle = dc;
        cliente data = new cliente().Detalle(id);

        if(data != null){
            txtID.setText(data.getId());
            txtNombre.setText(data.getNombre());
            txtNombreContacto.setText(data.getNombreContacto());
            txtTituloContacto.setText(data.getTituloContacto());
            txtEmail.setText(data.getEmail());
            txtTel.setText(data.getTelefono());
            txtDireccion.setText(data.getDomicilio());
            txtCiudad.getSelectionModel().select(data.getCiudad());
            EstadoValue.setSelected(data.getEstado()==1);
        }else {
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente."); 
            close();
        }
        
        return this;
    }
    
    
    private void createID(){
        String text = (txtNombre.getText().length() > 5) ? txtNombre.getText().substring(0,5).toLowerCase() : txtNombre.getText().toLowerCase();
        text += "-"+fad.getDayOfMonth() +""+ fat.getHour() +""+ fat.getMinute();
        txtID.setText(text);
    }
    
}
