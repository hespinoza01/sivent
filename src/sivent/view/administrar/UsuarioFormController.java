/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.administrar;

import DB.conexion;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import objetos.permisos;
import objetos.usuario;
import sivent.utilities.Dragged;
import sivent.utilities.Popup;
import sivent.utilities.SicemWindow;
import sivent.utilities.changeNode;
import sivent.utilities.chooseImage;
import sivent.utilities.rounded;
import sivent.view.home.HomeController;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class UsuarioFormController extends SicemWindow implements Initializable {

    @FXML private Pane title;
    @FXML private Label titleLbl;
    @FXML private ImageView perfil;
    @FXML private Button editPerfilPicture;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private PasswordField pass;
    @FXML private PasswordField confirPass;
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
    @FXML private CheckBox estadoValue;
    @FXML private Button cancelar;
    @FXML private Button aceptar;
    @FXML private TabPane navegacion;
    @FXML private Tab informacion;
    @FXML private Tab permisos;
    
    String accionformulario = "", from="";
    String __password__ = null;
    DetalleUsuarioController detalle;
    HomeController detalleHome;
    conexion c = new conexion();
    
    
    public UsuarioFormController(){ 
        super("/sivent/view/administrar/usuarioForm.fxml");
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
        Dragged.set(title, titleLbl);
        aceptar.setText((accionformulario.equals("crear")) ? "Guardar" : "Actualizar");
        permisosPanel.setVisible(false);
        if(accionformulario.equals("crear")) rounded.roundImageView(perfil, new Image("/sivent/images/avatar.png"));
        changeNode.set(txtNombre, txtApellido);
        changeNode.set(txtApellido, pass);
        changeNode.set(pass, confirPass);
        
        
        //Evt cancelar button
        cancelar.setOnAction(e -> { close(); });
        
        
        //Evt txtNombre xhange text listener
        txtNombre.textProperty().addListener((evt, Old, New) -> {
            if(!txtNombre.getText().isEmpty() && txtNombre.getText().length()==1)
                txtNombre.setText(txtNombre.getText().toUpperCase());
            
            if(accionformulario.equals("crear")) username();
        });
        
        
        //Evt txtApellido xhange text listener
        txtApellido.textProperty().addListener((evt, Old, New) -> {
            if(!txtApellido.getText().isEmpty() && txtApellido.getText().length()==1)
                txtApellido.setText(txtApellido.getText().toUpperCase());
            
            if(accionformulario.equals("crear")) username();
        });
        
        
        //Evt navegacion tab 
        navegacion.getSelectionModel().selectedItemProperty().addListener((evt, Old, New) -> {
            permisosPanel.setVisible(New.getId().equals("permisos"));
        });
        
        
        //Evt para cargar foto de perfil en el ImageView
        perfil.setOnMouseClicked(e -> { chooseImage.choose(perfil); });
        editPerfilPicture.setOnAction(e -> { chooseImage.choose(perfil); });
        
        
        //Evt acceso total selected change
        permisoAccesoTotal.selectedProperty().addListener((evt, Old, New) -> { permisosListener(); });
        
        
        //Evt aceptar button
        aceptar.setOnAction(e -> {
            if (validacampos()){
                boolean transac = true;
                usuario u = new usuario();
                u.setUsername(nomUser.getText());
                u.setNombre(txtNombre.getText());
                u.setApellido(txtApellido.getText());
                u.setPassword(getPassword());
                u.setFoto(perfil.getImage());
                u.setEstado((estadoValue.isSelected()) ? 1 : 0);
                
                
                if(accionformulario.equals("crear")){
                    transac = u.Insertar();
                    setPermisos(nomUser.getText()).Insertar();
                }
                else{
                    transac = u.Editar();
                    setPermisos(nomUser.getText()).Editar();
                }
                
                if(transac){
                    if(accionformulario.equals("editar")) 
                        if(from.equals("detalle")){ detalle.setInfo(nomUser.getText()); }else{ detalleHome.setInfo(); }
                    close();
                }else
                    Popup.error(
                        "Error innesperado", 
                        "Ocurrió un error al tratar de realizar la acción. Por favor, intente nuevamente.");
            }else{
                Popup.error(
                        "Error", 
                        "Campos vacíos.");
            }
        });
    }
    
    
    public UsuarioFormController setDataView(String id, DetalleUsuarioController du){
        detalle = du;
        from = "detalle";
        info(id);
        
        return this;
    }
    public UsuarioFormController setDataView(String id, HomeController dh){
        detalleHome = dh;
        from = "home";
        info(id);
        
        return this;
    }
    private void info(String id){
        accionformulario = "editar";
        usuario data = new usuario().Detalle(id);

        if(data != null){
            nomUser.setText(data.getUsername());
            txtNombre.setText(data.getNombre());
            txtApellido.setText(data.getApellido());
            //pass.setText(data.getPassword());
            //confirPass.setText(data.getPassword());
            __password__ = data.getPassword();
            perfil.setImage(data.getFoto());
            estadoValue.setSelected(data.getEstado()==1);
            getPermisos(data.getUsername());
            
            if(id.equals("admin")){
                navegacion.setVisible(false);
                estadoValue.setVisible(false);
            }
        }else{
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. \nPor favor, intente nuevamente.");
            close();
        }
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
    
    
    private permisos setPermisos(String id){
        permisos p = new permisos();
        p.setId(id);
        
        p.setDconsultar(value(directorioConsultar));
        p.setDeditar(value(directorioEdicion));
        p.setDcrear(value(directorioCreacion));
        
        p.setOconsultar(value(operacionesConsulta));
        p.setOeditar(value(operacionesEdicion));
        p.setOcrear(value(operacionesCreacion));
        
        p.setPconsultar(value(productosConsultar));
        p.setPeditar(value(productosEdicion));
        p.setPcrear(value(productosCreacion));
        
        p.setReportes(value(permisoReportes));
        p.setAccesototal(value(permisoAccesoTotal));
        
        return p;
    }
    
    
    private void username(){
        String valueNombre = txtNombre.getText();
        String valueApellido = txtApellido.getText();
        int n = valueNombre.length(), a = valueApellido.length();
        
        valueNombre =(n>=3)? valueNombre.substring(0, 3) : valueNombre+String.join("", Collections.nCopies(3-n, "0"));
        valueApellido =(a>=3)? valueApellido.substring(0, 3) : valueApellido+String.join("", Collections.nCopies(3-a, "0"));;
        
        String codigo = valueNombre.toLowerCase() + valueApellido.toLowerCase(); 
        String cmd = "select count(*) + 1 from Usuario where substring(ID, 1, 6) = '"+codigo+"'";

        int cont = (int) c.readerScalar(cmd, Integer.class);

        if (cont <= 9) codigo += "00" + cont;
        else if (cont <= 99) codigo += "0" + cont;
        else if (cont <= 999) codigo += cont;

        nomUser.setText(codigo);
    }
    
    private String getPassword(){
        if(!accionformulario.equals("crear")){
            return (pass.getText().isEmpty()) ? __password__ : pass.getText();
        }
        
        return pass.getText();
    }
    
    
    private boolean validacampos(){
        if((!pass.getText().isEmpty() && !confirPass.getText().isEmpty()) && !pass.getText().equals(confirPass.getText())){
            Popup.error("Error", "Las contraseñas no coinciden");
            return false;
        }
        
        if(txtNombre.getText().isEmpty()) return false;
        if(txtApellido.getText().isEmpty()) return false;
        if(__password__ == null){
            if(pass.getText().isEmpty()) return false;
            if(confirPass.getText().isEmpty()) return false;
        }else{
            if(!pass.getText().isEmpty() && !pass.getText().equals(confirPass.getText())) return false;
        }
        
        return true;
    }
    
    
    private void permisosListener(){
        if(permisoAccesoTotal.isSelected()){
            directorioConsultar.setDisable(true);
            directorioEdicion.setDisable(true);
            directorioCreacion.setDisable(true);
            
            operacionesConsulta.setDisable(true);
            operacionesEdicion.setDisable(true);
            operacionesCreacion.setDisable(true);
            
            productosConsultar.setDisable(true);
            productosEdicion.setDisable(true);
            productosCreacion.setDisable(true);
            
            permisoReportes.setDisable(true);
        }else{
            directorioConsultar.setDisable(false);
            directorioEdicion.setDisable(false);
            directorioCreacion.setDisable(false);
            
            operacionesConsulta.setDisable(false);
            operacionesEdicion.setDisable(false);
            operacionesCreacion.setDisable(false);
            
            productosConsultar.setDisable(false);
            productosEdicion.setDisable(false);
            productosCreacion.setDisable(false);
            
            permisoReportes.setDisable(false);
        }
    }
    
    
    private int value(CheckBox node){
        if(permisoAccesoTotal.isSelected()) return 1;
        
        if(node.isSelected()) return 1;
        else return 0;
    }
    
    
    private boolean value(int node){
        return node==1;
    }
    
    
    public UsuarioFormController ocultaCampos(){
        navegacion.setVisible(false);
        estadoValue.setVisible(false);
        
        return this;
    }

}
