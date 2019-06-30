/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;

import java.util.function.UnaryOperator;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author hespinoza
 */
public final class validator {
    static final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    static final PseudoClass activeClass = PseudoClass.getPseudoClass("active");
    static final PseudoClass desactiveClass = PseudoClass.getPseudoClass("desactive");
    private validator(){}
    
    public static void validate(TextField node, boolean bool){
        node.pseudoClassStateChanged(errorClass, bool);
    }
    
    public static void validate(DatePicker node, boolean bool){
        node.pseudoClassStateChanged(errorClass, bool);
    }
    
    public static void validate(ComboBox node, boolean bool){
        node.pseudoClassStateChanged(errorClass, bool);
    }
    
    public static void validate(TextArea node, boolean bool){
        node.pseudoClassStateChanged(errorClass, bool);
    }
    
    public static void validate(Label node, boolean bool){
        node.pseudoClassStateChanged(errorClass, bool);
    }
    
    public static void Numeros(TextField node){
        node.focusedProperty().addListener((evt, Old, New) -> {
            if(New && node.getText().equals("0"))
                node.clear();
            
            if(!New){
                if(node.getText().isEmpty()) node.setText("0");
                if(node.getText().matches("[0-9]*\\.")) node.setText(node.getText().replace(".", ""));
            }
        });
        

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getText(); // Se obtiene el valor ingresado
            int textControlLength = ((TextField)change.getControl()).getText().length();
            
            if(newText.equals("0.")){
                return change;
                
            }else if(newText.equals(".") && !change.getControlText().contains(".")){
                if(textControlLength==0){
                    ((TextField)change.getControl()).setText("0.");
                    ((TextField)change.getControl()).end();
                    return null;
                }else
                    return change;
                
            }else if(newText.matches("[0-9]*") || newText.matches("[0-9]{1,}\\.[0-9]*")){
                return change;
            }
            
            // Si el valor ingresado no cumple con niguna de las reglas de validacion 
            // se retorna null ignorando el valor ingresado
            return null;
        };
        node.setTextFormatter(new TextFormatter<String>(integerFilter));
    }
    
    
    public static void setActive(Button node){ node.pseudoClassStateChanged(activeClass, true); }
    public static void removeActive(Button node){ node.pseudoClassStateChanged(activeClass, false); }
    
    public static void setHide(Button node){ node.pseudoClassStateChanged(desactiveClass, true); }
    public static void removeHide(Button node){ node.pseudoClassStateChanged(desactiveClass, false); }

}
