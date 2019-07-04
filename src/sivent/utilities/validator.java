/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author hespinoza
 */
public final class Validator {
    
    public static void Decimal(KeyEvent e){
        boolean permiso=false;

        if (e.getCharacter().equals(".")){ permiso=true; }

        if (permiso==false) {
            try{
              int i=Integer.parseInt(e.getCharacter());
              permiso =true;
            }
            catch (Exception ee) {}  
        }

        if (!permiso) { e.consume(); }
    }
    
    public static void Number(KeyEvent e){
            boolean permiso=false; 
        if (permiso==false) 
        {
            try 
            {
              int i=Integer.parseInt(e.getCharacter());
              permiso =true;
            }
            catch (Exception ee) 
            {
              
            }  
        }
        if (!permiso) 
        {
            e.consume();
        }
    }
}
