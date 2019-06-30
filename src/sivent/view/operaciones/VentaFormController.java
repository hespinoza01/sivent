/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.operaciones;

import DB.conexion;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import objetos.SearchResult;
import objetos.cliente;
import objetos.detalleVenta;
import objetos.producto;
import objetos.venta;
import sivent.utilities.Dragged;
import sivent.utilities.Items;
import sivent.utilities.Popup;
import sivent.utilities.SicemWindow;
import sivent.utilities.validator;
import sivent.utilities.valueDate;

/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class VentaFormController extends SicemWindow implements Initializable {

    @FXML private TextField txtCliente;
    @FXML private TextField txtCodigoVenta;
    @FXML private DatePicker fechaVenta;
    @FXML private ComboBox<String> tipoPago;
    @FXML private CheckBox ventaCredito;
    @FXML private TextField txtProducto;
    @FXML private Spinner<Integer> cantidadVentaProducto;
    @FXML private TextField txtStock;
    @FXML private TextField txtPrecioVenta;
    @FXML private TextField descuentoProducto;
    @FXML private CheckBox impuestoValue;
    @FXML private Button agregarProductoDetalle;
    @FXML private TableView<detalleVenta> detalleVenta;
    @FXML private TableColumn<detalleVenta, Integer> idProducto;
    @FXML private TableColumn<detalleVenta, String> nombreProducto;
    @FXML private TableColumn<detalleVenta, Integer> cantidadProducto;
    @FXML private TableColumn<detalleVenta, ObjectProperty<BigDecimal>> precioProducto;
    @FXML private TableColumn<detalleVenta, ObjectProperty<BigDecimal>> valorDescuentoProducto;
    @FXML private TableColumn<detalleVenta, ObjectProperty<BigDecimal>> impuestoProducto;
    @FXML private TableColumn<detalleVenta, ObjectProperty<BigDecimal>> costoTotal;
    @FXML private Button cancelarButton;
    @FXML private Button aceptarButton;
    @FXML private Label labelSubtotal;
    @FXML private Label labelImpuesto;
    @FXML private Label labelTotal;
    @FXML private Pane title;
    @FXML private Label titleLbl;
    
    String accionformulario = "crear";
    boolean cargainfo = false;
    int idproducto = -1;
    String idcliente = "";
    double descuento = 0;
    ObservableList<String> cache = FXCollections.observableArrayList();
    DetalleVentaController detalle;
    conexion c = new conexion();
    
    
    public VentaFormController(){ 
        super("/sivent/view/operaciones/ventaForm.fxml");
        accionformulario = "crear"; 
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    private void inicia(){
        agregarProductoDetalle.setVisible(false);
        aceptarButton.setText((accionformulario.equals("crear")) ? "Guardar" : "Actualizar");
        labelSubtotal.setText("00.00");
        labelImpuesto.setText("00.00");
        labelTotal.setText("00.00");
        if(accionformulario.equals("crear")){
            txtCodigoVenta.setText(generaCodFactura());
            fechaVenta.setValue(LocalDate.now());
            detalleVenta.setTooltip(new Tooltip("De doble click en una fila para editar el producto del detalle"));
        }
        
        tipoPago.setItems(Items.tipoPagoVenta());
        tipoPago.getSelectionModel().selectFirst();
        
        Dragged.set(title, titleLbl);
        validator.Numeros(descuentoProducto); // Se añade la validación para solo números en el campo de descuento
        cantidadVentaProducto.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 1));
        agregarProductoDetalle.setTooltip(new Tooltip("Agregar producto al detalle de la compra"));
        clearTextboxDetalle();
        //prueba();
        
        idProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, Integer>("productoId"));
        nombreProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, String>("nombreProducto"));
        cantidadProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, Integer>("cantidad"));
        precioProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, ObjectProperty<BigDecimal>>("precioUnitario"));
        valorDescuentoProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, ObjectProperty<BigDecimal>>("descuento"));
        impuestoProducto.setCellValueFactory(new PropertyValueFactory<detalleVenta, ObjectProperty<BigDecimal>>("impuesto"));
        costoTotal.setCellValueFactory(new PropertyValueFactory<detalleVenta, ObjectProperty<BigDecimal>>("total"));
        
        
        //Evt cancelar button
        cancelarButton.setOnAction(e -> { close(); });
        
        
        //Evt escucha doble click con el mouse a una fila de la tabla
        detalleVenta.setRowFactory(tv -> {
            TableRow<detalleVenta> row = new TableRow<>();
            row.setOnMouseClicked(evt -> {
                if(evt.getClickCount() == 2 && !row.isEmpty()){
                    if(accionformulario.equals("crear")){
                        int index = row.getIndex();
                        detalleVenta item = row.getItem();
                        detalleVenta.getItems().remove(index);
                        
                        cargainfo = true;
                        idproducto = item.getProductoId();
                        txtProducto.setText(item.getNombreProducto());
                        cantidadVentaProducto.getValueFactory().setValue(item.getCantidad());
                        descuentoProducto.setText(item.getDescuento().toString());
                        impuestoValue.setSelected((item.getImpuesto().doubleValue() != 0) ? true : false);
                        fillTextboxDetalle();
                        cargainfo = false;
                    }
                }
            });
            return row;
        });
        
        
        //Evt txtCliente change listener
        txtCliente.textProperty().addListener((evt, Old, New) -> {
            if(New.length()>0 && !cargainfo){
                idcliente = "";
                new AutoCompletionTextFieldBinding<>(txtCliente, SuggestionProvider.create(sugerencia(New)));
            }
        });
        //Evt validar campo Proveedor, si corresponde a uno de los proveedores existentes
        txtCliente.focusedProperty().addListener((e, Old, New) -> {
            validator.validate(txtCliente, false);
            if(!New && txtCliente.getText().length()>0){
                String[] values = txtCliente.getText().split("-");
                if(values.length != 2 && !cache.contains(txtCliente.getText())){
                    validator.validate(txtCliente, true);
                    Popup.warning(
                            "Advertencia", 
                            "Valor ingresado en el campo de 'cliente' no valido. Por favor, seleccione una de las opciones de la lista de sugerencias");
                }else{
                    cargainfo = true;
                    idcliente = values[0];
                    txtCliente.setText(values[1]);
                    txtCliente.end();
                    cargainfo = false;
                }
            }
        });
        
        
        // Evt txtProducto change text listener
        txtProducto.textProperty().addListener((e, Old, New) -> {
            if(New.length()>0 && !cargainfo){
                idproducto = -1;
                new AutoCompletionTextFieldBinding<>(txtProducto, SuggestionProvider.create(sugerencia(New)));
            }
        });
        //Evt validar campo Proveedor, si corresponde a uno de los proveedores existentes
        txtProducto.focusedProperty().addListener((e, Old, New) -> {
            validator.validate(txtProducto, false);
            if(!New && txtProducto.getText().length()>0){
                String[] values = txtProducto.getText().split("-");
                if(values.length != 2 && !cache.contains(txtProducto.getText())){
                    validator.validate(txtProducto, true);
                    Popup.warning(
                            "Advertencia", 
                            "Valor ingresado en el campo de 'producto' no valido. Por favor, seleccione una de las opciones de la lista de sugerencias");
                }else{
                    cargainfo = true;
                    idproducto = Integer.parseInt(values[0]);
                    txtProducto.setText(values[1]);
                    txtProducto.end();
                    setPrecioStock(idproducto);
                    fillTextboxDetalle();
                    cargainfo = false;
                }
            }
        });
        
        
        //Evt para coregir el error en java sobre almacenar el valor del spinner cuando es cambiado por el usuario mediante el textfield
        cantidadVentaProducto.focusedProperty().addListener((evt, Old, New) -> {
            if(!New) cantidadVentaProducto.increment(0);
        });

        
        //Evt agregarProductoDetalle 
        agregarProductoDetalle.setOnAction(e -> {
            if (idproducto != -1){
                detalleVenta item = new detalleVenta();
                item.setVentaId(txtCodigoVenta.getText());
                item.setProductoId(idproducto);
                item.setNombreProducto(txtProducto.getText());
                item.setCantidad(cantidadVentaProducto.getValueFactory().getValue());
                item.setPrecioUnitario(new BigDecimal(txtPrecioVenta.getText()));
                item.setDescuento(new BigDecimal(descuentoProducto.getText()));
                    double impuesto = item.getCantidad() * item.getPrecioUnitario().doubleValue();
                item.setImpuesto(new BigDecimal((impuestoValue.isSelected()) ? impuesto : 0));
                    double total = (item.getCantidad() * item.getPrecioUnitario().doubleValue()) - item.getDescuento().doubleValue() + item.getImpuesto().doubleValue();
                item.setTotal(new BigDecimal(total));
                
                idproducto = -1;
                detalleVenta.getItems().add(item);
                clearTextboxDetalle();
                calculoTotales();
            }
            else
                Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
        });
        
        
        //Evt aceptar button
        aceptarButton.setOnAction(e -> {
            if (idcliente.equals("")){
                venta v = new venta();
                v.setId(txtCodigoVenta.getText());
                v.setClienteId(idcliente);
                v.setTipoPago(tipoPago.getSelectionModel().getSelectedIndex());
                v.setFechaRealizacion(valueDate.setValue(fechaVenta.getValue()));
                v.setTipoVenta((ventaCredito.isSelected()) ? 1 : 0);
                v.setSubTotal(new BigDecimal(labelSubtotal.getText()));
                v.setImpuesto(new BigDecimal(labelImpuesto.getText()));
                v.setTotal(new BigDecimal(labelTotal.getText()));

                if (accionformulario.equals("crear")) {
                    v.Insertar();
                    saveDetalle(1);
                } else {
                    v.Editar();
                    saveDetalle(0);
                    detalle.setInfo(txtCodigoVenta.getText());
                }
                
                close();
            }
            else
                Popup.error(
                    "Error innesperado", 
                    "Ocurrió un error al tratar de realizar la acción, por favor, intente nuevamente.");
        });
    }
    
    
    public VentaFormController setDataView(String id, DetalleVentaController dv){
        accionformulario = "editar";
        detalle = dv;
        venta data = new venta().Detalle(id);

        if (data != null)
        {
            cargainfo = true;

            txtCodigoVenta.setText(data.getId());
            obtenerCliente(data.getClienteId());
            tipoPago.getSelectionModel().select(data.getTipoPago());
            fechaVenta.setValue(valueDate.getValue(data.getFechaRealizacion()));
            ventaCredito.setSelected(data.getTipoVenta() == 0);
            getDetalleVenta(txtCodigoVenta.getText());

            cargainfo = false;
        }
        else
        {
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente."); 
            close();
        }
        
        return this;
    }
    
    
    private void getDetalleVenta(String id){
        try{
            detalleVenta.setItems(new detalleVenta().Mostrar(id));
        }catch(Exception ex){System.out.println("Error cargar detalle venta\n"+ex.getMessage());}
    }
    
    
    private void saveDetalle(int tipo){
        for(detalleVenta i : detalleVenta.getItems()){
            i.setVentaId(txtCodigoVenta.getText());
            
            if (tipo == 1) i.Insertar();
            else i.Editar();
        }
    }
    
    
    private void obtenerCliente(String id){
        idcliente = id;
        txtCliente.setText((String)c.readerScalar("select Nombre from Cliente where ID='"+id+"'", String.class));
    }
    
    
    private ObservableList<String> sugerencia(String value){
        ObservableList<String> data = FXCollections.observableArrayList();
        
        if(txtCliente.isFocused())
            for(SearchResult i : new cliente().Buscar(value, 0)){
                data.add(i.getId()+"-"+i.getName()); }
        else
            for(SearchResult i : new producto().Buscar(value, 0)){
                data.add(i.getId()+"-"+i.getName()); }
        
        cache.setAll(data);
        return data;
    }
    
    
    private void setPrecioStock(int id){
        int stock = (int) c.readerScalar("select Stock from Producto where ID="+id, Integer.class);
        BigDecimal precio = (BigDecimal) c.readerScalar("select PrecioVenta from Producto where ID="+id, BigDecimal.class);
        
        txtStock.setText(Integer.toString(stock));
        txtPrecioVenta.setText(precio.toString());
        cantidadVentaProducto.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, stock, 1));
    }
    
    
    private String generaCodFactura(){
        String codigo;
        LocalTime ta = LocalTime.now();
        LocalDate fa = LocalDate.now();

        codigo = "v" + fa.getDayOfMonth() +""+ fa.getMonthValue() +""+ Integer.toString(fa.getYear()).substring(2, 2);
        codigo += "-" + ta.getHour()+""+ta.getMinute()+""+ta.getSecond();

        return codigo;
    }
    
    
    private void calculoTotales(){
        double subtotal = 0, impuesto = 0;
        for(detalleVenta i : detalleVenta.getItems()) {
            subtotal += (i.getCantidad() * i.getPrecioUnitario().doubleValue()) - i.getDescuento().doubleValue();
            impuesto += i.getImpuesto().doubleValue();
        }
                
        labelSubtotal.setText(Double.toString(subtotal));
        labelImpuesto.setText(Double.toString(impuesto));
        labelTotal.setText(Double.toString(subtotal + impuesto));
    }
    
    
    private void clearTextboxDetalle(){
        txtProducto.clear();
        cantidadVentaProducto.getValueFactory().setValue(0);
        txtStock.setText("0");
        txtPrecioVenta.setText("0");
        descuentoProducto.setText("0");
        impuestoValue.setSelected(false);
        agregarProductoDetalle.setVisible(false);
        
        cantidadVentaProducto.setEditable(false);
        txtStock.setDisable(false);
        txtPrecioVenta.setEditable(false);
        descuentoProducto.setEditable(false);
        impuestoValue.setDisable(true);
        cantidadVentaProducto.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 1));
    }
    
    
    private void fillTextboxDetalle(){
        cantidadVentaProducto.setEditable(true);
        txtStock.setDisable(false);
        txtPrecioVenta.setEditable(true);
        descuentoProducto.setEditable(true);
        impuestoValue.setDisable(false);
        agregarProductoDetalle.setVisible(true);
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
