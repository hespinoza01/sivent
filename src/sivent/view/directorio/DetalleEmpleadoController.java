/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.directorio;

import DB.conexion;
import DB.sqlite;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import objetos.empleado;
import objetos.permisos;
import sivent.utilities.Load;
import sivent.utilities.Popup;
import sivent.utilities.rounded;

/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class DetalleEmpleadoController implements Initializable {

    @FXML private ImageView foto;
    @FXML private TextField txtID;
    @FXML private TextField txtNombre;
    @FXML private TabPane contentTabs;
    @FXML private Tab infoPersonal;
    @FXML private Tab infoLaboral;
    @FXML private Button editar;
    @FXML private TextField txtGenero;
    @FXML private AnchorPane contentInfoPersonal;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTel;
    @FXML private TextField txtCedula;
    @FXML private TextArea txtDireccion;
    @FXML private TextField txtEstadoCivil;
    @FXML private TextField txtCiudad;
    @FXML private TextField fechaNacimiento;
    @FXML private AnchorPane contentInfoLaboral;
    @FXML private TextField txtDepartamento;
    @FXML private TextField txtTituloLaboral;
    @FXML private TextField txtReportarA;
    @FXML private TextArea txtObservaciones;
    @FXML private TextField txtFechaContratacion;
    @FXML private Label EstadoValue;
    @FXML private Label labelFechaModificacion;
    
    conexion c = new conexion();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    private void inicia(){
        editar.setVisible(false);
        EstadoValue.setText("");
        labelFechaModificacion.setText("");
        rounded.roundImageView(foto, new Image("/sivent/images/avatar.png"));
        
        
        // Evt listener cambio de tab
        contentTabs.getSelectionModel().selectedItemProperty().addListener((evt, Old, New) -> {
            if(New.getId().equalsIgnoreCase("infoLaboral")){
                contentInfoLaboral.setVisible(true);
                contentInfoPersonal.setVisible(false);
            }else{
                contentInfoLaboral.setVisible(false);
                contentInfoPersonal.setVisible(true);
            }
        });
        
        
        // Evt Editar button
        editar.setOnAction(e -> {
            new EmpleadoFormController()
                    .load(new EmpleadoFormController())
                    .setDataView(Integer.parseInt(txtID.getText()), this)
                    .showAfterLoad();
        });
    }
    
    
    public void setInfo(int id){
        empleado data = new empleado().Detalle(id);
        permisos p = new permisos().get(sqlite.getUser());

        if(data != null){
            editar.setVisible(true);

            txtID.setText(Integer.toString(data.getId()));
            txtNombre.setText(data.getNombres()+" "+data.getApellidos());
            setDepartamento(data.getDepartamentoId());
            txtTituloLaboral.setText(data.getTituloLaboral());
            fechaNacimiento.setText(data.getFechaNacimiento().toString());
            txtFechaContratacion.setText(data.getFechaContratacion().toString());
            txtEstadoCivil.setText((data.getEstadoCivil()==0) ? "Soltero" : "Casado");
            txtGenero.setText((data.getGenero()==1) ? "Masculino" : "Femenino");
            txtDireccion.setText(data.getDomicilio());
            txtCiudad.setText(data.getCiudad());
            txtTel.setText(data.getTelefono());
            txtCedula.setText(data.getCedula());
            txtEmail.setText(data.getEmail());
            txtObservaciones.setText(data.getObservaciones());
            setReportarA(data.getReportarA());
            foto.setImage(data.getFoto());
            labelFechaModificacion.setText(data.getFechaModificacion());
            EstadoValue.setText((data.getEstado()==1) ? "Habilitado" : "Deshabilitado");
            
            if(p.getDeditar() == 0) editar.setVisible(false);
        }else
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
    }
    
    private void setDepartamento(int id){
        Object value = c.readerScalar("select Nombre from Departamentos where ID="+id, String.class);
        if (value != null)
            txtDepartamento.setText((String)value);
    }
    
    private void setReportarA(int id){
        Object value = c.readerScalar("select Nombres from Empleado where ID="+ id, String.class);
        if (value != null)
            txtReportarA.setText((String)value);
    }
    
}
