/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import DB.conexion;
import DB.param;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author hespinoza
 */
public class RecaudacionVentas {
    private final StringProperty idventa = new SimpleStringProperty("N/A");
	private final StringProperty idcliente = new SimpleStringProperty("N/A");
	private final StringProperty tipoventa = new SimpleStringProperty("N/A");
	private final ObjectProperty<BigDecimal> subtotal = new SimpleObjectProperty(new BigDecimal(0));
	private final ObjectProperty<BigDecimal> impuesto = new SimpleObjectProperty(new BigDecimal(0));
	private final ObjectProperty<BigDecimal> descuento = new SimpleObjectProperty(new BigDecimal(0));
    private final ObjectProperty<BigDecimal> total = new SimpleObjectProperty(new BigDecimal(0));
    
    conexion c = new conexion();
    
    
    public RecaudacionVentas(){}
    

    public String getIdventa() { return idventa.get(); }
    public void setIdventa(String value) { idventa.set(value); }
    public StringProperty getIdventaProperty() { return idventa; }
    
    
    public String getIdcliente() { return idcliente.get(); }
    public void setIdcliente(String value) { idcliente.set(value); }
    public StringProperty getIdclienteProperty() { return idcliente; }
    
    
    public String getTipoventa() { return tipoventa.get(); }
    public void setTipoventa(String value) { tipoventa.set(value); }
    public StringProperty getTipoventaProperty() { return tipoventa; }
    
    
    public BigDecimal getSubtotal() { return subtotal.get(); }
    public void setSubtotal(BigDecimal value) { subtotal.set(value); }
    public ObjectProperty<BigDecimal> getSubtotalProperty() { return subtotal; }
    
    
    public BigDecimal getImpuesto() { return impuesto.get(); }
    public void setImpuesto(BigDecimal value) { impuesto.set(value); }
    public ObjectProperty<BigDecimal> getImpuestoProperty() { return impuesto; }
    
    
    public BigDecimal getDescuento() { return descuento.get(); }
    public void setDescuento(BigDecimal value) { descuento.set(value); }
    public ObjectProperty<BigDecimal> getDescuentoProperty() { return descuento; }
    
    
    public BigDecimal getTotal() { return total.get(); }
    public void setTotal(BigDecimal value) { total.set(value); }
    public ObjectProperty<BigDecimal> getTotalProperty() { return total; }
    
    
    public ObservableList<RecaudacionVentas> get(Date inicio, Date fin){
        ObservableList<RecaudacionVentas> value = FXCollections.observableArrayList();
        param[] parametros = new param[]{
            new param("date", "_inicio", inicio),
            new param("date", "_fin", fin)
        };
        try{
            ResultSet rs = c.reader("Reporte_ventas", parametros);
            while(rs.next()){
                RecaudacionVentas item = new RecaudacionVentas();
                item.setIdventa(rs.getString(1));
                item.setIdcliente(rs.getString(2));
                item.setTipoventa(rs.getInt(3)==1 ? "Crédito" : "Contado");
                item.setSubtotal(rs.getBigDecimal(4));
                item.setImpuesto(rs.getBigDecimal(5));
                item.setTotal(rs.getBigDecimal(6));
                
                value.add(item);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return value;
    }

}
