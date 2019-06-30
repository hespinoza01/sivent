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
public class departamento {
    private final IntegerProperty id = new SimpleIntegerProperty(0);
    private final StringProperty nombre = new SimpleStringProperty("N/A");
    private final StringProperty nombreGrupo = new SimpleStringProperty("N/A");
    private final IntegerProperty estado = new SimpleIntegerProperty(0);
    private final StringProperty fechaCreacion = new SimpleStringProperty("N/A");
    private final StringProperty fechaModificacion = new SimpleStringProperty("N/A");
    conexion c = new conexion();
    
    public departamento(){}

    
    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }
    public IntegerProperty getIdProperty() { return id; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty getNombreProperty() { return nombre; }

    public String getNombreGrupo() { return nombreGrupo.get(); }
    public void setNombreGrupo(String value) { nombreGrupo.set(value); }
    public StringProperty getNombreGrupoProperty() { return nombreGrupo; }

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
            new param ("int", "_ID", getId()),
            new param("str", "_Nombre", getNombre()),
            new param("str", "_NombreGrupo", getNombreGrupo()),
            new param("int", "_Estado", getEstado())
        };

        return c.execute("Insertar_Departamento", Parametros);
    }

    public boolean Editar(){
        param[] Parametros = new param[]{
            new param ("int", "_ID", getId()),
            new param("str", "_Nombre", getNombre()),
            new param("str", "_NombreGrupo", getNombreGrupo()),
            new param("int", "_Estado", getEstado())
        };

        return c.execute("Actualizar_Departamento", Parametros);
    }

    public ObservableList<SearchResult> Buscar(String valor, int clave){
        param[] Parametros = new param[]{
            new param("str", "_valor", valor),
            new param("int", "_clave", clave)
        };
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Busqueda_Departamento", Parametros);
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
            ResultSet rs = c.reader("Mostrar_Departamentos");
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

    public departamento Detalle(int idvalue){
        param[] Param = new param[]{ new param("int", "_ID", idvalue) };
        departamento t = new departamento();
        try{
            ResultSet rs = c.reader("Detalle_Departamento", Param);
            while(rs.next()){
                t.setId(rs.getInt("ID"));
                t.setNombre("Nombre");
                t.setNombreGrupo(rs.getString("NombreGrupo"));
                t.setEstado(rs.getInt("Estado"));
                t.setFechaCreacion(rs.getString("FechaCreacion"));
                t.setFechaModificacion(rs.getString("FechaModificacion"));
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return t;
    }
}
