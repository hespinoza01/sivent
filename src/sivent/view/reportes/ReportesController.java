/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.reportes;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objetos.ExistenciaProducto;
import objetos.RecaudacionCompras;
import objetos.RecaudacionProducto;
import objetos.RecaudacionVentas;
import sivent.utilities.valueDate;

/**
 * FXML Controller class
 *
 * @author hespinoza
 */
public class ReportesController implements Initializable {

	@FXML private TableView<RecaudacionVentas> recaudacionventas;
	    @FXML private TableColumn<RecaudacionVentas, String> idventaRV;
	    @FXML private TableColumn<RecaudacionVentas, String> idclienteRV;
	    @FXML private TableColumn<RecaudacionVentas, String> tipoventaRV;
	    @FXML private TableColumn<RecaudacionVentas, ObjectProperty<BigDecimal>> subtotalRV;
	    @FXML private TableColumn<RecaudacionVentas, ObjectProperty<BigDecimal>> impuestoRV;
	    @FXML private TableColumn<RecaudacionVentas, ObjectProperty<BigDecimal>> descuentoRV;
	    @FXML private TableColumn<RecaudacionVentas, ObjectProperty<BigDecimal>> totalRV;
    @FXML private DatePicker fechainicioRV;
    @FXML private DatePicker fechafinRV;
    @FXML private TableView<RecaudacionCompras> recaudacionCompra;
	    @FXML private TableColumn<RecaudacionCompras, String> idcompraRC;
	    @FXML private TableColumn<RecaudacionCompras, String> idproveedorRC;
	    @FXML private TableColumn<RecaudacionCompras, String> tipopagoRC;
	    @FXML private TableColumn<RecaudacionCompras, ObjectProperty<BigDecimal>> totalRC;
    @FXML private DatePicker fechainicioRC;
    @FXML private DatePicker fechafinRC;
    @FXML private TableView<ExistenciaProducto> existenciaProducto;
	    @FXML private TableColumn<ExistenciaProducto, Integer> idproductoEP;
	    @FXML private TableColumn<ExistenciaProducto, String> nombreEP;
	    @FXML private TableColumn<ExistenciaProducto, String> categoriaEP;
	    @FXML private TableColumn<ExistenciaProducto, Integer> stockEP;
    @FXML private DatePicker fechainicioRP;
    @FXML private DatePicker fechafinRP;
    @FXML private TableView<RecaudacionProducto> recaudacionProducto;
	    @FXML private TableColumn<RecaudacionProducto, Integer> idproductoRP;
	    @FXML private TableColumn<RecaudacionProducto, String> nombreRP;
	    @FXML private TableColumn<RecaudacionProducto, String> categoriaRP;
	    @FXML private TableColumn<RecaudacionProducto, Integer> unidadesvendidasRP;
	    @FXML private TableColumn<RecaudacionProducto, ObjectProperty<BigDecimal>> recaudacionRP;
    @FXML private TabPane navegacion;
    @FXML private Tab tabRV;
    @FXML private Tab tabEC;
    @FXML private Tab tabEP;
    @FXML private Tab tabRP;
    @FXML private Label sumatoriaTotalRV;
    @FXML private Label sumatoriaTotalRC;
    @FXML private Label sumatoriaTotalRP;
    
    String activa = "";
    double sum = 0;
    
    public ReportesController(){ activa="tabRV"; }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    private void inicia(){
        sumatoriaTotalRV.setText("0");
        sumatoriaTotalRC.setText("0");
        sumatoriaTotalRP.setText("0");
        
        
        // Recaudacion ventas
        fechainicioRV.setValue(LocalDate.now());
        fechafinRV.setValue(LocalDate.now());
        idventaRV.setCellValueFactory(cell -> cell.getValue().getIdventaProperty());
	    idclienteRV.setCellValueFactory(cell -> cell.getValue().getIdclienteProperty());
	    tipoventaRV.setCellValueFactory(cell -> cell.getValue().getTipoventaProperty());
	    subtotalRV.setCellValueFactory(new PropertyValueFactory<RecaudacionVentas, ObjectProperty<BigDecimal>>("subtotal"));
	    impuestoRV.setCellValueFactory(new PropertyValueFactory<RecaudacionVentas, ObjectProperty<BigDecimal>>("impuesto"));
	    descuentoRV.setCellValueFactory(new PropertyValueFactory<RecaudacionVentas, ObjectProperty<BigDecimal>>("descuento"));
	    totalRV.setCellValueFactory(new PropertyValueFactory<RecaudacionVentas, ObjectProperty<BigDecimal>>("total"));
        
        
        //Recaudacion Compra
        fechainicioRC.setValue(LocalDate.now());
        fechafinRC.setValue(LocalDate.now());
        idcompraRC.setCellValueFactory(cell -> cell.getValue().getIdcompraProperty());
	    idproveedorRC.setCellValueFactory(cell -> cell.getValue().getIdproveedorProperty());
	    tipopagoRC.setCellValueFactory(cell -> cell.getValue().getTipopagoProperty());
	    totalRC.setCellValueFactory(new PropertyValueFactory<RecaudacionCompras, ObjectProperty<BigDecimal>>("total"));
        
        
        // Existencia producto
        idproductoEP.setCellValueFactory(new PropertyValueFactory<ExistenciaProducto, Integer>("idproducto"));
	    nombreEP.setCellValueFactory(cell -> cell.getValue().getNombreProperty());
	    categoriaEP.setCellValueFactory(cell -> cell.getValue().getCategoriaProperty());
	    stockEP.setCellValueFactory(new PropertyValueFactory<ExistenciaProducto, Integer>("stock"));
        
        
        // Recaudación producto
        fechainicioRP.setValue(LocalDate.now());
        fechafinRP.setValue(LocalDate.now());
        idproductoRP.setCellValueFactory(new PropertyValueFactory<RecaudacionProducto, Integer>("idproducto"));
	    nombreRP.setCellValueFactory(cell -> cell.getValue().getNombreProperty());
	    categoriaRP.setCellValueFactory(cell -> cell.getValue().getCategoriaProperty());
	    unidadesvendidasRP.setCellValueFactory(new PropertyValueFactory<RecaudacionProducto, Integer>("unidadesVendidas"));
	    recaudacionRP.setCellValueFactory(new PropertyValueFactory<RecaudacionProducto, ObjectProperty<BigDecimal>>("recaudacion"));
        
        
        // Evt navegacion listener
        navegacion.getSelectionModel().selectedItemProperty().addListener((evt, Old, New) -> {
            activa = New.getId(); 
            cargar();
        });
        
        
        //Evt value change
        fechainicioRV.valueProperty().addListener((evt, Old, New) -> { cargar(); });
        fechafinRV.valueProperty().addListener((evt, Old, New) -> { cargar(); });
        
        fechainicioRC.valueProperty().addListener((evt, Old, New) -> { cargar(); });
        fechafinRC.valueProperty().addListener((evt, Old, New) -> { cargar(); });
        
        fechainicioRP.valueProperty().addListener((evt, Old, New) -> { cargar(); });
        fechafinRP.valueProperty().addListener((evt, Old, New) -> { cargar(); });
    }
    
    
    public void cargar(){
        try{
            sum = 0;
        switch(activa){
            case "tabRV":
                recaudacionventas.setItems(new RecaudacionVentas().get(valueDate.setValue(fechainicioRV.getValue()), valueDate.setValue(fechafinRV.getValue())));
                for(RecaudacionVentas i : recaudacionventas.getItems()){
                    sum += i.getTotal().doubleValue(); }
                sumatoriaTotalRV.setText(Double.toString(sum));
                break;
                
            case "tabEC":
                recaudacionCompra.setItems(new RecaudacionCompras().get(valueDate.setValue(fechainicioRC.getValue()), valueDate.setValue(fechafinRC.getValue())));
                for(RecaudacionCompras i : recaudacionCompra.getItems()){
                    sum += i.getTotal().doubleValue(); }
                sumatoriaTotalRC.setText(Double.toString(sum));
                break;
                
            case "tabEP":
                existenciaProducto.setItems(new ExistenciaProducto().get());
                break;
                
            case "tabRP":
                recaudacionProducto.setItems(new RecaudacionProducto().get(valueDate.setValue(fechainicioRP.getValue()), valueDate.setValue(fechafinRP.getValue())));
                for(RecaudacionProducto i : recaudacionProducto.getItems()){
                    sum += i.getRecaudacion().doubleValue(); }
                sumatoriaTotalRP.setText(Double.toString(sum));
                break;
        }
        }catch(Exception ex){}
    }
    
    
}
