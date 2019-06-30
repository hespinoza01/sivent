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
public class proveedor extends PersonEntity<Integer> {
    public proveedor(){
        super("Proveedor", "int", "Mostrar_Proveedores");
    }
    
    /*
    *       Método Detalle
    */
    public proveedor Detalle(int idvalue){
        param[] Parametros = new param[]{ new param("int", "_ID", idvalue) };
        proveedor t = new proveedor();
        try{
            ResultSet rs = c.reader("Detalle_Proveedor", Parametros);
            while(rs.next()){
                t.setId(rs.getObject("ID", Integer.class));
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
