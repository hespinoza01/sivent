/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.productos;

import DB.conexion;
import DB.sqlite;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import objetos.SearchResult;
import objetos.permisos;
import objetos.producto;
import sivent.utilities.Popup;
import sivent.utilities.cellFill;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class DetalleProductoController implements Initializable {

    @FXML private Label EstadoValue;
    @FXML private Label labelFechaModificacion;
    @FXML private TabPane navegacion;
    @FXML private Tab informacionTab;
    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtID;
    @FXML private TextField txtPrecioVenta;
    @FXML private TextArea txtDescripcion;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtCantidadPorUnidad;
    @FXML private TextField txtStock;
    @FXML private Tab historialTab;
    @FXML private TabPane navHistorial;
    @FXML private Tab entradaTab;
    @FXML private Tab costoTab;
    @FXML private Tab precioTab;
    @FXML private TableView<SearchResult> historialVista;
    @FXML private TableColumn<SearchResult, String> column1;
    @FXML private TableColumn<SearchResult, String> column2;
    @FXML private TableColumn<SearchResult, String> column3;
    @FXML private Button editar;
    @FXML VBox indicadores;
    
    boolean admin = true;
    ObservableList<SearchResult> entrada = FXCollections.observableArrayList();
    ObservableList<SearchResult> precio = FXCollections.observableArrayList();
    ObservableList<SearchResult> costo = FXCollections.observableArrayList();
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
        EstadoValue.setText("");
        editar.setVisible(false);
        
        column1.setCellValueFactory(cell -> cell.getValue().getIdProperty());
        column2.setCellValueFactory(cell -> cell.getValue().getNameProperty());
        column3.setCellValueFactory(cell -> cell.getValue().getModificationDateProperty());
            cellFill.setBlue(column1);
            cellFill.setBlue(column2);
            cellFill.setBlue(column3);
        
        //Evt editar button
        editar.setOnAction(e -> {
            new ProductoFormController()
                    .load(new ProductoFormController())
                    .setDataView(Integer.parseInt(txtID.getText()), this)
                    .showAfterLoad();
        });
        
        
        // Listener Historial tab
        navHistorial.getSelectionModel().selectedItemProperty().addListener((evt, Old, New) -> {
            String activa = New.getText().toLowerCase();
            try{
            switch(activa){
                case "entrada":
                    historialVista.setItems(entrada);
                    column1.setText("Cantidad");
                    column2.setText("Costo unitario");
                    column3.setText("Fecha");
                    break;
                    
                case "costo":
                    historialVista.setItems(costo);
                    column1.setText("Fecha inicio");
                    column2.setText("Fecha final");
                    column3.setText("Costo");
                    break;
                    
                case "precio":
                    historialVista.setItems(precio);
                    column1.setText("Fecha inicio");
                    column2.setText("Fecha final");
                    column3.setText("Precio");
                    break;
            }
            }catch(Exception ex){}
        });
    }


    public void setInfo(int id){
        producto data = new producto().Detalle(id);
        permisos p = new permisos().get(sqlite.getUser());

        if(data != null){
            if (admin) editar.setVisible(true);

            txtID.setText(Integer.toString(data.getId()));
            setValorCategoria(data.getCategoriaId());
            txtNombreProducto.setText(data.getNombre());
            txtCantidadPorUnidad.setText(Integer.toString(data.getCantidadPorUnidad()));
            txtPrecioVenta.setText(data.getPrecioVenta().toString());
            txtStock.setText(Integer.toString(data.getStock()));
            txtDescripcion.setText(data.getDescripcion());
            EstadoValue.setText((data.getEstado()==1) ? "Habilitado" : "Deshabilitado");
            labelFechaModificacion.setText(data.getFechaModificacion());
            cargarHistorial(data.getId());
            
            if(p.getPeditar() == 0) editar.setVisible(false);
        }else
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
    }
    
    
    private void setValorCategoria(int id){
        String value = (String) c.readerScalar("select Nombre from Categoria where ID="+id, String.class);
        txtCategoria.setText(value);
    }
    
    
    private void cargarHistorial(int id){
        try{
        entrada = new producto().EntradaProducto(id);
        precio = new producto().HistorialPrecio(id);
        costo = new producto().HistorialCosto(id);
        
        historialVista.setItems(entrada);
        }catch(Exception ex){}
        column1.setText("Cantidad");
        column2.setText("Costo unitario");
        column3.setText("Fecha");
    }
    
    
    public void ocultaCampos(){
        admin = false;
        indicadores.setVisible(false);
        historialTab.setDisable(true);
    }
    
}
