/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.directorio;

import DB.sqlite;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import objetos.SearchResult;
import objetos.cliente;
import objetos.empleado;
import objetos.permisos;
import objetos.proveedor;
import sivent.utilities.Items;
import sivent.utilities.Load;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class DirectorioController implements Initializable {

    @FXML private TabPane navegacion;
    @FXML private Tab labelClientes;
    @FXML private Tab labelProveedores;
    @FXML private Tab labelEmpleados;
    @FXML private ImageView imageviewSearch;
    @FXML private TextField txtBusqueda;
    @FXML private ChoiceBox<String> metodoBusqueda;
    @FXML private Button btnAdd;
    @FXML private TableView<SearchResult> vistaResultados;
    @FXML private TableColumn<SearchResult, String> columnID;
    @FXML private TableColumn<SearchResult, String> columnNombre;
    @FXML private TableColumn<SearchResult, String> columnFechaModificacion;
    @FXML private BorderPane contentDetails;
    
    Pane detalleCliente;
    Pane detalleProveedor;
    Pane detalleEmpleado;
    DetalleClienteController controllerDC;
    DetalleProveedorController controllerDP;
    DetalleEmpleadoController controllerDE;
    
    boolean see = true;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    private void inicia(){
        columnID.setCellValueFactory(cell -> cell.getValue().getIdProperty());
        columnNombre.setCellValueFactory(cell -> cell.getValue().getNameProperty());
        columnFechaModificacion.setCellValueFactory(cell -> cell.getValue().getModificationDateProperty());
        
        metodoBusqueda.setItems(Items.cliente());
        metodoBusqueda.getSelectionModel().select(0);
        
        components();
        //verificaPermisos();
        
        
        // Evt click item vistaResultado
        vistaResultados.getSelectionModel().selectedItemProperty().addListener((evt, Old, New) -> {
            if(New != null){
                SearchResult item = (SearchResult) New;
                String activa = navegacion.getSelectionModel().getSelectedItem().getText().toLowerCase();

                switch(activa){
                    case "clientes":
                        controllerDC.setInfo(item.getId());
                        break;

                    case "proveedores":
                        controllerDP.setInfo(Integer.parseInt(item.getId()));
                        break;

                    case "empleados":
                        controllerDE.setInfo(Integer.parseInt(item.getId()));
                        break;
                }
            }
        });
        
        
        // Evt cambio de selección de tab
        navegacion.getSelectionModel().selectedItemProperty().addListener((e, Old, New) -> {
            vistaResultados.getSelectionModel().clearSelection();
            cargar(false);
            switch(New.getText().toLowerCase()){
                case "clientes":
                    contentDetails.setCenter(detalleCliente);
                    metodoBusqueda.setItems(Items.cliente());
                    metodoBusqueda.getSelectionModel().select(0);
                    break;
                    
                case "proveedores":
                    contentDetails.setCenter(detalleProveedor);
                    metodoBusqueda.setItems(Items.proveedor());
                    metodoBusqueda.getSelectionModel().select(0);
                    break;
                    
                case "empleados":
                    contentDetails.setCenter(detalleEmpleado);
                    metodoBusqueda.setItems(Items.empleado());
                    metodoBusqueda.getSelectionModel().select(0);
                    break;
            }
        });
        
        
        //Evt esccucha para cambio del texto de la barra de busqueda
        txtBusqueda.textProperty().addListener((e, Old, New) -> {
            if(New.length()>0)  cargar(true);
            else    cargar(false);
        });
        
        
        btnAdd.setOnAction(e -> {
            String activa = navegacion.getSelectionModel().getSelectedItem().getText().toLowerCase();

            switch(activa){
                case "clientes":
                    new ClienteFormController().show();
                    break;

                case "proveedores":
                    new ProveedorFormController().show();
                    break;

                case "empleados":
                    new EmpleadoFormController().show();
                    break;
            }
        });
    }
    
    
    private void components(){
        System.out.println("Principio carga components");
        try{
            FXMLLoader loader = Load.Loader("/sivent/view/directorio/detalleCliente.fxml");
            detalleCliente = loader.load();
            controllerDC = loader.getController();
            
            loader = Load.Loader("/sivent/view/directorio/detalleProveedor.fxml");
            detalleProveedor = loader.load();
            controllerDP = loader.getController();
            
            loader = Load.Loader("/sivent/view/directorio/detalleEmpleado.fxml");
            detalleEmpleado = loader.load();
            controllerDE = loader.getController();
            
            contentDetails.setCenter(detalleCliente);
        }catch(IOException ex){ System.out.println("Error carga components"); }
    }
    
    
    public void cargar(boolean busqueda){
        ObservableList<SearchResult> data = FXCollections.observableArrayList();
        int index = metodoBusqueda.getSelectionModel().getSelectedIndex();
        String activa = navegacion.getSelectionModel().getSelectedItem().getText().toLowerCase();
        String value = txtBusqueda.getText();

        try{
            switch(activa){
                case "clientes":
                    data = (busqueda)? new cliente().Buscar(value, index) : new cliente().Mostrar();
                    break;

                case "proveedores":
                    data = (busqueda)? new proveedor().Buscar(value, index) : new proveedor().Mostrar();
                    break;

                case "empleados":
                    data = (busqueda)? new empleado().Buscar(value, index) : new empleado().Mostrar();
                    break;
            }

            vistaResultados.getItems().clear();
            vistaResultados.setItems(data);
        }catch(Exception ex){}
    }
    
    
    public void verificaPermisos(){
        permisos p = new permisos().get(sqlite.getUser());
        
        if(p.getDconsultar()==0){
            vistaResultados.setVisible(false);
            txtBusqueda.setEditable(false);
        }else{
            vistaResultados.setVisible(true);
            txtBusqueda.setEditable(true);
        }
        
        if(p.getDcrear()==0) btnAdd.setVisible(false); 
        else btnAdd.setVisible(true);
    }
    
    private void prueba(){
        ObservableList<SearchResult> valores = FXCollections.observableArrayList();
        valores.addAll(
                new SearchResult("1", "Harold", "Hoy"),
                new SearchResult("2", "Benito", "Hoy"),
                new SearchResult("3", "Espinoza", "Hoy")
        );
        
        vistaResultados.setItems(valores);
    }
    
}
