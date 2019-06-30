/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;
import DB.param;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author espinoza
 */
public class cliente extends PersonEntity<String> {
    
    public cliente(){
        super("Cliente", "str", "Mostrar_Clientes");
    } 
    
    /*
    *       Método Detalle
    */
    public cliente Detalle(String idvalue){
        param[] Parametros = new param[]{ new param("str", "_ID", idvalue) };
        cliente t = new cliente();
        try{
            ResultSet rs = c.reader("Detalle_Cliente", Parametros);
            while(rs.next()){
                t.setId(rs.getObject("ID", String.class));
                t.setNombre(rs.getString("Nombre"));
                t.setNombreContacto(rs.getString("NombreContacto"));
                t.setTituloContacto(rs.getString("TituloContacto"));
                t.setDomicilio(rs.getString("Domicilio"));
                t.setCiudad(rs.getString("Ciudad"));
                t.setTelefono(rs.getString("Telefono"));
                t.setEmail(rs.getString("Email"));
                t.setEstado(rs.getInt("Estado"));
                t.setFechaModificacion(rs.getString("FechaModificacion"));
            }
        }catch(SQLException ex){
        }finally{ try{ c.close(); }catch(Exception ex){} }
        
        return t;
    }
}
