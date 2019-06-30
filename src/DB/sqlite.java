/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author hespinoza
 */
public final class sqlite {
    private static final String url = "jdbc:sqlite:src/sivent/dbproperties.db";
    private static final String dbClassName = "org.sqlite.JDBC";
    //private static Connection c;
    
    private sqlite(){}
    
    public static Properties get(){
        Properties p = new Properties();
        try{
            Class.forName(dbClassName);
            Connection c = DriverManager.getConnection(url);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from conexion;");
            while(rs.next()){
                p.put(rs.getString(2),rs.getString(3));
            }
            rs.close();
            stmt.close();
            c.close();
        }catch(Exception ex){ System.out.println("GET -> "+ex.getClass()+" : "+ex.getMessage()); }
        
        return p;
    }
    
    
    public static void updateCredentials(String user, String password){
        try{
            Class.forName(dbClassName);
            Connection c = DriverManager.getConnection(url);
            Statement stmt = c.createStatement();
            stmt.executeUpdate("update conexion set value=\""+user+"\" where id=1;");
            stmt.executeUpdate("update conexion set value=\""+password+"\" where id=2;");
            
            stmt.close();
            c.close();
        }catch(Exception ex){ System.out.println(ex.getClass()+" : "+ex.getMessage()); }
    }
    
    
    public static String getUser(){
        String value = "";
        try{
            Class.forName(dbClassName);
            Connection c = DriverManager.getConnection(url);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select value from active where id=1;");
            while(rs.next())
                value = rs.getString(1);
            
            rs.close();
            stmt.close();
            c.close();
        }catch(Exception ex){ System.out.println(ex.getClass()+" : "+ex.getMessage()); }
        
        return value;
    }
    
    
    public static void setUser(String id){
        try{
            Class.forName(dbClassName);
            Connection c = DriverManager.getConnection(url);
            Statement stmt = c.createStatement();
            stmt.executeUpdate("update active set value=\""+id+"\" where id=1;");
            
            stmt.close();
            c.close();
        }catch(Exception ex){ System.out.println(ex.getClass()+" : "+ex.getMessage()); }
    }
    
}