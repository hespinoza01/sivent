/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import DB.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author hespinoza
 */
public class ExistenciaProducto {
    private final IntegerProperty idproducto = new SimpleIntegerProperty(0);
    private final StringProperty nombre = new SimpleStringProperty("N/A");
    private final StringProperty categoria = new SimpleStringProperty("N/A");
    private final IntegerProperty stock = new SimpleIntegerProperty(0);
    conexion c = new conexion();
    
    public ExistenciaProducto(){}
    

    public int getIdproducto() { return idproducto.get(); }
    public void setIdproducto(int value) { idproducto.set(value); }
    public IntegerProperty getIdproductoProperty() { return idproducto; }
    
    
    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty getNombreProperty() { return nombre; }
    
    
    public String getCategoria() { return categoria.get(); }
    public void setCategoria(String value) { categoria.set(value); }
    public StringProperty getCategoriaProperty() { return categoria; }
    
    
    public int getStock() { return stock.get(); }
    public void setStock(int value) { stock.set(value); }
    public IntegerProperty getStockProperty() { return stock; }
   
    
    public ObservableList<ExistenciaProducto> get(){
        ObservableList<ExistenciaProducto> value = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Existencia_Producto");
            while(rs.next()){
                ExistenciaProducto item = new ExistenciaProducto();
                item.setIdproducto(rs.getInt(1));
                item.setNombre(rs.getString(2));
                item.setCategoria(rs.getString(3));
                item.setStock(rs.getInt(4));
                
                value.add(item);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return value;
    }
}
