/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.operaciones;

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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import objetos.SearchResult;
import objetos.compra;
import objetos.permisos;
import objetos.venta;
import sivent.utilities.Items;
import sivent.utilities.Load;

/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class OperacionesController implements Initializable {
    
    @FXML private TabPane navegacion;
    @FXML private Tab ventas;
    @FXML private Tab compras;
    @FXML private ImageView imageviewSearch;
    @FXML private TextField txtBusqueda;
    @FXML private ChoiceBox<String> metodoBusqueda;
    @FXML private Button btnAdd;
    @FXML private TableView<SearchResult> vistaResultados;
    @FXML private TableColumn<SearchResult, String> columnID;
    @FXML private TableColumn<SearchResult, String> columnFecha;
    @FXML private TableColumn<SearchResult, String> columnFechaModificacion;
    @FXML private BorderPane contentDetails;
    
    Pane detalleVenta;
    Pane detalleCompra;
    DetalleVentaController controllerDV;
    DetalleCompraController controllerDC;
    boolean see;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    private void inicia(){
        columnID.setCellValueFactory(cell -> cell.getValue().getIdProperty());
        columnFecha.setCellValueFactory(cell -> cell.getValue().getNameProperty());
        columnFechaModificacion.setCellValueFactory(cell -> cell.getValue().getModificationDateProperty());
        
        metodoBusqueda.setItems(Items.venta());
        metodoBusqueda.getSelectionModel().selectFirst();
        
        components();
        //verificaPermisos();
        
        
        // Evt click item vistaResultado
        vistaResultados.getSelectionModel().selectedItemProperty().addListener((evt, Old, New) -> {
            if(New != null){
                SearchResult item = (SearchResult) New;
                String activa = navegacion.getSelectionModel().getSelectedItem().getText().toLowerCase();
                switch(activa){
                    case "ventas":
                        controllerDV.setInfo(item.getId());
                        break;

                    case "compras":
                        controllerDC.setInfo(item.getId());
                        break;
                }
            }
        });
        
        
        // Evt cambio de selección de tab
        navegacion.getSelectionModel().selectedItemProperty().addListener((e, Old, New) -> {
            vistaResultados.getSelectionModel().clearSelection();
            cargar(false);
            switch(New.getText().toLowerCase()){
                case "ventas":
                    contentDetails.setCenter(detalleVenta);
                    metodoBusqueda.setItems(Items.venta());
                    metodoBusqueda.getSelectionModel().selectFirst();
                    break;
                    
                case "compras":
                    contentDetails.setCenter(detalleCompra);
                    metodoBusqueda.setItems(Items.compra());
                    metodoBusqueda.getSelectionModel().selectFirst();
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
                case "ventas":
                    new VentaFormController().show();
                    break;

                case "compras":
                    new CompraFormController().show();
                    break;
            }
        });
    }
    
    
    private void components(){
        System.out.println("Principio carga components");
        try{
            FXMLLoader loader = Load.Loader("/sivent/view/operaciones/detalleVenta.fxml");
            detalleVenta = loader.load();
            controllerDV = loader.getController();
            
            loader = Load.Loader("/sivent/view/operaciones/detalleCompra.fxml");
            detalleCompra = loader.load();
            controllerDC = loader.getController();
            
            contentDetails.setCenter(detalleVenta);
        }catch(IOException ex){ System.out.println("Error carga components"); }
    }
    
    
    public void cargar(boolean busqueda){
        ObservableList<SearchResult> data = FXCollections.observableArrayList();
        int index = metodoBusqueda.getSelectionModel().getSelectedIndex();
        String activa = navegacion.getSelectionModel().getSelectedItem().getText().toLowerCase();
        String value = txtBusqueda.getText();

        try{
            switch(activa){
                case "ventas":
                    data = (busqueda)? new venta().Buscar(value, index) : new venta().Mostrar();
                    break;

                case "compras":
                    data = (busqueda)? new compra().Buscar(value, index) : new compra().Mostrar();
                    break;
            }

            vistaResultados.setItems(data);
        }catch(Exception ex){}
    }
    
    
    public void verificaPermisos(){
        permisos p = new permisos().get(sqlite.getUser());
        
        if(p.getOconsultar()==0){
            vistaResultados.setVisible(false);
            txtBusqueda.setEditable(false);
        }else{
            vistaResultados.setVisible(true);
            txtBusqueda.setEditable(true);
        }
        
        if(p.getOcrear()==0) btnAdd.setVisible(false);
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
