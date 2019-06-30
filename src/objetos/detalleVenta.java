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
 * @author espinoza
 */
public class detalleVenta {
    private final StringProperty ventaId = new SimpleStringProperty("N/A");
    private final IntegerProperty productoId = new SimpleIntegerProperty(0);
    private final IntegerProperty cantidad = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> precioUnitario = new SimpleObjectProperty(new BigDecimal(0));
    private ObjectProperty<BigDecimal> descuento = new SimpleObjectProperty(new BigDecimal(0));
    private ObjectProperty<BigDecimal> impuesto = new SimpleObjectProperty(new BigDecimal(0));
    private ObjectProperty<BigDecimal> total = new SimpleObjectProperty(new BigDecimal(0));
    private StringProperty nombreProducto = new SimpleStringProperty("N/A");
    conexion c = new conexion();
    
    
    public detalleVenta(){}


    public String getVentaId() { return ventaId.get(); }
    public void setVentaId(String value) { ventaId.set(value); }
    public StringProperty getVentaIdProperty() { return ventaId; }

    public int getProductoId() { return productoId.get(); }
    public void setProductoId(int value) { productoId.set(value); }
    public IntegerProperty getProductoIdProperty() { return productoId; }

    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int value) { cantidad.set(value); }
    public IntegerProperty getCantidadProperty() { return cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario.get(); }
    public void setPrecioUnitario(BigDecimal value) { precioUnitario.set(value); }
    public ObjectProperty<BigDecimal> getPrecioUnitarioProperty(){ return precioUnitario; }

    public BigDecimal getDescuento() { return descuento.get(); }
    public void setDescuento(BigDecimal value) { descuento.set(value); }
    public ObjectProperty<BigDecimal> getDescuentoProperty(){ return descuento; }

    public BigDecimal getImpuesto() { return impuesto.get(); }
    public void setImpuesto(BigDecimal value) { impuesto.set(value); }
    public ObjectProperty<BigDecimal> getImpuestoProperty(){ return impuesto; }

    public BigDecimal getTotal() { return total.get(); }
    public void setTotal(BigDecimal value) { total.set(value); }
    public ObjectProperty<BigDecimal> getTotalProperty(){ return total; }
    
    public String getNombreProducto(){ return nombreProducto.get(); }
    public void setNombreProducto(String value){ nombreProducto.set(value); }
    public StringProperty getNombreProductoProperty(){ return nombreProducto; }
    
    
    public boolean Insertar(){
        param[] Parametros = new param[]{
            new param("str", "_VentaID", getVentaId()),
            new param("int", "_ProductoID", getProductoId()),
            new param("int", "_Cantidad", getCantidad()),
            new param("decimal", "_PrecioUnitario", getPrecioUnitario()),
            new param("decimal", "_Descuento", getDescuento()),
            new param("decimal", "_Impuesto", getImpuesto()),
            new param("decimal", "_Total", getTotal())
        };

        return c.execute("Insertar_Detalle_Venta", Parametros);
    }

    public boolean Editar()
    {
        param[] Parametros = new param[]{
            new param("str", "_VentaID", getVentaId()),
            new param("int", "_ProductoID", getProductoId()),
            new param("int", "_Cantidad", getCantidad()),
            new param("decimal", "_PrecioUnitario", getPrecioUnitario()),
            new param("decimal", "_Descuento", getDescuento()),
            new param("decimal", "_Impuesto", getImpuesto()),
            new param("decimal", "_Total", getTotal())
        };

        return c.execute("Actualizar_Detalle_Venta", Parametros);
    }

    public ObservableList<detalleVenta> Mostrar(String idvalue){
        param[] Parametros = new param[]{ new param("str", "_VentaID", idvalue) };
        ObservableList<detalleVenta> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Mostrar_Detalle_Venta", Parametros);
            while(rs.next()){
                detalleVenta t = new detalleVenta();
                t.setProductoId(rs.getInt("ID"));
                t.setNombreProducto(rs.getString("Producto"));
                t.setCantidad(rs.getInt("Cantidad"));
                t.setPrecioUnitario(rs.getBigDecimal("Precio unitario"));
                t.setDescuento(rs.getBigDecimal("Descuento"));
                t.setImpuesto(rs.getBigDecimal("Impuesto"));
                t.setTotal(rs.getBigDecimal("Total"));

                resultado.add(t);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return resultado;
    }
}
