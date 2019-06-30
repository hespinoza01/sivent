/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;

import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.ENTER;

/**
 *
 * @author hespinoza
 */
public final class changeNode {
    private changeNode(){}
    
    public static void set(TextField from, TextField to){
        from.setOnKeyPressed(e -> {
            if(e.getCode() == ENTER)
                to.requestFocus();
        });
    }
}
