/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.administrar;

import DB.conexion;
import DB.sqlite;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import objetos.permisos;
import objetos.usuario;
import sivent.utilities.Popup;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class DetalleUsuarioController implements Initializable {

    @FXML private ImageView perfil;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private Button editar;
    @FXML private Label EstadoValue;
    @FXML private Label labelFechaCreacion;
    @FXML private Label labelFechaModificacion;
    @FXML private TextField nomUser;
    @FXML private AnchorPane permisosPanel;
    @FXML private CheckBox directorioConsultar;
    @FXML private CheckBox directorioEdicion;
    @FXML private CheckBox directorioCreacion;
    @FXML private CheckBox operacionesConsulta;
    @FXML private CheckBox operacionesEdicion;
    @FXML private CheckBox operacionesCreacion;
    @FXML private CheckBox productosConsultar;
    @FXML private CheckBox productosEdicion;
    @FXML private CheckBox productosCreacion;
    @FXML private CheckBox permisoReportes;
    @FXML private CheckBox permisoAccesoTotal;
    @FXML private TabPane navegacion;
    @FXML private Tab informacion;
    @FXML private Tab permisos;
    
    conexion c = new conexion();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    private void inicia(){
        labelFechaModificacion.setText("");
        labelFechaCreacion.setText("");
        EstadoValue.setText("");
        editar.setVisible(false);
        
        
        //Evt navegacion tab 
        navegacion.getSelectionModel().selectedItemProperty().addListener((evt, Old, New) -> {
            permisosPanel.setVisible(New.getId().equals("permisos"));
        });
        
        
        //Evt editar button
        editar.setOnAction(e -> {
            new UsuarioFormController()
                    .load(new UsuarioFormController())
                    .setDataView(nomUser.getText(), this)
                    .showAfterLoad();
        });
    }
    
    
    public void setInfo(String id){
        usuario data = new usuario().Detalle(id);

        if(data != null){
            editar.setVisible(true);
            
            nomUser.setText(data.getUsername());
            txtNombre.setText(data.getNombre());
            txtApellido.setText(data.getApellido());
            perfil.setImage(data.getFoto());
            EstadoValue.setText((data.getEstado() == 1) ? "Habilitado" : "Deshabilitado");
            labelFechaCreacion.setText(data.getFechaCreacion());
            labelFechaModificacion.setText(data.getFechaModificacion());
            getPermisos(data.getUsername());
            
            if(data.getUsername().equals("admin") && !sqlite.getUser().equals("admin")) editar.setVisible(false);
        }else
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
    }
    
    
    private void getPermisos(String id){
        permisos p = new permisos().get(id);
        
        directorioConsultar.setSelected(value(p.getDconsultar()));
        directorioEdicion.setSelected(value(p.getDeditar()));
        directorioCreacion.setSelected(value(p.getDcrear()));

        operacionesConsulta.setSelected(value(p.getOconsultar()));
        operacionesEdicion.setSelected(value(p.getOeditar()));
        operacionesCreacion.setSelected(value(p.getOcrear()));

        productosConsultar.setSelected(value(p.getPconsultar()));
        productosEdicion.setSelected(value(p.getPeditar()));
        productosCreacion.setSelected(value(p.getPcrear()));

        permisoReportes.setSelected(value(p.getReportes()));
        permisoAccesoTotal.setSelected(value(p.getAccesototal()));
    }
    
    
    private boolean value(int node){
        return (node == 1);
    }
    
}
