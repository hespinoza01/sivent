/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.operaciones;

import DB.conexion;
import DB.sqlite;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import objetos.compra;
import objetos.detalleCompra;
import objetos.permisos;
import sivent.utilities.Items;
import sivent.utilities.Popup;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class DetalleCompraController implements Initializable {

    @FXML private Label txtTotalCompra;
    @FXML private TextField txtProveedor;
    @FXML private TextField txtCodigoCompra;
    @FXML private TableView<detalleCompra> detalleCompra;
    @FXML private TableColumn<detalleCompra, Integer> idProducto;
    @FXML private TableColumn<detalleCompra, String> nombreProducto;
    @FXML private TableColumn<detalleCompra, Integer> cantidadProducto;
    @FXML private TableColumn<detalleCompra, ObjectProperty<BigDecimal>> costoProducto;
    @FXML private TableColumn<detalleCompra, ObjectProperty<BigDecimal>> costoTotal;
    @FXML private TextField txtFechaCompra;
    @FXML private TextField txtTipoPago;
    @FXML private Button editar;
    @FXML private Label EstadoValue;
    @FXML private Label labelFechaModificacion;
    @FXML private HBox EstadoValueContent;
    
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
        txtTotalCompra.setText("");
        editar.setVisible(false);
        EstadoValueContent.setVisible(false);
        
        idProducto.setCellValueFactory(new PropertyValueFactory<detalleCompra, Integer>("productoId"));
        nombreProducto.setCellValueFactory(new PropertyValueFactory<detalleCompra, String>("nombreProducto"));
        cantidadProducto.setCellValueFactory(new PropertyValueFactory<detalleCompra, Integer>("cantidad"));
        costoProducto.setCellValueFactory(new PropertyValueFactory<detalleCompra, ObjectProperty<BigDecimal>>("costoUnitario"));
        costoTotal.setCellValueFactory(new PropertyValueFactory<detalleCompra, ObjectProperty<BigDecimal>>("total"));
        
        //prueba();
        
        
        //Evt editar button
        editar.setOnAction(e -> {
            new CompraFormController()
                    .load(new CompraFormController())
                    .setDataView(txtCodigoCompra.getText(), this)
                    .showAfterLoad();
        });
    }
    
    
    public void setInfo(String id){
        compra data = new compra().Detalle(id);
        permisos p = new permisos().get(sqlite.getUser());
        if (data != null){
            editar.setVisible(true);

            txtCodigoCompra.setText(data.getId());
            getProveedor(data.getProveedorId());
            txtFechaCompra.setText(data.getFechaRealizacion().toString());
            txtTipoPago.setText(Items.tipoPagoVenta().get(data.getTipoPago()));
            txtTotalCompra.setText(data.getTotal().toString());
            labelFechaModificacion.setText(data.getFechaModificacion());
            listarDetalle(data.getId());
            
            if(p.getOeditar() == 0) editar.setVisible(false);
        }
        else
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
    }
    
    
    private void getProveedor(int id){
        Object value = c.readerScalar("select Nombre from Proveedor where ID="+id, String.class);
        if (value != null) txtProveedor.setText((String)value);
    }
    
    
    private void listarDetalle(String id){
        try{
            detalleCompra.setItems(new detalleCompra().Mostrar(id));
        }catch(Exception ex){System.out.println("Error cargar detalle compra\n"+ex.getMessage());}
    }
    
    
    private void prueba(){
        detalleCompra d = new detalleCompra();
        d.setProductoId(1);
        d.setNombreProducto("Manzana");
        d.setCantidad(5);
        d.setCostoUnitario(new BigDecimal(20.50));
        d.setTotal(new BigDecimal(125));
        d.setCompraId("codigo");
        
        detalleCompra d2 = new detalleCompra();
        d2.setProductoId(1);
        d2.setNombreProducto("Manzana");
        d2.setCantidad(5);
        d2.setCostoUnitario(new BigDecimal(20.50));
        d2.setTotal(new BigDecimal(125));
        d2.setCompraId("codigo2");
            
        detalleCompra d3 = new detalleCompra();
        d3.setProductoId(1);
        d3.setNombreProducto("Manzana");
        d3.setCantidad(5);
        d3.setCostoUnitario(new BigDecimal(20.50));
        d3.setTotal(new BigDecimal(125));
        d3.setCompraId("codigo3");
        
        detalleCompra.getItems().add(d);
        detalleCompra.getItems().add(d2);
        detalleCompra.getItems().add(d3);
    }
    
}
