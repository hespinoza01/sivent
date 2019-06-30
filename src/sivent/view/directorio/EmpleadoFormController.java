/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.directorio;

import DB.conexion;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import objetos.SearchResult;
import objetos.departamento;
import objetos.empleado;
import sivent.utilities.Dragged;
import sivent.utilities.chooseImage;
import sivent.utilities.Items;
import sivent.utilities.Popup;
import sivent.utilities.SicemWindow;
import sivent.utilities.rounded;
import sivent.utilities.validator;
import sivent.utilities.valueDate;

/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class EmpleadoFormController extends SicemWindow implements Initializable {

    @FXML private ImageView foto;
    @FXML private RadioButton generoMasculino;
    @FXML private ToggleGroup genero;
    @FXML private RadioButton generoFemenino;
    @FXML private TextField txtID;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TabPane contentTabs;
    @FXML private Tab infoPersonal;
    @FXML private Tab infoLaboral;
    @FXML private CheckBox estadoValue;
    @FXML private Button cancelar;
    @FXML private Button guardar;
    @FXML private AnchorPane contentInfoLaboral;
    @FXML private DatePicker fechaContratacion;
    @FXML private TextField txtDepartamento;
    @FXML private TextField txtTituloLaboral;
    @FXML private TextField txtReportarA;
    @FXML private TextArea txtObservaciones;
    @FXML private AnchorPane contentInfoPersonal;
    @FXML private DatePicker fechaNacimiento;
    @FXML private ComboBox<String> txtCiudad;
    @FXML private RadioButton ecSoltero;
    @FXML private ToggleGroup estadocivil;
    @FXML private RadioButton ecCasado;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTel;
    @FXML private TextField txtCedula;
    @FXML private TextArea txtDireccion;
    @FXML private Pane title;
    @FXML private Label titleLbl;
    
    String accionformulario;
    int _id, idd=-1, idr=-1;
    boolean cargainfo = false;
    ObservableList<String> cache = FXCollections.observableArrayList();
    conexion c = new conexion();
    DetalleEmpleadoController detalle;
    
    
    public EmpleadoFormController(){ 
        super("/sivent/view/directorio/empleadoForm.fxml");
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
        Tooltip.install(foto, new Tooltip("Cargar imagen"));
        guardar.setText(accionformulario.equals("crear") ? "Guardar" : "Actualizar");
        contentInfoLaboral.setVisible(false);
        txtCiudad.setItems(Items.ciudad());
        txtCiudad.getSelectionModel().select(0);
        Dragged.set(title, titleLbl);
        
        if (accionformulario.equals("crear")){
            loadID();
            fechaNacimiento.setValue(LocalDate.now());
            fechaContratacion.setValue(LocalDate.now());
            rounded.roundImageView(foto, new Image("/sivent/images/avatar.png"));
        }
        
        
        // Evento cambio de pestaña del tab
        contentTabs.getSelectionModel().selectedItemProperty().addListener((e, oldTab, newTab) -> {
            if(newTab.getId().equalsIgnoreCase("infoLaboral")){
                contentInfoLaboral.setVisible(true);
                contentInfoPersonal.setVisible(false);
            }else{
                contentInfoLaboral.setVisible(false);
                contentInfoPersonal.setVisible(true);
            }
        });
        
        
        // Evento cambio texto txtDepartamento
        txtDepartamento.textProperty().addListener((e, oldValue, newValue) -> {
            if(!cargainfo){
                idd=-1;
                if(txtDepartamento.getText().length() > 0 && !cargainfo)
                    new AutoCompletionTextFieldBinding<>(txtDepartamento, SuggestionProvider.create(sugerencia(newValue, 1)));
            }
        });
        
        
        // Validacion número de teléfono
        txtTel.textProperty().addListener((evt, Old, New) -> {
//            Pattern pat = Pattern.compile("[0-9]{4}-[0-9]{4}");
//            Matcher mat = pat.matcher(New);
//            if(mat.find())
//                if(New.length()>0) txtTel.setText(New.substring(0, New.length()-1));
//                else txtTel.clear();
//            
//            if(New.length() == 4) txtTel.setText(New+"-");
        });
        
        
        // Evento focusProperty txtDepartamento
        txtDepartamento.focusedProperty().addListener((e, Old, New) -> {
            if(!New && txtDepartamento.getText().length()>0){
                    String[] values = txtDepartamento.getText().split("-");
                    if(values.length != 2){
                        validator.validate(txtDepartamento, true);
                        Popup.warning(
                                "Advertencia", 
                                "Valor ingresado en el departamento no valido. Por favor, seleccione una de las opciones de la lista de sugerencias");
                    }else{
                        cargainfo = true;
                        idd = Integer.parseInt(values[0]);
                        txtDepartamento.setText(values[1]);
                        cargainfo = false;
                    }
            }else
                validator.validate(txtDepartamento, false);
        });
        
        
        // Evento cambio texto reportarA
        txtReportarA.textProperty().addListener((e, oldValue, newValue) -> {
            if(!cargainfo){
                idr = -1;
                if(newValue.length() > 0 && !cargainfo)
                    new AutoCompletionTextFieldBinding<>(txtReportarA, SuggestionProvider.create(sugerencia(newValue, 2)));
            }
        });
        
        
        // Evento focusProperty txtReportarA
        txtReportarA.focusedProperty().addListener((e, Old, New) -> {
            if(!New && txtReportarA.getText().length()>0){
                if(!cargainfo){
                    String[] values = txtReportarA.getText().split("-");
                    if(values.length != 2){
                        validator.validate(txtReportarA, true);
                        Popup.warning(
                                "Advertencia", 
                                "Valor ingresado en el campo de 'reportar a' no valido. Por favor, seleccione una de las opciones de la lista de sugerencias");
                    }else{
                        cargainfo = true;
                        idr = Integer.parseInt(values[0]);
                        txtReportarA.setText(values[1]);
                        cargainfo = false;
                    }
                }
            }else
                validator.validate(txtReportarA, false);
        });
        
        
        // Evento botón cancelar
        cancelar.setOnAction((e) -> { this.close(); });
        
        
        //Evento botón guardar
        guardar.setOnAction((e) -> {
            if (idd != -1 && idr != -1){
                empleado em = new empleado();
                em.setNombres(txtNombre.getText());
                em.setApellidos(txtApellido.getText());
                em.setDepartamentoId(idd);
                em.setTituloLaboral(txtTituloLaboral.getText());
                em.setFechaNacimiento(valueDate.setValue(fechaNacimiento.getValue()));
                em.setFechaContratacion(valueDate.setValue(fechaContratacion.getValue()));
                em.setEstadoCivil((ecSoltero.isSelected()) ? 0 : 1);
                em.setGenero((generoFemenino.isSelected()) ? 0 : 1);
                em.setDomicilio(txtDireccion.getText());
                em.setCiudad(txtCiudad.getSelectionModel().getSelectedItem());
                em.setTelefono(txtTel.getText());
                em.setCedula(txtCedula.getText());
                em.setEmail(txtEmail.getText());
                em.setObservaciones(txtObservaciones.getText());
                em.setReportarA(idr);
                em.setFoto(foto.getImage());
                em.setEstado((estadoValue.isSelected()) ? 1 : 0);
                
                boolean transac = (accionformulario.equals("crear")) ? em.Insertar() : em.Editar();
                
                if(transac){ 
                    if(accionformulario.equals("editar")) detalle.setInfo(Integer.parseInt(txtID.getText())); 
                    this.close(); 
                }else Popup.error(
                        "Error innesperado", 
                        "Ocurrió un error al tratar de realizar la acción, por favor, intente nuevamente.");
            } 
            else
                Popup.error(
                    "Error en el campo departamento/reportar a",
                    "Ocurrió un error inesperado al tratar de guardar la información. Por favor, verifique los campos e intente nuevamente.");
        });
        
        
        // Evento cargar imagen
        foto.setOnMouseClicked((e) -> {
            chooseImage.choose(foto);
        });
    }
    
    public EmpleadoFormController setDataView(int id, DetalleEmpleadoController de){
        accionformulario = "editar";
        detalle = de;
        empleado e = new empleado().Detalle(id);

        if(e != null){
            cargainfo = true;

            txtID.setText(Integer.toString(e.getId()));
            txtNombre.setText(e.getNombres());
            txtApellido.setText(e.getApellidos());
            setValueDepartamento(e.getDepartamentoId());
            txtTituloLaboral.setText(e.getTituloLaboral());
            fechaNacimiento.setValue(valueDate.getValue(e.getFechaNacimiento()));
            fechaContratacion.setValue(valueDate.getValue(e.getFechaContratacion()));

            ecSoltero.setSelected(e.getEstadoCivil() == 0);
            generoFemenino.setSelected(e.getGenero() == 0);

            txtCiudad.getSelectionModel().select(e.getCiudad());
            txtCedula.setText(e.getCedula());
            txtEmail.setText(e.getEmail());
            txtTel.setText(e.getTelefono());
            txtDireccion.setText(e.getDomicilio());
            txtObservaciones.setText(e.getObservaciones());
            setValueReportarA(e.getReportarA());
            foto.setImage(e.getFoto());
            estadoValue.setSelected(e.getEstado() == 1);

            cargainfo = false;
        }else{
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
            this.close();
        }
        
        return this;
    }
    
    public void loadID(){
        int value = (int) c.readerScalar("select count(*) + 1 from Empleado", Integer.class);
        txtID.setText(Integer.toString(value));
    }
    
    private void setValueDepartamento(int id){
        departamento r = new departamento().Detalle(id);
        idd = r.getId();
        txtDepartamento.setText(r.getNombre());
    }
    
    private void setValueReportarA(int id){
        empleado r = new empleado().Detalle(id);
        idr = r.getId();
        txtReportarA.setText(r.getNombres()+" "+r.getApellidos());
    }
    
    public ObservableList<String> sugerencia(String value, int tipo){
        ObservableList<String> items = FXCollections.observableArrayList();
        if(tipo == 1)
            for(SearchResult sr : new departamento().Buscar(value, 0)){
                items.add(sr.getId()+"-"+sr.getName()); }
        else
            for(SearchResult sr : new empleado().Buscar(value, 0)){
                items.add(sr.getId()+"-"+sr.getName()); }
        cache = items;
        
        return items;
    }
    
}
