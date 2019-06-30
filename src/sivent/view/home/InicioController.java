/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.home;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import objetos.SearchResult;
import objetos.inicio;
import sivent.utilities.cellFill;
import sivent.view.directorio.ClienteFormController;
import sivent.view.directorio.ProveedorFormController;
import sivent.view.operaciones.CompraFormController;
import sivent.view.operaciones.VentaFormController;

/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class InicioController implements Initializable {

    @FXML private Label clientesContador;
    @FXML private Label productosContador;
    @FXML private Label proveedoresContador;
    @FXML private Label ventasContador;
    @FXML private TableView<SearchResult> topClientes;
        @FXML private TableColumn<SearchResult, String> TCcolumnID;
        @FXML private TableColumn<SearchResult, String> TCcolumnNombre;
        @FXML private TableColumn<SearchResult, String> TCcolumnRecaudacion;
    @FXML private TableView<SearchResult> ultimasVentas;
        @FXML private TableColumn<SearchResult, String> UVcolumnID;
        @FXML private TableColumn<SearchResult, String> UVcolumnFecha;
        @FXML private TableColumn<SearchResult, String> UVcolumnTotal;
    @FXML private TableView<SearchResult> productosVendidos;
        @FXML private TableColumn<SearchResult, String> PVcolumnID;
        @FXML private TableColumn<SearchResult, String> PVcolumnNombre;
        @FXML private TableColumn<SearchResult, String> PVcolumnUnidades;
    @FXML private Button showBaged;
    @FXML private Pane bagedContent;
    @FXML private Button hideBaged;
    @FXML private Button clienteItem;
    @FXML private Button compraItem;
    @FXML private Button proveedorItem;
    @FXML private Button ventaItem;


    public InicioController(){}
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }   
    
    
    private void inicia(){
        initializeTables();
        bagedContent.setVisible(false);
        
        showBaged.setOnAction(e -> {
            bagedContent.setVisible(true);
            showBaged.setVisible(false);
        });
        
        
        hideBaged.setOnAction(e -> {
            bagedContent.setVisible(false);
            showBaged.setVisible(true);
        });
        
        
        clienteItem.setOnAction(e -> { new ClienteFormController().show(); });
        compraItem.setOnAction(e -> { new CompraFormController().show(); });
        proveedorItem.setOnAction(e -> { new ProveedorFormController().show(); });
        ventaItem.setOnAction(e -> { new VentaFormController().show(); });
    }
    
    
    private void initializeTables(){
        // Top Clientes table
        TCcolumnID.setCellValueFactory(cell -> cell.getValue().getIdProperty());
        TCcolumnNombre.setCellValueFactory(cell -> cell.getValue().getNameProperty());
        TCcolumnRecaudacion.setCellValueFactory(cell -> cell.getValue().getModificationDateProperty());
            cellFill.setBlue(TCcolumnID);
            cellFill.setBlue(TCcolumnNombre);
            cellFill.setBlue(TCcolumnRecaudacion);
        
        // Productos más vendidos table
        PVcolumnID.setCellValueFactory(cell -> cell.getValue().getIdProperty());
        PVcolumnNombre.setCellValueFactory(cell -> cell.getValue().getNameProperty());
        PVcolumnUnidades.setCellValueFactory(cell -> cell.getValue().getModificationDateProperty());
            cellFill.setBlue(PVcolumnID);
            cellFill.setBlue(PVcolumnNombre);
            cellFill.setBlue(PVcolumnUnidades);
        
        // Ultimas ventas property
        UVcolumnID.setCellValueFactory(cell -> cell.getValue().getIdProperty());
        UVcolumnFecha.setCellValueFactory(cell -> cell.getValue().getNameProperty());
        UVcolumnTotal.setCellValueFactory(cell -> cell.getValue().getModificationDateProperty());
            cellFill.setBlue(UVcolumnID);
            cellFill.setBlue(UVcolumnFecha);
            cellFill.setBlue(UVcolumnTotal);
            
        cargar();
    }
    
    
    public void cargar(){
        ObservableList<String> values = inicio.contadores();
        clientesContador.setText(values.get(0));
        proveedoresContador.setText(values.get(1));
        productosContador.setText(values.get(2));
        ventasContador.setText(values.get(3));

        ultimasVentas.setItems(inicio.ultimasVentas());
        productosVendidos.setItems(inicio.productosMasVendidos());
        topClientes.setItems(inicio.topClientes());
    }
    
}
