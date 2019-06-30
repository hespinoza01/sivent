/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.administrar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import objetos.SearchResult;
import objetos.categoria;
import objetos.departamento;
import objetos.producto;
import objetos.usuario;
import sivent.utilities.Items;
import sivent.utilities.Load;
import sivent.view.productos.DetalleProductoController;
import sivent.view.productos.ProductoFormController;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class AdministrarController implements Initializable {

    @FXML private ImageView imageviewSearch;
    @FXML private TextField txtBusqueda;
    @FXML private ChoiceBox<String> metodoBusqueda;
    @FXML private Button btnAdd;
    @FXML private TableView<SearchResult> vistaResultados;
    @FXML private TableColumn<SearchResult, String> columnID;
    @FXML private TableColumn<SearchResult, String> columnNombre;
    @FXML private TableColumn<SearchResult, String> columnFechaModificacion;
    @FXML private TabPane navegacion;
    @FXML private Tab usuariosTab;
    @FXML private Tab ProductosTab;
    @FXML private Tab categoriasTab;
    @FXML private Tab departamentosTab;
    @FXML private BorderPane contentDetails;
    
    Pane detalleUsuario;
    Pane detalleProducto;
    Pane detalleCategoria;
    Pane detalleDepartamento;
    
    DetalleUsuarioController usuarioDC;
    DetalleProductoController productoDC;
    DetalleCategoriaController categoriaDC;
    DetalleDepartamentosController departamentoDC;


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
        
        components();
        metodoBusqueda.setItems(Items.usuario());
        metodoBusqueda.getSelectionModel().selectFirst();
        
        
        // Evt cambio de selección de tab
        navegacion.getSelectionModel().selectedItemProperty().addListener((e, Old, New) -> {
            vistaResultados.getSelectionModel().clearSelection();
            System.out.println("Tab click: "+New.getText().toLowerCase());
            cargar(false);
            switch(New.getText().toLowerCase()){
                case "usuarios":
                    contentDetails.setCenter(detalleUsuario);
                    metodoBusqueda.setItems(Items.usuario());
                    metodoBusqueda.getSelectionModel().selectFirst();
                    break;
                    
                case "productos":
                    contentDetails.setCenter(detalleProducto);
                    metodoBusqueda.setItems(Items.producto());
                    metodoBusqueda.getSelectionModel().selectFirst();
                    break;
                    
                case "categorías":
                    contentDetails.setCenter(detalleCategoria);
                    metodoBusqueda.setItems(Items.categoria());
                    metodoBusqueda.getSelectionModel().selectFirst();
                    break;
                    
                case "departamentos":
                    contentDetails.setCenter(detalleDepartamento);
                    metodoBusqueda.setItems(Items.departamento());
                    metodoBusqueda.getSelectionModel().selectFirst();
                    break;
            }
        });
        
        
        // Evt click item vistaResultado
        vistaResultados.getSelectionModel().selectedItemProperty().addListener((evt, Old, New) -> {
            if(New != null){
                SearchResult item = (SearchResult) New;
                String activa = navegacion.getSelectionModel().getSelectedItem().getText().toLowerCase();

                switch(activa){
                    case "usuarios":
                        usuarioDC.setInfo(item.getId());
                        break;

                    case "productos":
                        productoDC.setInfo(Integer.parseInt(item.getId()));
                        break;

                    case "categorías":
                        categoriaDC.setInfo(Integer.parseInt(item.getId()));
                        break;

                    case "departamentos":
                        departamentoDC.setInfo(Integer.parseInt(item.getId()));
                        break;
                }
            }
        });
        
        
        btnAdd.setOnAction(e -> {
            String activa = navegacion.getSelectionModel().getSelectedItem().getText().toLowerCase();

            switch(activa){
                case "usuarios":
                    new UsuarioFormController().show();
                    break;

                case "productos":
                    new ProductoFormController().show();
                    break;

                case "categorías":
                    new CategoriaController().show();
                    break;
                    
                case "departamentos":
                    new DepartamentosController().show();
                    break;
            }
        });
        
        
        //Evt esccucha para cambio del texto de la barra de busqueda
        txtBusqueda.textProperty().addListener((e, Old, New) -> {
            if(New.length()>0)  cargar(true);
            else    cargar(false);
        });
    }
    
    
    private void components(){
        try{
            FXMLLoader loader = Load.Loader("/sivent/view/administrar/detalleUsuario.fxml");
            detalleUsuario = loader.load();
            usuarioDC = loader.getController();
            
            loader = Load.Loader("/sivent/view/productos/detalleProducto.fxml");
            detalleProducto = loader.load();
            productoDC = loader.getController();
            
            loader = Load.Loader("/sivent/view/administrar/detalleCategoria.fxml");
            detalleCategoria = loader.load();
            categoriaDC = loader.getController();
            
            loader = Load.Loader("/sivent/view/administrar/detalleDepartamentos.fxml");
            detalleDepartamento = loader.load();
            departamentoDC = loader.getController();
            
            contentDetails.setCenter(detalleUsuario);
        }catch(IOException ex){ System.out.println("Error carga components"); }
    }
    
    
    public void cargar(boolean busqueda){
        ObservableList<SearchResult> data = FXCollections.observableArrayList();
        int index = metodoBusqueda.getSelectionModel().getSelectedIndex();
        String activa = navegacion.getSelectionModel().getSelectedItem().getText().toLowerCase();
        String value = txtBusqueda.getText();

        try{
            switch(activa){
                case "usuarios":
                    data = (busqueda)? new usuario().Buscar(value, index) : new usuario().Mostrar();
                    break;

                case "productos":
                    data = (busqueda)? new producto().BuscarTodo(value, index) : new producto().Mostrar();
                    break;

                case "categorías":
                    data = (busqueda)? new categoria().Buscar(value) : new categoria().Mostrar();
                    break;

                case "departamentos":
                    data = (busqueda)? new departamento().Buscar(value, index) : new departamento().Mostrar();
                    break;
            }

            vistaResultados.setItems(data); 
        }catch(Exception ex){}
    }
    
}
