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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author espinoza
 */
public class venta extends OperationEntity {
    
    private final StringProperty clienteId = new SimpleStringProperty("N/A");
    private final IntegerProperty tipoVenta = new SimpleIntegerProperty(0);
    private BigDecimal subTotal = new BigDecimal(0);
    private BigDecimal impuesto = new BigDecimal(0);
    
    conexion c = new conexion();
    
    public venta(){}


    public String getClienteId() { return clienteId.get(); }
    public void setClienteId(String value) { clienteId.set(value); }
    public StringProperty getClienteIdProperty() { return clienteId; }

    public int getTipoVenta() { return tipoVenta.get(); }
    public void setTipoVenta(int value) { tipoVenta.set(value); }
    public IntegerProperty getTipoVentaProperty() { return tipoVenta; }

    public BigDecimal getSubTotal() { return subTotal; }
    public void setSubTotal(BigDecimal value) { subTotal=value; }

    public BigDecimal getImpuesto() { return impuesto; }
    public void setImpuesto(BigDecimal value) { impuesto=value; }
    
    
    public boolean Insertar(){
        param[] Parametros = new param[]{
            new param("str", "_ID", getId()),
            new param("str", "_ClienteID", getClienteId()),
            new param("date", "_FechaVenta", getFechaRealizacion()),
            new param("int", "_TipoPago", getTipoPago()),
            new param("int", "_TipoVenta", getTipoVenta()),
            new param("decimal", "_SubTotal", getSubTotal()),
            new param("decimal", "_Impuesto", getImpuesto()),
            new param("decimal", "_Total", getTotal())
        };

        return c.execute("Insertar_Venta", Parametros);
    }

    public boolean Editar(){
        param[] Parametros = new param[]{
            new param("str", "_ID", getId()),
            new param("str", "_ClienteID", getClienteId()),
            new param("date", "_FechaVenta", getFechaRealizacion()),
            new param("int", "_TipoPago", getTipoPago()),
            new param("int", "_TipoVenta", getTipoVenta()),
            new param("decimal", "_SubTotal", getSubTotal()),
            new param("decimal", "_Impuesto", getImpuesto()),
            new param("decimal", "_Total", getTotal())
        };

        return c.execute("Actualizar_Venta", Parametros);
    }


    public ObservableList<SearchResult> Mostrar(){
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Mostrar_Ventas");
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(rs.getString("ID"));
                t.setName(rs.getDate("FechaVenta").toString());
                t.setModificationDate(rs.getString("FechaModificacion"));
                resultado.add(t);
            }
        }catch(SQLException e){}finally{ try{ c.close(); }catch(SQLException e){} }
        
        return resultado;
    }

    public venta Detalle(String idvalue){
        param[] Parametros = new param[]{ new param("str", "_ID", idvalue) };
        venta t = new venta();
        try{
            ResultSet rs = c.reader("VentaDetallada", Parametros);
            while(rs.next()){
                t.setId(rs.getString("ID"));
                t.setClienteId(rs.getString("ClienteID"));
                t.setFechaRealizacion(rs.getDate("FechaVenta"));
                t.setTipoPago(rs.getInt("TipoPago"));
                t.setTipoVenta(rs.getInt("TipoVenta"));
                t.setSubTotal(rs.getBigDecimal("SubTotal"));
                t.setImpuesto(rs.getBigDecimal("Impuesto"));
                t.setTotal(rs.getBigDecimal("Total"));
                t.setFechaModificacion(rs.getString("FechaModificacion"));
            }
        }catch(SQLException e){}finally{ try{ c.close(); }catch(SQLException e){} }
        
        return t;
    }

    public ObservableList<SearchResult> Buscar(String valor, int clave){
        param[] Parametros = new param[]{
            new param("str", "_valor", valor),
            new param("int", "_clave", clave)
        };
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Busqueda_Ventas", Parametros);
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(rs.getString("ID"));
                t.setName(rs.getDate("FechaVenta").toString());
                t.setModificationDate(rs.getString("FechaModificacion"));
                resultado.add(t);
            }
        }catch(SQLException e){}finally{ try{ c.close(); }catch(SQLException e){} }
        
        return resultado;
    }
    
}
