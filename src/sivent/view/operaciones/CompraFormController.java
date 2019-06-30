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
import objetos.compra;
import objetos.detalleCompra;
import objetos.producto;
import objetos.proveedor;
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
public class CompraFormController extends SicemWindow implements Initializable {

    @FXML private TextField txtProveedor;
    @FXML private TextField txtCodigoCompra;
    @FXML private DatePicker fechaCompra;
    @FXML private ComboBox<String> tipoPago;
    @FXML private TextField txtProducto;
    @FXML private Spinner<Integer> txtCantidadProducto;
    @FXML private TextField txtCostoUnitario;
    @FXML private Button agregarProductoDetalle;
    @FXML private TableView<detalleCompra> detalleCompra;
    @FXML private TableColumn<detalleCompra, Integer> idProducto;
    @FXML private TableColumn<detalleCompra, String> nombreProducto;
    @FXML private TableColumn<detalleCompra, Integer> cantidadProducto;
    @FXML private TableColumn<detalleCompra, ObjectProperty<BigDecimal>> costoProducto;
    @FXML private TableColumn<detalleCompra, ObjectProperty<BigDecimal>> costoTotal;
    @FXML private Button cancelarButton;
    @FXML private Button aceptarButton;
    @FXML private Label txtTotalCompra;
    @FXML private Pane title;
    @FXML private Label titleLbl;
    
    String accionformulario; int cont=1;
    boolean cargainfo = false;
    int idproveedor = -1, idproducto = -1;
    ObservableList<String> cache = FXCollections.observableArrayList();
    DetalleCompraController detalle;
    conexion c = new conexion();
    
    
    public CompraFormController(){ 
        super("/sivent/view/operaciones/compraForm.fxml");
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
        txtTotalCompra.setText("00.00");
        if (accionformulario.equals("crear")){
            txtCodigoCompra.setText(generaCodFactura());
            fechaCompra.setValue(LocalDate.now());
            detalleCompra.setTooltip(new Tooltip("De doble click en una fila para editar el producto del detalle"));
        }
        
        tipoPago.setItems(Items.tipoPagoVenta());
        tipoPago.getSelectionModel().selectFirst();
        
        Dragged.set(title, titleLbl);
        validator.Numeros(txtCostoUnitario); // Se añade la validación para solo números en el campo de costo unitario
        txtCantidadProducto.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 1));
        agregarProductoDetalle.setTooltip(new Tooltip("Agregar producto al detalle de la compra"));
        clearTextboxDetalle();
        
        idProducto.setCellValueFactory(new PropertyValueFactory<detalleCompra, Integer>("productoId"));
        nombreProducto.setCellValueFactory(new PropertyValueFactory<detalleCompra, String>("nombreProducto"));
        cantidadProducto.setCellValueFactory(new PropertyValueFactory<detalleCompra, Integer>("cantidad"));
        costoProducto.setCellValueFactory(new PropertyValueFactory<detalleCompra, ObjectProperty<BigDecimal>>("costoUnitario"));
        costoTotal.setCellValueFactory(new PropertyValueFactory<detalleCompra, ObjectProperty<BigDecimal>>("total"));
        
        
        //Evt cancelar button
        cancelarButton.setOnAction(e -> { close(); });
        
        
        //Evt de escucha para dobleclick con el mouse en una fila de la tabla
        detalleCompra.setRowFactory(tv -> {
            TableRow<detalleCompra> row = new TableRow<>();
            row.setOnMouseClicked(evt -> {
                if(evt.getClickCount() == 2 && !row.isEmpty()){
                    if(accionformulario.equals("crear")){
                        int index = row.getIndex();
                        detalleCompra item = row.getItem();
                        detalleCompra.getItems().remove(index);
                        
                        cargainfo = true;
                        idproducto = item.getProductoId();
                        txtProducto.setText(item.getNombreProducto());
                        txtCantidadProducto.getValueFactory().setValue(item.getCantidad());
                        txtCostoUnitario.setText(item.getCostoUnitario().toString());
                        fillTextboxDetalle();
                        cargainfo = false;
                    }
                }
            });
            
            return row;
        });
        
        
        //Evt txtProveedor change text listener
        txtProveedor.textProperty().addListener((e, Old, New) -> {
            if(New.length()>0 && !cargainfo){
                idproveedor = -1;
                new AutoCompletionTextFieldBinding<>(txtProveedor, SuggestionProvider.create(sugerencia(New)));
            }
        });
        //Evt validar campo Proveedor, si corresponde a uno de los proveedores existentes
        txtProveedor.focusedProperty().addListener((e, Old, New) -> {
            validator.validate(txtProveedor, false);
            if(!New && txtProveedor.getText().length()>0){
                String[] values = txtProveedor.getText().split("-");
                if(values.length != 2 && !cache.contains(txtProveedor.getText())){
                    validator.validate(txtProveedor, true);
                    Popup.warning(
                            "Advertencia", 
                            "Valor ingresado en el campo de 'proveedor' no valido. Por favor, seleccione una de las opciones de la lista de sugerencias");
                }else{
                    cargainfo = true;
                    idproveedor = Integer.parseInt(values[0]);
                    txtProveedor.setText(values[1]);
                    txtProveedor.end();
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
                    fillTextboxDetalle();
                    cargainfo = false;
                }
            }
        });
        
        
        //Evt para coregir el error en java sobre almacenar el valor del spinner cuando es cambiado por el usuario mediante el textfield
        txtCantidadProducto.focusedProperty().addListener((evt, Old, New) -> {
            if(!New) txtCantidadProducto.increment(0);
        });
        
        
        //Evt agregarProductoDetalle
        agregarProductoDetalle.setOnAction(e -> {
            if (idproducto != -1){
                detalleCompra item = new detalleCompra();
                item.setCompraId(txtCodigoCompra.getText());
                item.setProductoId(idproducto);
                item.setNombreProducto(txtProducto.getText());
                item.setCantidad(txtCantidadProducto.getValueFactory().getValue());
                item.setCostoUnitario(new BigDecimal(txtCostoUnitario.getText()));
                double total = item.getCantidad() * item.getCostoUnitario().doubleValue();
                item.setTotal(BigDecimal.valueOf(total));

                idproducto = -1;

                clearTextboxDetalle();
                detalleCompra.getItems().add(item);
                txtTotalCompra.setText(calculoTotalCompra());
            }else
                Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
        });
        
        
        //Evt aceptar button
        aceptarButton.setOnAction(e -> {
            if (idproveedor != -1){
                compra c = new compra();
                c.setId(txtCodigoCompra.getText());
                c.setProveedorId(idproveedor);
                c.setTipoPago(tipoPago.getSelectionModel().getSelectedIndex());
                c.setFechaRealizacion(valueDate.setValue(fechaCompra.getValue()));
                c.setTotal(new BigDecimal(txtTotalCompra.getText()));

                if (accionformulario.equals("crear")) {
                    c.Insertar();
                    saveDetalle(1);
                }
                else {
                    c.Editar();
                    saveDetalle(0);
                    detalle.setInfo(txtCodigoCompra.getText());
                }
                
                close();
            }else
                Popup.error(
                    "Error innesperado", 
                    "Ocurrió un error al tratar de realizar la acción, por favor, intente nuevamente.");
        });
    }
    
    
    public CompraFormController setDataView(String id, DetalleCompraController dc){
        accionformulario = "editar";
        detalle = dc;
        compra data = new compra().Detalle(id);

        if (data != null) {
            cargainfo = true;
            
            txtCodigoCompra.setText(data.getId());
            obtenerProveedor(data.getProveedorId());
            tipoPago.getSelectionModel().select(data.getTipoPago());
            fechaCompra.setValue(valueDate.getValue(data.getFechaRealizacion()));
            txtTotalCompra.setText(data.getTotal().toString());
            getDetalleCompra(txtCodigoCompra.getText());

            cargainfo = false;
        }else{
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente."); 
            close();
        }

        return this;
    }
    
    
    private void obtenerProveedor(int id){
        idproveedor = id;
        txtProveedor.setText((String) c.readerScalar("select Nombre from Producto where ID="+id, String.class));
    }
    
    
    private void getDetalleCompra(String id){
        try{
            detalleCompra.setItems(new detalleCompra().Mostrar(id));
        }catch(Exception ex){System.out.println("Error cargar detalle compra\n"+ex.getMessage());}
    }
    
    
    private void saveDetalle(int tipo){
        for(detalleCompra item : detalleCompra.getItems()){
            item.setCompraId(txtCodigoCompra.getText());
            
            if (tipo == 1) item.Insertar();
            else item.Editar();
        }
    }
    
    
    private ObservableList<String> sugerencia(String value){
        ObservableList<String> data = FXCollections.observableArrayList();
        
        if(txtProveedor.isFocused())
            for(SearchResult i : new proveedor().Buscar(value, 0)){
                data.add(i.getId()+"-"+i.getName()); }
        else
            for(SearchResult i : new producto().Buscar(value, 0)){
                data.add(i.getId()+"-"+i.getName()); }
        
        cache.setAll(data);
        return data;
    }
    
    
    private String generaCodFactura(){
        String codigo;
        LocalTime ta = LocalTime.now();
        LocalDate fa = LocalDate.now();

        codigo = "c" + fa.getDayOfMonth() +""+ fa.getMonthValue() +""+ Integer.toString(fa.getYear()).substring(2, 2);
        codigo += "-" + ta.getHour()+""+ta.getMinute()+""+ta.getSecond();

        return codigo;
    }
    
    
    private void clearTextboxDetalle(){
        txtProducto.clear();
        txtCantidadProducto.getValueFactory().setValue(0);
        txtCostoUnitario.setText("0");
        
        txtCantidadProducto.setEditable(false);
        txtCostoUnitario.setEditable(false);
        agregarProductoDetalle.setVisible(false);
        txtCantidadProducto.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 1));
    }
    
    
    private void fillTextboxDetalle(){
        txtCantidadProducto.setEditable(true);
        txtCostoUnitario.setEditable(true);
        agregarProductoDetalle.setVisible(true);
        txtCantidadProducto.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 1));
    }

    
    private String calculoTotalCompra(){
        double total = 0;
        for(detalleCompra r : detalleCompra.getItems())
            total += r.getTotal().doubleValue();

        return Double.toString(total);
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
