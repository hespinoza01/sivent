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
import objetos.detalleVenta;
import objetos.permisos;
import objetos.venta;
import sivent.utilities.Items;
import sivent.utilities.Popup;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class DetalleVentaController implements Initializable {

    @FXML private Button editar;
    @FXML private Label EstadoValue;
    @FXML private Label labelFechaModificacion;
    @FXML private TextField txtCliente;
    @FXML private TextField txtCodigoVenta;
    @FXML private TableView<detalleVenta> detalleVenta;
    @FXML private TableColumn<detalleVenta, Integer> idProducto;
    @FXML private TableColumn<detalleVenta, String> nombreProducto;
    @FXML private TableColumn<detalleVenta, Integer> cantidadProducto;
    @FXML private TableColumn<detalleVenta, ObjectProperty<BigDecimal>> precioProducto;
    @FXML private TableColumn<detalleVenta, ObjectProperty<BigDecimal>> valorDescuentoProducto;
    @FXML private TableColumn<detalleVenta, ObjectProperty<BigDecimal>> impuestoProducto;
    @FXML private TextField txtFechaVenta;
    @FXML private TextField txtTipoPago;
    @FXML private TextField txtTipoVenta;
    @FXML private Label labelTotal;
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
        labelTotal.setText("");
        editar.setVisible(false);
        EstadoValueContent.setVisible(false);
        
        idProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, Integer>("productoId"));
        nombreProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, String>("nombreProducto"));
        cantidadProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, Integer>("cantidad"));
        precioProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, ObjectProperty<BigDecimal>>("precioUnitario"));
        valorDescuentoProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, ObjectProperty<BigDecimal>>("descuento"));
        impuestoProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, ObjectProperty<BigDecimal>>("impuesto"));
        //prueba();
        
        //Evt editar button
        editar.setOnAction(e -> {
            new VentaFormController()
                    .load(new VentaFormController())
                    .setDataView(txtCodigoVenta.getText(), this)
                    .showAfterLoad();
        });
    }
    
    
    public void setInfo(String id){
        venta data = new venta().Detalle(id);
        permisos p = new permisos().get(sqlite.getUser());

        if (data != null){
            editar.setVisible(true);

            txtCodigoVenta.setText(data.getId());
            txtCliente.setText((String)c.readerScalar("select Nombre from Cliente where ID='"+id+"'", String.class));
            txtFechaVenta.setText(data.getFechaRealizacion().toString());
            txtTipoPago.setText(Items.tipoPagoVenta().get(data.getTipoPago()));
            txtTipoVenta.setText((data.getTipoVenta() == 0) ? "Contado" : "Crédito");
            labelTotal.setText(data.getTotal().toString());
            labelFechaModificacion.setText(data.getFechaModificacion());
            listarDetalle(data.getId());
            
            if(p.getOeditar() == 0) editar.setVisible(false);
        }
        else
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
    }
    
    
    private void listarDetalle(String id){
        try{
            detalleVenta.setItems(new detalleVenta().Mostrar(id));
        }catch(Exception ex){System.out.println("Error cargar detalle venta\n"+ex.getMessage());}
    }
     
     
    private void prueba(){
        detalleVenta d = new detalleVenta();
        d.setProductoId(1);
        d.setNombreProducto("Manzana");
        d.setCantidad(5);
        d.setPrecioUnitario(new BigDecimal(25));
        d.setDescuento(new BigDecimal(5.5));
        d.setImpuesto(new BigDecimal((d.getPrecioUnitario().doubleValue() * d.getCantidad()) * 0.15));
        d.setTotal(new BigDecimal(d.getCantidad() * d.getPrecioUnitario().doubleValue() - d.getDescuento().doubleValue() + d.getImpuesto().doubleValue()));
        
        detalleVenta d2 = new detalleVenta();
        d2.setProductoId(1);
        d2.setNombreProducto("Manzana");
        d2.setCantidad(5);
        d2.setPrecioUnitario(new BigDecimal(25));
        d2.setDescuento(new BigDecimal(5.5));
        d2.setImpuesto(new BigDecimal((d2.getPrecioUnitario().doubleValue() * d2.getCantidad()) * 0.15));
        d2.setTotal(new BigDecimal(d2.getCantidad() * d2.getPrecioUnitario().doubleValue() - d2.getDescuento().doubleValue() + d2.getImpuesto().doubleValue()));
        
        detalleVenta d3 = new detalleVenta();
        d3.setProductoId(1);
        d3.setNombreProducto("Manzana");
        d3.setCantidad(5);
        d3.setPrecioUnitario(new BigDecimal(25));
        d3.setDescuento(new BigDecimal(5.5));
        d3.setImpuesto(new BigDecimal((d3.getPrecioUnitario().doubleValue() * d3.getCantidad()) * 0.15));
        d3.setTotal(new BigDecimal(d3.getCantidad() * d3.getPrecioUnitario().doubleValue() - d3.getDescuento().doubleValue() + d3.getImpuesto().doubleValue()));
        
        detalleVenta.getItems().addAll(d, d2, d3);
    }
    
}
