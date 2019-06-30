/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author espinoza
 */
public class conexion {
    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/sicem";
    private Connection c;
    
    public conexion(){
        try{
            Class.forName(dbClassName);
//            this.p.put("user","root");
//            this.p.put("password","harold97");
//            this.p.put("useSSL", "false");
//            this.p.put("autoReconnect", "true");
        }catch(ClassNotFoundException e){}
    }
    
    public void open() throws SQLException{ c = DriverManager.getConnection(CONNECTION,sqlite.get()); }
    public void close() throws SQLException{ try{ c.close(); }catch(SQLException e){} }
    
    /*
    *           Escritura de datos
    */
    
    public boolean execute(String cmd){
        boolean estado = false;
        try{
            open();
            CallableStatement cs = c.prepareCall("call "+cmd);
            estado = (cs.executeUpdate() != 0) ? true : false;
        }catch(SQLException e){ estado = false;
        }finally{ try{ close(); }catch(SQLException e){} }
        
        return estado;
    }
    
    public boolean execute(String cmd, param[] p){
        boolean estado = false;
        try{
            open();
            CallableStatement cs = c.prepareCall(procedure(cmd, p.length));
            setParams(cs, p);
            estado = (cs.executeUpdate() != 0) ? true : false;
        }catch(SQLException e){ estado = false; System.out.println("ERROR: "+e.getCause()+" --- "+e.getMessage());
        }catch(IOException e){
        }finally{ try{ close(); }catch(SQLException e){} }
        
        return estado;
    }
    
    
    /*
    *           Lectura de datos
    */
    
    public ResultSet reader(String cmd){ // procedimiento sin parámetros
        ResultSet r = null;
        try{
            open();
            CallableStatement cs = c.prepareCall("call "+cmd);
            r = cs.executeQuery();
        }catch(SQLException e){}
        
        return r;
    }
    
    public ResultSet reader(String cmd, param[] p){ // Procedimiento con parámetros
        ResultSet r = null;
        try{
            open();
            CallableStatement cs = c.prepareCall(procedure(cmd, p.length));
            setParams(cs, p);
            r = cs.executeQuery();
        }catch(SQLException e){
        }catch(IOException e){}
        
        return r;
    }
    
    public ResultSet readerSimple(String cmd) throws SQLException{
        ResultSet r = null;
        try{
            open();
            Statement s = c.createStatement();
            r = s.executeQuery(cmd);
        }catch(SQLException e){}
        
        return r;
    }
    
    public Object readerScalar(String cmd, Class<?> tipo){
        Object o = null;
        try{
            open();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(cmd);
            while(rs.next()){ o = rs.getObject(1, tipo); }
        }catch(SQLException e){
        }finally{ try{ close(); }catch(SQLException e){} }
        
        return o;
    }
    
    
    /*
    *       Métodos complementarios
    */
            public String procedure(String command, int cont){
                String cmd = "call "+command+"(";
                for(int i=0; i<cont-1; i++) cmd+="?,";

                return cmd+="?)";
            }

            public void setParams(CallableStatement cs, param[] param) throws SQLException, IOException{
                for(param p : param){
                    switch(p.type){
                        case "str":
                            p.value = (p.value.equals("")) ? "N/A" : p.value;
                            cs.setString(p.name, (String)p.value);
                            break;

                        case "int":
                            cs.setInt(p.name, (int)p.value);
                            break;
                            
                        case "img":
                            cs.setBlob(p.name, getBlob((Image)p.value));
                            break;
                            
                        case "date":
                            Date datevalue = (Date)p.value;
                            cs.setDate(p.name, new java.sql.Date(datevalue.getTime()));
                            break;
                            
                        case "float":
                            cs.setFloat(p.name, (float)p.value);
                            break;
                            
                        case "decimal":
                            cs.setBigDecimal(p.name, (BigDecimal)p.value);
                            break;
                    }
                }
            }
            
            public Blob getBlob(Image img) throws IOException, SQLException{
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", byteOutput);
                Blob blob =  new SerialBlob(byteOutput.toByteArray());
                
                return blob;
            }
            
            public Image getImage(Blob b) throws SQLException{
                byte byteImage[] = null;
                byteImage = b.getBytes(1, (int) b.length());

                Image img = new Image(new ByteArrayInputStream(byteImage));
                return img;
            }
}
