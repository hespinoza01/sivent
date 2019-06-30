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
import sivent.utilities.Items;

/**
 *
 * @author hespinoza
 */
public class RecaudacionCompras {
    private final StringProperty idcompra = new SimpleStringProperty("N/A");
    private final StringProperty idproveedor = new SimpleStringProperty("N/A");
    private final StringProperty tipopago = new SimpleStringProperty("N/A");
    private final ObjectProperty<BigDecimal> total = new SimpleObjectProperty(new BigDecimal(0));
    conexion c = new conexion();
    
    public RecaudacionCompras(){}
    

    public String getIdcompra() { return idcompra.get(); }
    public void setIdcompra(String value) { idcompra.set(value); }
    public StringProperty getIdcompraProperty() { return idcompra; }
    
    
    public String getIdproveedor() { return idproveedor.get(); }
    public void setIdproveedor(String value) { idproveedor.set(value); }
    public StringProperty getIdproveedorProperty() { return idproveedor; }
    
    
    public String getTipopago() { return tipopago.get(); }
    public void setTipopago(String value) { tipopago.set(value); }
    public StringProperty getTipopagoProperty() { return tipopago; }
    
    
    public BigDecimal getTotal() { return total.get(); }
    public void setTotal(BigDecimal value) { total.set(value); }
    public ObjectProperty<BigDecimal> totalProperty() { return total; }
   
    
    public ObservableList<RecaudacionCompras> get(Date inicio, Date fin){
        ObservableList<RecaudacionCompras> value = FXCollections.observableArrayList();
        param[] parametros = new param[]{
            new param("date", "_inicio", inicio),
            new param("date", "_fin", fin)
        };
        try{
            ResultSet rs = c.reader("Reporte_ventas", parametros);
            while(rs.next()){
                RecaudacionCompras item = new RecaudacionCompras();
                item.setIdcompra(rs.getString(1));
                item.setIdproveedor(rs.getString(2));
                item.setTipopago(Items.tipoPagoVenta().get(rs.getInt(3)));
                item.setTotal(rs.getBigDecimal(4));
                
                value.add(item);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return value;
    }
}
