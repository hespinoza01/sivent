/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sivent.utilities.Dragged;
import sivent.utilities.Load;
import sivent.utilities.SicemWindow;
import sivent.utilities.dialogos;
import static sivent.utilities.dialogos.tipo.confir;
import sivent.view.AggregatePlanning.AggregatePlanningController;
import sivent.view.PModel.PmodelController;
import sivent.view.QModel.QmodelController;
import sivent.view.QModel.QmodelInvController;

/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class HomeController extends SicemWindow implements Initializable {

    @FXML private BorderPane content;
    @FXML private Pane header;
    @FXML private Button menu;
    @FXML private TabPane navegacion;
    @FXML private Tab qmodelTab;
    @FXML private Tab qmodelinvTab;
    @FXML private Tab pmodelTab;
    @FXML private Tab aggregationplanTab;

    
    Pane Qmodel;
    Pane QmodelInv;
    Pane Pmodel;
    Pane AggregatePlanning;
    
    QmodelController qmodelController;
    QmodelInvController qmodelinvController;
    PmodelController pmodelController;
    AggregatePlanningController aggregateplanningController;

    
    private static Stage window;
    
    public HomeController(){ super("/sivent/view/home.fxml"); }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }
    
    
    public void inicia(){
        Dragged.set(header);
        contextmenu();
        components();
        
        navegacion.getSelectionModel().selectedIndexProperty().addListener((e, Old, New) -> {
            switch(New.intValue()){
                case 0:
                    content.setCenter(Qmodel);
                    break;
                    
                case 1:
                    content.setCenter(QmodelInv);
                    break;
                    
                case 2:
                    content.setCenter(Pmodel);
                    break;
                    
                case 3:
                    content.setCenter(AggregatePlanning);
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
            
            loader = Load.Loader("/sivent/view/PModel/pmodel.fxml");
            Pmodel = loader.load();
            pmodelController = loader.getController();
            
            loader = Load.Loader("/sivent/view/AggregatePlanning/aggregatePlanning.fxml");
            AggregatePlanning = loader.load();
            aggregateplanningController = loader.getController();
            
            
            content.setCenter(Qmodel);
        }catch(IOException ex){ System.out.println("Error carga components\n"+ex.toString()); }
    }
    
    
    private void contextmenu(){
        ContextMenu contextmenu = new ContextMenu();
        MenuItem salir = new MenuItem("Salir");
        MenuItem configurarperfil = new MenuItem("Configurar perfil");
        contextmenu.getItems().addAll(configurarperfil/*, cerrarsesion*/, salir);
        
        menu.setContextMenu(contextmenu);
        menu.setOnAction(e -> { 
            double x = menu.getScene().getWindow().getX() + 10;
            double y = menu.getScene().getWindow().getY() + 40;
            contextmenu.show(menu, x, y); 
        });
        
        
        salir.setOnAction(e -> { 
            if(new dialogos("Salir", "¿Salir del sistema?", confir).show())
                System.exit(0); 
        });
    }

    @Override
    public void show(){
        try {
            window = Load.Form(new Scene(Load.Loader(getFXMLLocation()).load()));
            window.show();
        } catch (IOException ex) { System.out.println("Error on showing Home: "+ex.toString()); }
    }
    
    @Override
    public void close(){
        window.close();
    }
}
