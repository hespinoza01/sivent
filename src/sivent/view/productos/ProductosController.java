/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.productos;

import DB.sqlite;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import objetos.SearchResult;
import objetos.permisos;
import objetos.producto;
import sivent.utilities.Items;
import sivent.utilities.Load;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class ProductosController implements Initializable {

    @FXML private ImageView imageviewSearch;
    @FXML private TextField txtBusqueda;
    @FXML private ChoiceBox<String> metodoBusqueda;
    @FXML private TableView<SearchResult> vistaResultados;
    @FXML private TableColumn<SearchResult, String> columnID;
    @FXML private TableColumn<SearchResult, String> columnName;
    @FXML private TableColumn<SearchResult, String> columnFechaModificacion;
    @FXML private BorderPane contentDetails;
    
    DetalleProductoController detalle;
    boolean see;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    private void inicia(){
        components();
        metodoBusqueda.setItems(Items.producto());
        metodoBusqueda.getSelectionModel().selectFirst();
        
        columnID.setCellValueFactory(cell -> cell.getValue().getIdProperty());
        columnName.setCellValueFactory(cell -> cell.getValue().getNameProperty());
        columnFechaModificacion.setCellValueFactory(cell -> cell.getValue().getModificationDateProperty());
        
        
        // Evt txtBusqueda text change listener
        txtBusqueda.textProperty().addListener((evt, Old, New) -> {
            if(New.length()>0) cargar(true);
            else cargar(false);
        });
        
        
        //Evt selection listener vistaResultados
        vistaResultados.getSelectionModel().selectedItemProperty().addListener((evt, Old, New) -> {
            SearchResult item = (SearchResult) New;
            detalle.setInfo(Integer.parseInt(item.getId()));
        });
    }
    
    
    private void components(){
        try{
            FXMLLoader loader = Load.Loader("/sivent/view/productos/detalleProducto.fxml");
            Pane pane = loader.load();
            detalle = loader.getController();
            detalle.ocultaCampos();
            
            contentDetails.setCenter(pane);
        }catch(IOException ex){ System.out.println("Error carga components"); }
    }
    
    
    public void cargar(boolean buscar){
        String value = txtBusqueda.getText();
        int index = metodoBusqueda.getSelectionModel().getSelectedIndex();
        
        try{
        ObservableList<SearchResult> items = (buscar) ? new producto().Buscar(value, index) : new producto().MostrarHabilitados();
        vistaResultados.setItems(items); 
        }catch(Exception ex){}
    }
    
    public void verificaPermisos(){
        permisos p = new permisos().get(sqlite.getUser());
        
        if(p.getPconsultar()==0){
            vistaResultados.setVisible(false);
            txtBusqueda.setEditable(false);
        }else{
            vistaResultados.setVisible(true);
            txtBusqueda.setEditable(true);
        }
        
    }
    
}
