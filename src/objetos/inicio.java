/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import DB.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author hespinoza
 */
public final class inicio {
    private inicio(){}
    
    static conexion c = new conexion();
    
    
    public static ObservableList<String> contadores(){
        ObservableList<String> values = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("contadores_tab_inicio");
            while(rs.next()){
                values.add(Integer.toString(rs.getInt(1)));
                values.add(Integer.toString(rs.getInt(2)));
                values.add(Integer.toString(rs.getInt(3)));
                values.add(Integer.toString(rs.getInt(4)));
            }
        }catch(SQLException ex){}
        
        return (values.size()>0) ? values : FXCollections.observableArrayList("0", "0", "0", "0");
    }
    
    
    public static ObservableList<SearchResult> ultimasVentas(){
        ObservableList<SearchResult> values = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("ultimas_ventas");
            while(rs.next()){
                SearchResult item = new SearchResult();
                item.setId(rs.getString(1));
                item.setName(rs.getString(2));
                item.setModificationDate(rs.getString(3));
                
                values.add(item);
            }
        }catch(SQLException ex){}
        
        return values;
    }
    
    
    public static ObservableList<SearchResult> productosMasVendidos(){
        ObservableList<SearchResult> values = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("productos_mas_vendidos");
            while(rs.next()){
                SearchResult item = new SearchResult();
                item.setId(rs.getString(1));
                item.setName(rs.getString(2));
                item.setModificationDate(rs.getString(3));
                
                values.add(item);
            }
        }catch(SQLException ex){}
        
        return values;
    }
    
    
    public static ObservableList<SearchResult> topClientes(){
        ObservableList<SearchResult> values = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.reader("top_clientes");
            while(rs.next()){
                SearchResult item = new SearchResult();
                item.setId(rs.getString(1));
                item.setName(rs.getString(2));
                item.setModificationDate(rs.getString(3));
                
                values.add(item);
            }
        }catch(SQLException ex){}
        
        return values;
    }
}
