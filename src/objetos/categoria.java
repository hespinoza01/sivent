/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import DB.conexion;
import DB.param;
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
public class categoria {
    private final IntegerProperty id = new SimpleIntegerProperty(0);
    private final StringProperty nombre = new SimpleStringProperty("N/A");
    private final StringProperty descripcion = new SimpleStringProperty("N/A");
    private final IntegerProperty estado = new SimpleIntegerProperty(0);
    private final StringProperty fechaCreacion = new SimpleStringProperty("N/A");
    private final StringProperty fechaModificacion = new SimpleStringProperty("N/A");
    conexion c = new conexion();
    
    public categoria(){}

    
    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }
    public IntegerProperty getIdProperty() { return id; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty getNombreProperty() { return nombre; }

    public String getDescripcion() { return descripcion.get(); }
    public void setDescripcion(String value) { descripcion.set(value); }
    public StringProperty getDescripcionProperty() { return descripcion; }

    public int getEstado() { return estado.get(); }
    public void setEstado(int value) { estado.set(value); }
    public IntegerProperty getEstadoProperty() { return estado; }

    public String getFechaCreacion() { return fechaCreacion.get(); }
    public void setFechaCreacion(String value) { fechaCreacion.set(value); }
    public StringProperty getFechaCreacionProperty() { return fechaCreacion; }

    public String getFechaModificacion() { return fechaModificacion.get(); }
    public void setFechaModificacion(String value) { fechaModificacion.set(value); }
    public StringProperty getFechaModificacionProperty() { return fechaModificacion; }
    
    
    public boolean Insertar(){
        param[] Parametros = new param[]{
            new param("int", "_ID", getId()),
            new param("str", "_Nombre", getNombre()),
            new param("str", "_Descripcion", getDescripcion()),
            new param("int", "_Estado", getEstado())
        };

        return c.execute("Insertar_Categoria", Parametros);
    }

    public boolean Editar(){
        param[] Parametros = new param[]{
            new param("int", "_ID", getId()),
            new param("str", "_Nombre", getNombre()),
            new param("str", "_Descripcion", getDescripcion()),
            new param("int", "_Estado", getEstado())
        };

        return c.execute("Actualizar_Categoria", Parametros);
    }

    public ObservableList<SearchResult> Mostrar(){
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Mostrar_Categorias");
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

    public ObservableList<SearchResult> Buscar(String valor){
        param[] Parametros = new param[]{ new param("str", "_valor", valor) };
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Busqueda_Categoria", Parametros);
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

    public categoria Detalle(int idvalue){
        param[] Parametros = new param[]{ new param("int", "_ID", idvalue) };
        categoria t = new categoria();
        try{
            ResultSet rs = c.reader("Detalle_Categoria", Parametros);
            while(rs.next()){
                t.setId(rs.getInt("ID"));
                t.setNombre(rs.getString("Nombre"));
                t.setDescripcion(rs.getString("Descripcion"));
                t.setEstado(rs.getInt("Estado"));
                t.setFechaCreacion(rs.getString("FechaCreacion"));
                t.setFechaModificacion(rs.getString("FechaModificacion"));
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return t;
    }
}
