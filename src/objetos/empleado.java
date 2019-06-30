package objetos;
import DB.conexion;
import DB.param;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author espinoza
 */
public class empleado {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private final StringProperty nombres = new SimpleStringProperty("N/A");
    private final StringProperty apellidos = new SimpleStringProperty("N/A");
    private final StringProperty titulolaboral = new SimpleStringProperty("N/A");
    private final StringProperty domicilio = new SimpleStringProperty("N/A");
    private final StringProperty ciudad = new SimpleStringProperty("N/A");
    private final StringProperty telefono = new SimpleStringProperty("N/A");
    private final StringProperty cedula = new SimpleStringProperty("N/A");
    private final StringProperty email = new SimpleStringProperty("N/A");
    private final StringProperty observaciones = new SimpleStringProperty("N/A");
    private final IntegerProperty departamentoId = new SimpleIntegerProperty(0);
    private final IntegerProperty estadocivil = new SimpleIntegerProperty(0);
    private final IntegerProperty genero = new SimpleIntegerProperty(0);
    private Date fechanacimiento = new Date();
    private Date fechacontratacion = new Date();
    private int estado = 0;
    private int reportara = 0;
    private Image foto = new Image("/sicem/images/avatar.png");
    private String fechamodificacion = "N/A";
    conexion c = new conexion();


    public empleado(){}
    
    
    public int getId(){ return this.id.get(); }
    public void setId(int v){ this.id.set(v); }
    public IntegerProperty getIdProperty(){ return this.id; }
    
    public String getNombres(){ return this.nombres.get(); }
    public void setNombres(String v){ this.nombres.set(v); }
    public StringProperty getNombresProperty(){ return this.nombres; }
    
    public String getApellidos(){ return this.apellidos.get(); }
    public void setApellidos(String v){ this.apellidos.set(v); }
    public StringProperty getApellidosProperty(){ return this.apellidos; }
    
    public String getTituloLaboral(){ return this.titulolaboral.get(); }
    public void setTituloLaboral(String v){ this.titulolaboral.set(v); }
    public StringProperty getTituloLaboralProperty(){ return this.titulolaboral; }
    
    public String getDomicilio(){ return this.domicilio.get(); }
    public void setDomicilio(String v){ this.domicilio.set(v); }
    public StringProperty getDomicilioProperty(){ return this.domicilio; }
    
    public String getCiudad(){ return this.ciudad.get(); }
    public void setCiudad(String v){ this.ciudad.set(v); }
    public StringProperty getCiudadProperty(){ return this.ciudad; }
            
    public String getTelefono(){ return this.telefono.get(); }
    public void setTelefono(String v){ this.telefono.set(v); }
    public StringProperty getTelefonoProperty(){ return this.telefono; }
            
    public String getCedula(){ return this.cedula.get(); }
    public void setCedula(String v){ this.cedula.set(v); }
    public StringProperty getCedulaProperty(){ return this.cedula; }
    
    public String getEmail(){ return this.email.get(); }
    public void setEmail(String v){ this.email.set(v); }
    public StringProperty getEmailProperty(){ return this.email; }
    
    public String getObservaciones(){ return this.observaciones.get(); }
    public void setObservaciones(String v){ this.observaciones.set(v); }
    public StringProperty getObservacionesProperty(){ return this.observaciones; }
    
    public int getDepartamentoId(){ return this.departamentoId.get(); }
    public void setDepartamentoId(int v){ this.departamentoId.set(v); }
    public IntegerProperty getDepartamentoIdProperty(){ return this.departamentoId; }
    
    public int getEstadoCivil(){ return this.estadocivil.get(); }
    public void setEstadoCivil(int v){ this.estadocivil.set(v); }
    public IntegerProperty getEstadoCivilProperty(){ return this.estadocivil; }
    
    public int getGenero(){ return this.genero.get(); }
    public void setGenero(int v){ this.genero.set(v); }
    public IntegerProperty getGeneroProperty(){ return this.genero; }
    
    public Date getFechaNacimiento(){ return this.fechanacimiento; }
    public void setFechaNacimiento(Date v){ this.fechanacimiento = v; }
    
    public Date getFechaContratacion(){ return this.fechacontratacion; }
    public void setFechaContratacion(Date v){ this.fechacontratacion = v; }
    
    public int getEstado(){ return this.estado; }
    public void setEstado(int v){ this.estado = v; }
    
    public int getReportarA(){ return this.reportara; }
    public void setReportarA(int v){ this.reportara = v; }
    
    public Image getFoto(){ return this.foto; }
    public void setFoto(Image v){ this.foto = v; }
    
    public String getFechaModificacion(){ return this.fechamodificacion; }
    public void setFechaModificacion(String v){ this.fechamodificacion = v; }


    public boolean Insertar(){
        param[] Parametros = new param[]{
            new param("int", "_ID", getId()),
            new param("str", "_Nombres", getNombres()),
            new param("str", "_Apellidos", getApellidos()),
            new param("int", "_DepartamentoID", getDepartamentoId()),
            new param("str", "_TituloLaboral", getTituloLaboral()),
            new param("date", "_FechaDeNacimiento", getFechaNacimiento()),
            new param("date", "_FechaDeContratacion", getFechaContratacion()),
            new param("int", "_EstadoCivil", getEstadoCivil()),
            new param("int", "_Genero", getGenero()),
            new param("str", "_Domicilio", getDomicilio()),
            new param("str", "_Ciudad", getCiudad()),
            new param("str", "_Telefono", getTelefono()),
            new param("str", "_Cedula", getCedula()),
            new param("str", "_Email", getEmail()),
            new param("str", "_Observaciones", getObservaciones()),
            new param("int", "_ReportarA", getReportarA()),
            new param("img", "_Foto", getFoto()),
            new param("int", "_Estado", getEstado())
        };

        return c.execute("Insertar_Empleado", Parametros);
    }


    public boolean Editar(){
        param[] Parametros = new param[]{
            new param("int", "_ID", getId()),
            new param("str", "_Nombres", getNombres()),
            new param("str", "_Apellidos", getApellidos()),
            new param("int", "_DepartamentoID", getDepartamentoId()),
            new param("str", "_TituloLaboral", getTituloLaboral()),
            new param("date", "_FechaDeNacimiento", getFechaNacimiento()),
            new param("date", "_FechaDeContratacion", getFechaContratacion()),
            new param("int", "_EstadoCivil", getEstadoCivil()),
            new param("int", "_Genero", getGenero()),
            new param("str", "_Domicilio", getDomicilio()),
            new param("str", "_Ciudad", getCiudad()),
            new param("str", "_Telefono", getTelefono()),
            new param("str", "_Cedula", getCedula()),
            new param("str", "_Email", getEmail()),
            new param("str", "_Observaciones", getObservaciones()),
            new param("int", "_ReportarA", getReportarA()),
            new param("img", "_Foto", getFoto()),
            new param("int", "_Estado", getEstado())
        };
        
        return c.execute("Actualizar_Empleado", Parametros);
    }


    public ObservableList<SearchResult> Buscar(String valor, int clave){
        param[] Parametros = new param[]{
            new param("str", "_valor", valor),
            new param("int", "_clave", clave)
        };
        
        ObservableList<SearchResult> resultado = FXCollections.observableArrayList();

        try{
            ResultSet rs = c.reader("Busqueda_Empleado", Parametros);
            while(rs.next()){
                SearchResult t = new SearchResult();
                t.setId(Integer.toString(rs.getInt("ID")));
                t.setName(rs.getString("Nombres")+" "+rs.getString("Apellidos"));
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
            ResultSet rs = c.reader("Mostrar_Empleados");
            while(rs.next()){
                SearchResult d = new SearchResult();
                d.setId(Integer.toString(rs.getInt("ID")));
                d.setName(rs.getString("Nombres")+" "+rs.getString("Apellidos"));
                d.setModificationDate(rs.getString("FechaModificacion"));

                resultado.add(d);
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return resultado;
    }


    public empleado Detalle(int idvalue){
        param[] Parametros = new param[]{ new param("int", "_ID", idvalue) };
        empleado t = new empleado();
        
        try{
            ResultSet rs = c.reader("Detalle_Empleado", Parametros);
            while(rs.next()){
                t.setId(rs.getInt("ID"));
                t.setNombres(rs.getString("Nombres"));
                t.setApellidos(rs.getString("Apellidos"));
                t.setDepartamentoId(rs.getInt("DepartamentoID"));
                t.setTituloLaboral(rs.getString("TituloLaboral"));
                t.setFechaNacimiento(rs.getDate("FechaDeNacimiento"));
                t.setFechaContratacion(rs.getDate("FechaDeContratacion"));
                t.setEstadoCivil(rs.getInt("EstadoCivil"));
                t.setGenero(rs.getInt("Genero"));
                t.setDomicilio(rs.getString("Domicilio"));
                t.setCiudad(rs.getString("Ciudad"));
                t.setTelefono(rs.getString("Telefono"));
                t.setCedula(rs.getString("Cedula"));
                t.setEmail(rs.getString("Email"));
                t.setObservaciones(rs.getString("Observaciones"));
                t.setReportarA(rs.getInt("ReportarA"));
                t.setFoto(c.getImage(rs.getBlob("Foto")));
                t.setEstado(rs.getInt("Estado"));
                t.setFechaModificacion(rs.getString("FechaModificacion"));
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return t;
    }


}