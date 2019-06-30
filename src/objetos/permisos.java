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

/**
 *
 * @author hespinoza
 */
public class permisos {
    private final StringProperty id = new SimpleStringProperty("N/A");
    private final IntegerProperty Dconsultar = new SimpleIntegerProperty(0);
    private final IntegerProperty Deditar = new SimpleIntegerProperty(0);
    private final IntegerProperty Dcrear = new SimpleIntegerProperty(0);
    private final IntegerProperty Oconsultar = new SimpleIntegerProperty(0);
    private final IntegerProperty Oeditar = new SimpleIntegerProperty(0);
    private final IntegerProperty Ocrear = new SimpleIntegerProperty(0);
    private final IntegerProperty Pconsultar = new SimpleIntegerProperty(0);
    private final IntegerProperty Peditar = new SimpleIntegerProperty(0);
    private final IntegerProperty Pcrear = new SimpleIntegerProperty(0);
    private final IntegerProperty reportes = new SimpleIntegerProperty(0);
    private final IntegerProperty accesototal = new SimpleIntegerProperty(0);
    conexion c = new conexion();
    
    
    public permisos(){}
    

    public String getId() { return id.get(); }
    public void setId(String value) { id.set(value); }
    public StringProperty getIdProperty() { return id; }
    
    
    public int getDconsultar() { return Dconsultar.get(); }
    public void setDconsultar(int value) { Dconsultar.set(value); }
    public IntegerProperty getDconsultarProperty() { return Dconsultar; }
    
    
    public int getDeditar() { return Deditar.get(); }
    public void setDeditar(int value) { Deditar.set(value); }
    public IntegerProperty getDeditarProperty() { return Deditar; }
    
    
    public int getDcrear() { return Dcrear.get(); }
    public void setDcrear(int value) { Dcrear.set(value); }
    public IntegerProperty getDcrearProperty() { return Dcrear; }
    
    
    public int getOconsultar() { return Oconsultar.get(); }
    public void setOconsultar(int value) { Oconsultar.set(value); }
    public IntegerProperty getOconsultarProperty() { return Oconsultar; }
    
    
    public int getOeditar() { return Oeditar.get(); }
    public void setOeditar(int value) { Oeditar.set(value); }
    public IntegerProperty getOeditarProperty() { return Oeditar; }
    
    
    public int getOcrear() { return Ocrear.get(); }
    public void setOcrear(int value) { Ocrear.set(value); }
    public IntegerProperty getOcrearProperty() { return Ocrear; }
    
    
    public int getPconsultar() { return Pconsultar.get(); }
    public void setPconsultar(int value) { Pconsultar.set(value); }
    public IntegerProperty getPconsultarProperty() { return Pconsultar; }
    
    
    public int getPeditar() { return Peditar.get(); }
    public void setPeditar(int value) { Peditar.set(value); }
    public IntegerProperty getPeditarProperty() { return Peditar; }
    

    public int getPcrear() { return Pcrear.get(); }
    public void setPcrear(int value) { Pcrear.set(value); }
    public IntegerProperty getPcrearProperty() { return Pcrear; }
    
    
    public int getReportes() { return reportes.get(); }
    public void setReportes(int value) { reportes.set(value); }
    public IntegerProperty getReportesProperty() { return reportes; }
    
    
    public int getAccesototal() { return accesototal.get(); }
    public void setAccesototal(int value) { accesototal.set(value); }
    public IntegerProperty getAccesototalProperty() { return accesototal; }
   
    
    public void Insertar(){
        param[] parametros = new param[]{
            new param("str", "_IDusuario", getId()),
            new param("int", "_directorioConsultar", getDconsultar()),
            new param("int", "_directorioEditar", getDeditar()),
            new param("int", "_directorioCrear", getDcrear()),
            new param("int", "_operacionesConsultar", getOconsultar()),
            new param("int", "_operacionesEditar", getOeditar()),
            new param("int", "_operacionesCrear", getOcrear()),
            new param("int", "_productosConsultar", getPconsultar()),
            new param("int", "_productosEditar", getPeditar()),
            new param("int", "_productosCrear", getPcrear()),
            new param("int", "_reportes", getReportes()),
            new param("int", "_accesoTotal", getAccesototal())
        };
        
        c.execute("Insertar_Permisos", parametros);
    }
    
    
    public void Editar(){
        param[] parametros = new param[]{
            new param("str", "_IDusuario", getId()),
            new param("int", "_directorioConsultar", getDconsultar()),
            new param("int", "_directorioEditar", getDeditar()),
            new param("int", "_directorioCrear", getDcrear()),
            new param("int", "_operacionesConsultar", getOconsultar()),
            new param("int", "_operacionesEditar", getOeditar()),
            new param("int", "_operacionesCrear", getOcrear()),
            new param("int", "_productosConsultar", getPconsultar()),
            new param("int", "_productosEditar", getPeditar()),
            new param("int", "_productosCrear", getPcrear()),
            new param("int", "_reportes", getReportes()),
            new param("int", "_accesoTotal", getAccesototal())
        };
        
        c.execute("Actualizar_Permisos", parametros);
    }
    
    
    public permisos get(String id){
        param[] Param = new param[]{ new param("str", "_IDusuario", id) };
        permisos p = new permisos();
        try{
            ResultSet rs = c.reader("Mostrar_Permisos", Param);
            while(rs.next()){
                p.setId(rs.getString("IDusuario"));
                p.setDconsultar(rs.getInt("directorioConsultar"));
                p.setDeditar(rs.getInt("directorioEditar"));
                p.setDcrear(rs.getInt("directorioCrear"));
                p.setOconsultar(rs.getInt("operacionesConsultar"));
                p.setOeditar(rs.getInt("operacionesEditar"));
                p.setOcrear(rs.getInt("operacionesCrear"));
                p.setPconsultar(rs.getInt("productosConsultar"));
                p.setPeditar(rs.getInt("productosEditar"));
                p.setPcrear(rs.getInt("productosCrear"));
                p.setReportes(rs.getInt("reportes"));
                p.setAccesototal(rs.getInt("accesoTotal"));
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return p;
    }
    
    
    public permisos getDirectorio(String id){
        param[] Param = new param[]{ new param("str", "_IDusuario", id) };
        permisos p = new permisos();
        try{
            ResultSet rs = c.reader("Mostrar_Permisos_Directorio", Param);
            while(rs.next()){
                p.setId(rs.getString("IDusuario"));
                p.setDconsultar(rs.getInt("directorioConsultar"));
                p.setDeditar(rs.getInt("directorioEditar"));
                p.setDcrear(rs.getInt("directorioCrear"));
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return p;
    }
    
    
    public permisos getOperaciones(String id){
        param[] Param = new param[]{ new param("str", "_IDusuario", id) };
        permisos p = new permisos();
        try{
            ResultSet rs = c.reader("Mostrar_Permisos_Operaciones", Param);
            while(rs.next()){
                p.setId(rs.getString("IDusuario"));
                p.setOconsultar(rs.getInt("operacionesConsultar"));
                p.setOeditar(rs.getInt("operacionesEditar"));
                p.setOcrear(rs.getInt("operacionesCrear"));
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return p;
    }
    
    
    public permisos getProductos(String id){
        param[] Param = new param[]{ new param("str", "_IDusuario", id) };
        permisos p = new permisos();
        try{
            ResultSet rs = c.reader("Mostrar_Permisos_Productos", Param);
            while(rs.next()){
                p.setId(rs.getString("IDusuario"));
                p.setPconsultar(rs.getInt("productosConsultar"));
                p.setPeditar(rs.getInt("productosEditar"));
                p.setPcrear(rs.getInt("productosCrear"));
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return p;
    }
}
