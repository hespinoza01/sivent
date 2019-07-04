/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.AggregatePlanning;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sivent.utilities.Validator;

/**
 * FXML Controller class
 *
 * @author hespinoza
 */
public class AggregatePlanningController implements Initializable {
    @FXML private RadioButton tipoPersecucion;
    @FXML private RadioButton tipoConstante;
    @FXML private RadioButton tipoSubcontratacion;
    @FXML private TableView<datosEntity> datosTable;
        @FXML private TableColumn<datosEntity, Integer> periodoColumn;
        @FXML private TableColumn<datosEntity, Integer> demandaColumn;
        @FXML private TableColumn<datosEntity, Integer> diasHabilesColumn;
    @FXML private TextField txtDemanda;
    @FXML private TextField txtDiasHabiles;
    @FXML private Button agregarDato;
    @FXML private TextField txtCostoProduccion;
    @FXML private TextField txtMantenimiento;
    @FXML private TextField txtCostoContratacion;
    @FXML private TextField txtCostoSubcontratacion;
    @FXML private TextField txtCostoDespido;
    @FXML private TextField txtHorasRequeridas;
    @FXML private TextField txtCostoFaltante;
    @FXML private TextField txtCostoHrsNormal;
    @FXML private TextField txtCostoHrsExtras;
    @FXML private TextField txtInventarioInicial;
    @FXML private TextField txtStockSeguridad;
    @FXML private TextField txtFuerzaLaboral;
    @FXML private Button btnResultado;
    @FXML private Button btnLimpiarCampos;
    @FXML private TableView<resultadoEntity> resultadoTable;
        @FXML private TableColumn<resultadoEntity, Integer> periodoResultadoColumn;
        @FXML private TableColumn<resultadoEntity, Integer> requerimientoProdColumn;
        @FXML private TableColumn<resultadoEntity, Integer> diasHabilesResultadoColumn;
        @FXML private TableColumn<resultadoEntity, ObjectProperty<BigDecimal>> hrsProduccionRequeridasColumn;
        @FXML private TableColumn<resultadoEntity, Integer> produccionRealColumn;
        @FXML private TableColumn<resultadoEntity, Integer> unidadesSubcontratadasColumn;
        @FXML private TableColumn<resultadoEntity, ObjectProperty<BigDecimal>> costoUnidadesSubcontratadasColumn;
        @FXML private TableColumn<resultadoEntity, ObjectProperty<BigDecimal>> costoUnidadesProducidasColumn;
        @FXML private TableColumn<resultadoEntity, ObjectProperty<BigDecimal>> totalPeriodoColumn;
    
        datosEntity ItemDatoEntity = new datosEntity();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }
    
    private void  inicia(){
        tableCells();
        validaciones();
        
        agregarDato.disableProperty().bind(
                    txtDemanda.textProperty().isEmpty()
                    .or(txtDiasHabiles.textProperty().isEmpty())
                );
        
        agregarDato.setOnAction(e -> {
            datosEntity item = new datosEntity();
            item
                .setPeriodo((ItemDatoEntity.isNew())? datosTable.getItems().size() + 1 : ItemDatoEntity.getPeriodo())
                .setDemanda(Integer.parseInt(txtDemanda.getText()))
                .setDiasHabiles(Integer.parseInt(txtDiasHabiles.getText()));
            
            if(ItemDatoEntity.isNew()) datosTable.getItems().add(item);
            else datosTable.getItems().add(ItemDatoEntity.getPeriodo()-1, item);
            clearDatosField();
        });
        
        //Evt escucha doble click con el mouse a una fila de la tabla
        datosTable.setRowFactory(tv -> {
            TableRow<datosEntity> row = new TableRow<>();
            row.setOnMouseClicked(evt -> {
                if(evt.getClickCount() == 2 && !row.isEmpty()){
                    int index = row.getIndex();
                    ItemDatoEntity = row.getItem();
                    datosTable.getItems().remove(index);

                    this.txtDemanda.setText(String.valueOf(ItemDatoEntity.getDemanda()));
                    this.txtDiasHabiles.setText(String.valueOf(ItemDatoEntity.getDiasHabiles()));
                }
            });
            return row;
        });
        
        /*btnResultado.disableProperty().bind(
            txtCostoProduccion.textProperty().isEmpty()
            .or(txtMantenimiento.textProperty().isEmpty())
            .or(txtCostoContratacion.textProperty().isEmpty())
            .or(txtCostoSubcontratacion.textProperty().isEmpty())
            .or(txtCostoDespido.textProperty().isEmpty())
            .or(txtHorasRequeridas.textProperty().isEmpty())
            .or(txtCostoFaltante.textProperty().isEmpty())
            .or(txtCostoHrsNormal.textProperty().isEmpty())
            .or(txtCostoHrsExtras.textProperty().isEmpty())
            .or(txtInventarioInicial.textProperty().isEmpty())
            .or(txtStockSeguridad.textProperty().isEmpty())
            .or(txtFuerzaLaboral.textProperty().isEmpty())
        );*/
        
        btnResultado.setOnAction(e -> {
            for(datosEntity row: datosTable.getItems()){
                resultadoEntity item = new resultadoEntity();
                datosEntity dato = row;
                int demanda = dato.getDemanda();
                int ss = demanda * Math.round(Integer.parseInt(txtStockSeguridad.getText()) / 100);

                item.setPeriodo(dato.getPeriodo());
                item.setDiasHabiles(dato.getDiasHabiles());
                item.setHrsProduccionRequeridas(new BigDecimal(dato.getDiasHabiles() * 8 * Integer.parseInt(txtFuerzaLaboral.getText())));
                item.setRequerimientosProduccion(demanda + (demanda * ss));
                item.setproduccionReal(Math.floorDiv(item.getRequirimientosProduccion(), Integer.parseInt(txtHorasRequeridas.getText())));
                item.setUnidadesSubcontratadas((item.getRequirimientosProduccion() - item.getProduccionReal() > 0)? (item.getRequirimientosProduccion() - item.getProduccionReal()) : 0);
                item.setCostoUnidadesSubcontratadas(new BigDecimal(item.getUnidadesSubcontratadas() * Float.parseFloat( txtCostoProduccion.getText())));

                float ctoHNormal = item.getHrsProduccionRequeridas().floatValue() * Float.parseFloat(txtCostoHrsNormal.getText());
                float ctoHExtras = dato.getDiasHabiles() * (Integer.parseInt(txtHorasRequeridas.getText()) - 8) * Float.parseFloat(txtCostoHrsExtras.getText());
                item.setCostoUnidadesProducidas(new BigDecimal(ctoHNormal + ctoHExtras));
                item.setTotalPeriodo(new BigDecimal(item.getCostoUnidadesProducidas().floatValue() + item.getCostoUnidadesSubcontratadas().floatValue()));
                resultadoTable.getItems().add(item);
            }
        });
    }
    
    private void tableCells(){
        periodoColumn.setCellValueFactory(new PropertyValueFactory<datosEntity, Integer>("periodo"));
        demandaColumn.setCellValueFactory(new PropertyValueFactory<datosEntity, Integer>("demanda"));
        diasHabilesColumn.setCellValueFactory(new PropertyValueFactory<datosEntity, Integer>("diasHabiles"));
        
        periodoResultadoColumn.setCellValueFactory(new PropertyValueFactory<resultadoEntity, Integer>("periodo"));
        requerimientoProdColumn.setCellValueFactory(new PropertyValueFactory<resultadoEntity, Integer>("requerimientoProduccion"));
        diasHabilesResultadoColumn.setCellValueFactory(new PropertyValueFactory<resultadoEntity, Integer>("diasHabiles"));
        hrsProduccionRequeridasColumn.setCellValueFactory(new PropertyValueFactory<resultadoEntity, ObjectProperty<BigDecimal>>("hrsProduccionRequeridas"));
        produccionRealColumn.setCellValueFactory(new PropertyValueFactory<resultadoEntity, Integer>("produccionReal"));
        unidadesSubcontratadasColumn.setCellValueFactory(new PropertyValueFactory<resultadoEntity, Integer>("unidadesSubcontratadas"));
        costoUnidadesSubcontratadasColumn.setCellValueFactory(new PropertyValueFactory<resultadoEntity, ObjectProperty<BigDecimal>>("costoUnidadesSubcontratadas"));
        costoUnidadesProducidasColumn.setCellValueFactory(new PropertyValueFactory<resultadoEntity, ObjectProperty<BigDecimal>>("costoUnidadesProducidas"));
        totalPeriodoColumn.setCellValueFactory(new PropertyValueFactory<resultadoEntity, ObjectProperty<BigDecimal>>("totalPeriodo"));
    }
    
    private void clearDatosField(){
        this.ItemDatoEntity.setPeriodo(-1);
        this.ItemDatoEntity.setDemanda(0);
        this.ItemDatoEntity.setDiasHabiles(0);
        this.txtDemanda.clear();
        this.txtDiasHabiles.clear();
    }
    
    private void validaciones(){
        txtDemanda.setOnKeyTyped(e -> { Validator.Number(e); });
        txtDiasHabiles.setOnKeyTyped(e -> { Validator.Number(e); });
        txtCostoProduccion.setOnKeyTyped(e -> { Validator.Decimal(e);});
        txtMantenimiento.setOnKeyTyped(e -> { Validator.Decimal(e);});
        txtCostoContratacion.setOnKeyTyped(e -> { Validator.Decimal(e);});
        txtCostoSubcontratacion.setOnKeyTyped(e -> { Validator.Decimal(e);});
        txtCostoDespido.setOnKeyTyped(e -> { Validator.Decimal(e);});
        txtHorasRequeridas.setOnKeyTyped(e -> { Validator.Decimal(e);});
        txtCostoFaltante.setOnKeyTyped(e -> { Validator.Decimal(e);});
        txtCostoHrsNormal.setOnKeyTyped(e -> { Validator.Decimal(e);});
        txtCostoHrsExtras.setOnKeyTyped(e -> { Validator.Decimal(e);});
        txtInventarioInicial.setOnKeyTyped(e -> { Validator.Number(e); });
        txtStockSeguridad.setOnKeyTyped(e -> { Validator.Number(e); });
        txtFuerzaLaboral.setOnKeyTyped(e -> { Validator.Number(e); });
    }
    
}
