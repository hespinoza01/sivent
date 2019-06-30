/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import DB.param;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author espinoza
 */
public class compra extends OperationEntity {
    private final IntegerProperty proveedorId = new SimpleIntegerProperty(0);
    
    public compra(){}

    public int getProveedorId() { return proveedorId.get(); }
    public void setProveedorId(int value) { proveedorId.set(value); }
    public IntegerProperty getProveedorIdProperty() { return proveedorId; }
    
    public boolean Insertar(){
        param[] Parametros = new param[]{
            new param("str", "_ID", getId()),
            new param("int", "_ProveedorID", getProveedorId()),
            new param("date", "_FechaCompra", getFechaRealizacion()),
            new param("int", "_TipoPago", getTipoPago()),
            new param("decimal", "_Monto", getTotal())
        };

        return c.execute("Insertar_Compra", Parametros);
    }

    public boolean Editar(){
        param[] Parametros = new param[]{
            new param("str", "_ID", getId()),
            new param("int", "_ProveedorID", getProveedorId()),
            new param("date", "_FechaCompra", getFechaRealizacion()),
            new param("int", "_TipoPago", getTipoPago()),
            new param("decimal", "_Monto", getTotal())
        };

        return c.execute("Actualizar_Compra", Parametros);
    }


    public ObservableList<SearchResult> Buscar(String valor, int clave){
        param[] Parametros = new param[]{
            new param("str", "_valor", valor),
            new param("int", "_clave", clave)
        };
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Busqueda_Compras", Parametros);
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(rs.getString("ID"));
                t.setName(rs.getDate("FechaCompra").toString());
                t.setModificationDate(rs.getString("FechaModificacion"));
                resultado.add(t);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return resultado;
    }


    public ObservableList<SearchResult> Mostrar(){
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Mostrar_Compras");
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(rs.getString("ID"));
                t.setName(rs.getDate("FechaCompra").toString());
                t.setModificationDate(rs.getString("FechaModificacion"));
                resultado.add(t);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return resultado;
    }

    public compra Detalle(String idvalue){
        param[] Parametros = new param[]{ new param("str", "_ID", idvalue) };
        compra t = new compra();
        try{
            ResultSet rs = c.reader("CompraDetallada", Parametros);
            while(rs.next()){
                t.setId(rs.getString("ID"));
                t.setProveedorId(rs.getInt("ProveedorID"));
                t.setFechaRealizacion(rs.getDate("FechaCompra"));
                t.setTipoPago(rs.getInt("TipoPago"));
                t.setTotal(rs.getBigDecimal("Monto"));
                t.setFechaModificacion(rs.getString("FechaModificacion"));
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return t;
    }
}
