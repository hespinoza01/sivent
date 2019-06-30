/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.productos;

import DB.conexion;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import objetos.producto;
import sivent.utilities.Dragged;
import sivent.utilities.Popup;
import sivent.utilities.SicemWindow;
import sivent.utilities.validator;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class ProductoFormController extends SicemWindow implements Initializable {

    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtID;
    @FXML private ComboBox<String> txtCategoria;
    @FXML private TextField txtPrecioVenta;
    @FXML private Spinner<Integer> txtCantidadPorUnidad;
    @FXML private TextArea txtDescripcion;
    @FXML private Button cancelar;
    @FXML private Button guardar;
    @FXML private CheckBox estadoValor;
    @FXML private Pane title;
    @FXML private Label titleLbl;
    @FXML private TextField txtStock;
    
    String accionformulario = "";
    boolean cargainfo = false;
    ObservableList<Integer> idCategoria = FXCollections.observableArrayList();
    DetalleProductoController detalle;
    conexion c = new conexion();
    
    
    public ProductoFormController(){ 
        super("/sivent/view/productos/productoForm.fxml");
        accionformulario="crear"; 
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    private void inicia(){
        guardar.setText((accionformulario.equals("crear")) ? "Guardar" : "Actualizar");
        Dragged.set(title, titleLbl);
        cargaCategorias();
        txtCantidadPorUnidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));
        validator.Numeros(txtPrecioVenta);
        if(accionformulario.equals("crear")){ 
            loadID();
            txtStock.setText("0");
            txtPrecioVenta.setText("0");
        }
        
        
        //Evt cancelar button
        cancelar.setOnAction(e -> { close(); });
        
        
        //Evt guardar button
        guardar.setOnAction(e -> {
            producto p = new producto();
            p.setId(Integer.parseInt(txtID.getText()));
            p.setNombre(txtNombreProducto.getText());
            p.setDescripcion(txtDescripcion.getText());
            p.setCantidadPorUnidad(txtCantidadPorUnidad.getValue());
            p.setPrecioVenta(new BigDecimal(txtPrecioVenta.getText().replace(",", "")));
            p.setStock(Integer.parseInt(txtStock.getText()));
            p.setCategoriaId(idCategoria.get(txtCategoria.getSelectionModel().getSelectedIndex()));
            p.setEstado((estadoValor.isSelected()) ? 1 : 0);

            boolean transac = (accionformulario.equals("crear")) ? p.Insertar() : p.Editar();
            
            if(transac){ 
                if(accionformulario.equals("editar")) detalle.setInfo(Integer.parseInt(txtID.getText())); 
                close(); 
            }else Popup.error(
                    "Error innesperado", 
                    "Ocurrió un error al tratar de realizar la acción, por favor, intente nuevamente.");
        });
        
        
        //Evt para coregir el error en java sobre almacenar el valor del spinner cuando es cambiado por el usuario mediante el textfield
        txtCantidadPorUnidad.focusedProperty().addListener((evt, Old, New) -> {
            if(!New) txtCantidadPorUnidad.increment(0);
        });
    }
    
    
    private void loadID(){
        int value = (int) c.readerScalar("select count(*) + 1 from Producto", Integer.class);
        txtID.setText(Integer.toString(value));
    }
    
    
    public ProductoFormController setDataView(int id, DetalleProductoController dp){
        accionformulario = "editar";
        detalle = dp;
        producto data = new producto().Detalle(id);

        if (data != null){
            cargainfo = true;

            txtID.setText(Integer.toString(data.getId()));
            txtNombreProducto.setText(data.getNombre());
            txtPrecioVenta.setText(data.getPrecioVenta().toString());
            txtCantidadPorUnidad.getValueFactory().setValue(data.getCantidadPorUnidad());
            setValorCategoria(data.getCategoriaId());
            txtDescripcion.setText(data.getDescripcion());
            txtStock.setText(Integer.toString(data.getStock()));
            estadoValor.setSelected(data.getEstado()==1);

            cargainfo = false;
        }else{
            Popup.error(
                    "Error con la solicitud", 
                    "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
            close();
        }
        
        return this;
    }
    
    
    private void setValorCategoria(int id){
        String value = (String) c.readerScalar("select Nombre from Categoria where ID="+id, String.class);
        txtCategoria.getSelectionModel().select(value);
    }
    
    
    private void cargaCategorias(){
        System.out.println("Inicio carga categorías");
        ObservableList<Integer> listInt = FXCollections.observableArrayList();
        ObservableList<String> listString = FXCollections.observableArrayList();
        try{
            System.out.println("Antes del select");
            ResultSet rs = c.readerSimple("select ID, Nombre from Categoria");
            System.out.println("Después del select");
            while(rs.next()){
                idCategoria.add(rs.getInt(1));
                txtCategoria.getItems().add(rs.getString(2));
            }
            c.close();
            
            txtCategoria.getSelectionModel().selectFirst();
            System.out.println("Fin carga categorías");
        }catch(SQLException ex){ System.out.println("Error carga categorias"); }
    }
    
}
