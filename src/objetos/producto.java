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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author espinoza
 */
public class producto {
    private final IntegerProperty id = new SimpleIntegerProperty(0);
    private final StringProperty nombre = new SimpleStringProperty("N/A");
    private final IntegerProperty categoriaId = new SimpleIntegerProperty(0);
    private final IntegerProperty cantidadPorUnidad = new SimpleIntegerProperty(0);
    private BigDecimal precioVenta = new BigDecimal(0);
    private final IntegerProperty stock = new SimpleIntegerProperty(0);
    private final StringProperty descripcion = new SimpleStringProperty("N/A");
    private final IntegerProperty estado = new SimpleIntegerProperty(0);
    private final StringProperty fechaModificacion = new SimpleStringProperty("N/A");
    conexion c = new conexion();
    
    
    public producto(){}


    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }
    public IntegerProperty getIdProperty() { return id; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty getNombreProperty() { return nombre; }

    public int getCategoriaId() { return categoriaId.get(); }
    public void setCategoriaId(int value) { categoriaId.set(value); }
    public IntegerProperty getCategoriaIdProperty() { return categoriaId; }

    public int getCantidadPorUnidad() { return cantidadPorUnidad.get(); }
    public void setCantidadPorUnidad(int value) { cantidadPorUnidad.set(value); }
    public IntegerProperty getCantidadPorUnidadProperty() { return cantidadPorUnidad; }

    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal value) { precioVenta=value; }

    public int getStock() { return stock.get(); }
    public void setStock(int value) { stock.set(value); }
    public IntegerProperty getStockProperty() { return stock; }

    public String getDescripcion() {return descripcion.get(); }
    public void setDescripcion(String value) {descripcion.set(value); }
    public StringProperty getDescripcionProperty() {return descripcion; }

    public int getEstado() { return estado.get(); }
    public void setEstado(int value) { estado.set(value); }
    public IntegerProperty getEstadoProperty() { return estado; }

    public String getFechaModificacion() { return fechaModificacion.get(); }
    public void setFechaModificacion(String value) { fechaModificacion.set(value); }
    public StringProperty getFechaModificacionProperty() { return fechaModificacion; }
    
    
    
    public boolean Insertar(){
        param[] Parametros = new param[]
        {
            new param("int", "_ID", getId()),
            new param("int", "_CategoriaID", getCategoriaId()),
            new param("str", "_Nombre", getNombre()),
            new param("int", "_CantidadPorUnidad", getCantidadPorUnidad()),
            new param("decimal", "_PrecioVenta", getPrecioVenta()),
            new param("int", "_Stock", getStock()),
            new param("str", "_Descripcion", getDescripcion()),
            new param("int", "_Estado", getEstado())
        };

        return c.execute("Insertar_Producto", Parametros);
    }



    public boolean Editar(){
        param[] Parametros = new param[]{
            new param("int", "_ID", getId()),
            new param("int", "_CategoriaID", getCategoriaId()),
            new param("str", "_Nombre", getNombre()),
            new param("int", "_CantidadPorUnidad", getCantidadPorUnidad()),
            new param("decimal", "_PrecioVenta", getPrecioVenta()),
            new param("int", "_Stock", getStock()),
            new param("str", "_Descripcion", getDescripcion()),
            new param("int", "_Estado", getEstado())
        };

        return c.execute("Actualizar_Producto", Parametros);
    }


    public ObservableList<SearchResult> Buscar(String valor, int clave){
       param[] Parametros = new param[]{
            new param("str", "_valor", valor),
            new param("int", "_clave", clave)
        };

        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Busqueda_Productos", Parametros);
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(Integer.toString(rs.getInt("ID")));
                t.setName(rs.getString("Nombre"));
                t.setModificationDate(rs.getString("FechaModificacion"));
                resultado.add(t);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return resultado;
    }

    
    public ObservableList<SearchResult> BuscarTodo(String valor, int clave){
       param[] Parametros = new param[]{
            new param("str", "_valor", valor),
            new param("int", "_clave", clave)
        };

       ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Busqueda_Productos_Todo", Parametros);
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(Integer.toString(rs.getInt("ID")));
                t.setName(rs.getString("Nombre"));
                t.setModificationDate(rs.getString("FechaModificacion"));
                resultado.add(t);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return resultado;
    }

    
    public ObservableList<SearchResult> MostrarHabilitados(){
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs =c.reader("Mostrar_Productos");
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(Integer.toString(rs.getInt("ID")));
                t.setName(rs.getString("Nombre"));
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
            ResultSet rs =c.reader("Mostrar_Todo_Productos");
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(Integer.toString(rs.getInt("ID")));
                t.setName(rs.getString("Nombre"));
                t.setModificationDate(rs.getString("FechaModificacion"));
                resultado.add(t);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return resultado;
    }


    public void InsertarHistorialPrecio(int id, float precio){
        param[] Parametros = new param[]
        {
            new param("int", "_ProductoID", id),
            new param("float", "_Precio", precio)
        };

        c.execute("Insertar_Historial_Precio_Producto", Parametros);
    }


    public ObservableList<SearchResult> HistorialPrecio(int idvalue){
        param[] Parametros = new param[]{ new param("int", "_ID", idvalue) };
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        
        try{
            ResultSet rs = c.reader("Mostrar_HistorialPrecioProducto", Parametros);
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(rs.getDate("FechaInicio").toString());
                t.setName(rs.getDate("FechaFinal").toString());
                t.setModificationDate(rs.getBigDecimal("Precio").toString());
                resultado.add(t);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return resultado;
    }


    public ObservableList<SearchResult> HistorialCosto(int idvalue){
        param[] Parametros = new param[]{ new param("int", "_ID", idvalue) };
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        
        try{
            ResultSet rs = c.reader("Mostrar_HistorialCostoProducto", Parametros);
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(rs.getDate("FechaInicio").toString());
                t.setName(rs.getDate("FechaFinal").toString());
                t.setModificationDate(rs.getBigDecimal("Precio").toString());
                resultado.add(t);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return resultado;
    }


    public void InsertarHistorialCosto(int id, float precio){
        param[] Parametros = new param[]{
            new param("int", "_ProductoID", id),
            new param("float", "_Precio", precio)
        };

        c.execute("Insertar_Historial_Costo_Producto", Parametros);
    }
    
    
    public ObservableList<SearchResult> EntradaProducto(int id){
        param[] Param = new param[] { new param("int", "_ProductoID", id) };
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        
        try{
            ResultSet rs = c.reader("Mostrar_Entrada_Producto", Param);
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(Integer.toString(rs.getInt("Cantidad")));
                t.setName(rs.getBigDecimal("CostoUnitario").toString());
                t.setModificationDate(rs.getString("Fecha"));
                resultado.add(t);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return resultado;
    }
    
    
    public void InsertarHistorialEntrada(int id, int cantidad, float costo){
        param[] Parametros = new param[]{
            new param("int", "_ProductoID", id),
            new param("int", "_Cantidad", cantidad),
            new param("float", "_CostoUnitario", costo)
        };

        c.execute("Insertar_Historial_Entrada_Producto", Parametros);
    }


    public producto Detalle(int idvalue){
        param[] Parametros = new param[]{ new param("int", "_ID", idvalue) };
        producto p = new producto();
        
        try{
            ResultSet rs = c.reader("Detalle_Producto", Parametros);
            while(rs.next()){
                p.setId(rs.getInt("ID"));
                p.setCategoriaId(rs.getInt("CategoriaID"));
                p.setNombre(rs.getString("Nombre"));
                p.setCantidadPorUnidad(rs.getInt("CantidadPorUnidad"));
                p.setPrecioVenta(rs.getBigDecimal("PrecioVenta"));
                p.setStock(rs.getInt("Stock"));
                p.setDescripcion(rs.getString("Descripcion"));
                p.setEstado(rs.getInt("Estado"));
                p.setFechaModificacion(rs.getString("FechaModificacion"));
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return p;
    }
    
}
