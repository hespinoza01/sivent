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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author hespinoza
 */
public class RecaudacionProducto {
    private final IntegerProperty idproducto = new SimpleIntegerProperty(0);
    private final StringProperty nombre = new SimpleStringProperty("N/A");
    private final StringProperty categoria = new SimpleStringProperty("N/A");
    private final IntegerProperty unidadesVendidas = new SimpleIntegerProperty(0);
    private final ObjectProperty<BigDecimal> recaudacion = new SimpleObjectProperty(new BigDecimal(0));
    conexion c = new conexion();
    
    public RecaudacionProducto(){}
    

    public int getIdproducto() { return idproducto.get(); }
    public void setIdproducto(int value) { idproducto.set(value); }
    public IntegerProperty getIdproductoProperty() { return idproducto; }
    
    
    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty getNombreProperty() { return nombre; }
    
    
    public String getCategoria() { return categoria.get(); }
    public void setCategoria(String value) { categoria.set(value); }
    public StringProperty getCategoriaProperty() { return categoria; }
    
    
    public int getUnidadesVendidas() { return unidadesVendidas.get(); }
    public void setUnidadesVendidas(int value) { unidadesVendidas.set(value); }
    public IntegerProperty getUnidadesVendidasProperty() { return unidadesVendidas; }
    
    
    public BigDecimal getRecaudacion() { return recaudacion.get(); }
    public void setRecaudacion(BigDecimal value) { recaudacion.set(value); }
    public ObjectProperty<BigDecimal> getRecaudacionProperty() { return recaudacion; }
   
    
    public ObservableList<RecaudacionProducto> get(Date inicio, Date fin){
        ObservableList<RecaudacionProducto> value = FXCollections.observableArrayList();
        param[] parametros = new param[]{
            new param("date", "_inicio", inicio),
            new param("date", "_fin", fin)
        };
        try{
            ResultSet rs = c.reader("Recaudacion_Producto", parametros);
            while(rs.next()){
                RecaudacionProducto item = new RecaudacionProducto();
                item.setIdproducto(rs.getInt(1));
                item.setNombre(rs.getString(2));
                item.setCategoria(rs.getString(3));
                item.setUnidadesVendidas(rs.getInt(4));
                item.setRecaudacion(rs.getBigDecimal(5));
                
                value.add(item);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return value;
    }
}
