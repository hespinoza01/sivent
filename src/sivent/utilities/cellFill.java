/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import objetos.SearchResult;

/**
 *
 * @author hespinoza
 */
public final class cellFill {
    private  cellFill(){}
    
    public static void setBlue(TableColumn<SearchResult,String> column){
        column.setCellFactory(tc -> {
            TableCell<SearchResult, String> cell = new TableCell<SearchResult, String>(){
                @Override
                protected void updateItem(String item, boolean empty){
                    super.updateItem(item, empty);
                    setText(empty ? "" : item);
                    setTextFill(Color.ROYALBLUE);
                }
            };
                    
            return cell;
        });
    }
    
    public static void setWhite(TableColumn<SearchResult,String> column){
        column.setCellFactory(tc -> {
            TableCell<SearchResult, String> cell = new TableCell<SearchResult, String>(){
                @Override
                protected void updateItem(String item, boolean empty){
                    super.updateItem(item, empty);
                    setText(empty ? "" : item);
                    setTextFill(Color.WHITE);
                }
            };
                    
            return cell;
        });
    }
}
