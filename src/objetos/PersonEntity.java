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
 * @author hespinoza
 */
public abstract class PersonEntity<T> {
    private T id;
    private final StringProperty nombre = new SimpleStringProperty("N/A");
    private final StringProperty nombrecontacto = new SimpleStringProperty("N/A");
    private final StringProperty titulocontacto = new SimpleStringProperty("N/A");
    private final StringProperty domicilio = new SimpleStringProperty("N/A");
    private final StringProperty ciudad = new SimpleStringProperty("N/A"); 
    private final StringProperty telefono = new SimpleStringProperty("N/A");
    private final StringProperty email = new SimpleStringProperty("N/A");
    private final IntegerProperty estado = new SimpleIntegerProperty(0);
    private final StringProperty fechamodificacion = new SimpleStringProperty("N/A");
    
    conexion c = new conexion();
    
    String __TableName__;
    String __IdTypeValue__;
    String __ShowProcedure__;
    
    public PersonEntity(String TableName, String IdTypeValue, String ShowProcedure){
        this.__TableName__ = TableName;
        this.__IdTypeValue__ = IdTypeValue;
        this.__ShowProcedure__ = ShowProcedure;
    }
    
    public void setId(T v){ this.id = v; }
    public T getId(){ return this.id; }
    
    public String getNombre(){ return this.nombre.get(); }
    public void setNombre(String v){ this.nombre.set(v); }
    public StringProperty getNombreProperty(){ return this.nombre; }
    
    
    public String getNombreContacto(){ return this.nombrecontacto.get(); }
    public void setNombreContacto(String v){ this.nombrecontacto.set(v); }
    public StringProperty getNombreContactoProperty(){ return this.nombrecontacto; }
    
    
    public String getTituloContacto(){ return this.titulocontacto.get(); }
    public void setTituloContacto(String v){ this.titulocontacto.set(v); }
    public StringProperty getTituloContactoProperty(){ return this.titulocontacto; }
    
    
    public String getDomicilio(){ return this.domicilio.get(); }
    public void setDomicilio(String v){ this.domicilio.set(v); }
    public StringProperty getDomicilioProperty(){ return this.domicilio; }
    
    
    public String getCiudad(){ return this.ciudad.get(); }
    public void setCiudad(String v){ this.ciudad.set(v); }
    public StringProperty getCiudadProperty(){ return this.ciudad; }
    
    
    public String getTelefono(){ return this.telefono.get(); }
    public void setTelefono(String v){ this.telefono.set(v); }
    public StringProperty getTelefonoProperty(){ return this.telefono; }
    
    
    public String getEmail(){ return this.email.get(); }
    public void setEmail(String v){ this.email.set(v); }
    public StringProperty getEmailProperty(){ return this.email; }
    
    
    public int getEstado(){ return this.estado.get(); }
    public void setEstado(int v){ this.estado.set(v); }
    public IntegerProperty getEstadoProperty(){ return this.estado; }
    
    
    public String getFechaModificacion(){ return this.fechamodificacion.get(); }
    public void setFechaModificacion(String v){ this.fechamodificacion.set(v); }
    public StringProperty getFechaModificacionProperty(){ return this.fechamodificacion; }
    
    
    /*
    **  CRUD Methods
    */
    public boolean Insertar(){
        param[] Parametros = new param[]{
            new param(this.__IdTypeValue__, "_ID", getId()),
            new param("str", "_Nombre", getNombre()),
            new param("str", "_NombreContacto", getNombreContacto()),
            new param("str", "_TituloContacto", getTituloContacto()),
            new param("str", "_Domicilio", getDomicilio()),
            new param("str", "_Ciudad", getCiudad()),
            new param("str", "_Telefono", getTelefono()),
            new param("str", "_Email", getEmail()),
            new param("int", "_Estado", getEstado())
        };

        return c.execute("Insertar_"+this.__TableName__, Parametros);
    }
    
    public boolean Editar(){
        param[] Parametros = new param[]{
            new param(this.__IdTypeValue__, "_ID", getId()),
            new param("str", "_Nombre", getNombre()),
            new param("str", "_NombreContacto", getNombreContacto()),
            new param("str", "_TituloContacto", getTituloContacto()),
            new param("str", "_Domicilio", getDomicilio()),
            new param("str", "_Ciudad", getCiudad()),
            new param("str", "_Telefono", getTelefono()),
            new param("str", "_Email", getEmail()),
            new param("int", "_Estado", getEstado())
        };

        return c.execute("Actualizar_"+this.__TableName__, Parametros);
    }
    
    public ObservableList<SearchResult> Buscar(String valor, int clave){
        param[] Parametros = new param[]{
            new param("str", "_valor", valor),
            new param("int", "_clave", clave)
        };
        
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("Busqueda_"+this.__TableName__, Parametros);
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(rs.getString("ID"));
                t.setName(rs.getString("Nombre"));
                t.setModificationDate(rs.getString("FechaModificacion"));

                resultado.add(t);
            }
        }catch(SQLException ex){
            System.err.println("Error PersonEntity search method: "+ex.toString());
        }finally{ 
            try{ c.close(); 
            }catch(Exception ex){
                System.err.println("Error: "+ex.toString());
            }
        }
        
        return resultado;
    }
    
    public ObservableList<SearchResult> Mostrar(){
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader(this.__ShowProcedure__);
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(rs.getString("ID"));
                t.setName(rs.getString("Nombre"));
                t.setModificationDate(rs.getString("FechaModificacion"));

                resultado.add(t);
            }
        }catch(SQLException ex){
        }finally{ 
            try{ c.close(); 
            }catch(Exception ex){
                System.err.println("Error: "+ex.toString());
            }
        }
        
        return resultado;
    }
    
}
