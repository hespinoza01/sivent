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
public class detalleCompra {
    private final StringProperty compraId = new SimpleStringProperty("N/A");
    private final IntegerProperty productoId = new SimpleIntegerProperty(0);
    private final IntegerProperty cantidad = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> costoUnitario = new SimpleObjectProperty(new BigDecimal(0));
    private ObjectProperty<BigDecimal> total = new SimpleObjectProperty(new BigDecimal(0));
    private StringProperty nombreProducto = new SimpleStringProperty("N/A");
    conexion c = new conexion();
    
    public detalleCompra(){}

    
    public String getCompraId() { return compraId.get(); }
    public void setCompraId(String value) { compraId.set(value); }
    public StringProperty getCompraIdProperty() { return compraId; }

    public int getProductoId() { return productoId.get(); }
    public void setProductoId(int value) { productoId.set(value); }
    public IntegerProperty getProductoIdProperty() { return productoId; }

    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int value) { cantidad.set(value); }
    public IntegerProperty getCantidadProperty() { return cantidad; }

    public BigDecimal getCostoUnitario() { return costoUnitario.get(); }
    public void setCostoUnitario(BigDecimal value) { costoUnitario.set(value); }
    public ObjectProperty<BigDecimal> getCostoUnitarioProperty(){ return costoUnitario; }

    public BigDecimal getTotal() { return total.get(); }
    public void setTotal(BigDecimal value) { total.set(value); }
    public ObjectProperty<BigDecimal> getTotalProperty(){ return total; }
    
    public String getNombreProducto(){ return nombreProducto.get(); }
    public void setNombreProducto(String value){ nombreProducto.set(value); }
    public StringProperty getNombreProductoProperty(){ return nombreProducto; }
    
    
    public boolean Insertar(){
        param[] Parametros = new param[]{
            new param("str", "_CompraID", getCompraId()),
            new param("int", "_ProductoID", getProductoId()),
            new param("int", "_Cantidad", getCantidad()),
            new param("decimal", "_CostoUnitario", getCostoUnitario()),
            new param("decimal", "_Total", getTotal())
        };
        
        return c.execute("Insertar_Detalle_Compra", Parametros);
    }
    
    public boolean Editar(){
        param[] Parametros = new param[]{
            new param("str", "_CompraID", getCompraId()),
            new param("int", "_ProductoID", getProductoId()),
            new param("int", "_Cantidad", getCantidad()),
            new param("decimal", "_CostoUnitario", getCostoUnitario()),
            new param("decimal", "_Total", getTotal())
        };

        return c.execute("Actualizar_Detalle_Compra", Parametros);
    }

    public ObservableList<detalleCompra> Mostrar(String idvalue){
        param[] Parametros = new param[]{ new param("str", "_ID", idvalue) };
        ObservableList<detalleCompra> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Mostrar_Detalle_Compra", Parametros);
            while(rs.next()){
                detalleCompra t = new detalleCompra();
                t.setProductoId(rs.getInt("ID"));
                t.setNombreProducto(rs.getString("Producto"));
                t.setCantidad(rs.getInt("Cantidad"));
                t.setCostoUnitario(rs.getBigDecimal("Costo unitario"));
                t.setTotal(rs.getBigDecimal("Total"));
                
                resultado.add(t);
            }
        }catch(SQLException ex){
            System.out.println("Error carga detalle compra\n"+ex.getMessage());
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return resultado;
    }
}
