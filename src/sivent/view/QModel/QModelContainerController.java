/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.QModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sivent.utilities.Load;

/**
 * FXML Controller class
 *
 * @author hespinoza
 */
public class QModelContainerController implements Initializable {

    @FXML private TabPane navegacion;
    @FXML private Tab modelEOQTab;
    @FXML private Tab modelEOQVTab;
    @FXML private BorderPane container;
    
    Pane Qmodel;
    Pane QmodelInv;
    
    QmodelController qmodelController;
    QmodelInvController qmodelinvController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        components();
        
        navegacion.getSelectionModel().selectedIndexProperty().addListener((Old, Current, New) -> {
            switch(New.intValue()){
                case 0:
                    container.setCenter(Qmodel);
                    break;
                    
                case 1:
                    container.setCenter(QmodelInv);
                    break;
            }
        });
    }    
    
    private void components(){
        try{
            FXMLLoader loader = Load.Loader("/sivent/view/QModel/qmodel.fxml");
            Qmodel = loader.load();
            qmodelController = loader.getController();
            
            loader = Load.Loader("/sivent/view/QModel/qmodelInv.fxml");
            QmodelInv = loader.load();
            qmodelinvController = loader.getController();
            
            container.setCenter(Qmodel);
        }catch(IOException ex){ System.out.println("Error carga components\n"+ex.toString()); }
    }
    
}
