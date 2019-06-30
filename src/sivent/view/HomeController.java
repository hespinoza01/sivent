/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view;

import DB.sqlite;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objetos.permisos;
import objetos.usuario;
import sivent.utilities.Dragged;
import sivent.utilities.Load;
import sivent.utilities.Popup;
import sivent.utilities.SicemWindow;
import sivent.utilities.dialogos;
import static sivent.utilities.dialogos.tipo.confir;
import sivent.utilities.rounded;
import sivent.utilities.validator;

/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class HomeController extends SicemWindow implements Initializable {

    boolean activenav = false;
    @FXML private BorderPane content;
    @FXML private Pane header;
    @FXML private Button menu;
    @FXML private Pane navbar;
    @FXML private Button qmodelTab;
    @FXML private Button qmodelITab;
    @FXML private Button pmodelTab;
    @FXML private Button agregationplanTab;
    @FXML private VBox vbox;
    
    Pane inicio;
    Pane directorio;
    Pane operaciones;
    Pane productos;
    Pane reportes;
    Pane administrar;
    
    /*InicioController inicioController;
    DirectorioController directorioController;
    OperacionesController operacionesController;
    ProductosController productosController;
    ReportesController reportesController;
    AdministrarController administrarController;*/
    
    private static Stage window;
    
    public HomeController(){ super("/sivent/view/home.fxml"); }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }
    
    
    public void inicia(){
        Dragged.set(header);
        contextmenu();
        /*menu.setTooltip(new Tooltip("Menú"));
        inicioTab.setTooltip(new Tooltip("Inicio"));
        directorioTab.setTooltip(new Tooltip("Directorio"));
        operacionesTab.setTooltip(new Tooltip("Operaciones"));
        productosTab.setTooltip(new Tooltip("Productos"));
        reportesTab.setTooltip(new Tooltip("Reportes"));
        administrarTab.setTooltip(new Tooltip("Administrar"));
        components();*/
        
    }
    
    
    private void components(){
        /*try{
            FXMLLoader loader = Load.Loader("/sivent/view/home/inicio.fxml");
            inicio = loader.load();
            inicioController = loader.getController();
            
            loader = Load.Loader("/sivent/view/directorio/directorio.fxml");
            directorio = loader.load();
            directorioController = loader.getController();
            
            loader = Load.Loader("/sivent/view/operaciones/operaciones.fxml");
            operaciones = loader.load();
            operacionesController = loader.getController();
            
            loader = Load.Loader("/sivent/view/productos/productos.fxml");
            productos = loader.load();
            productosController = loader.getController();
            
            loader = Load.Loader("/sivent/view/reportes/reportes.fxml");
            reportes = loader.load();
            reportesController = loader.getController();
            
            loader = Load.Loader("/sivent/view/administrar/administrar.fxml");
            administrar = loader.load();
            administrarController = loader.getController();
            
            
            validator.setActive(inicioTab);
            content.setCenter(inicio);
        }catch(IOException ex){ System.out.println("Error carga components"); }*/
    }
    
    
    private void contextmenu(){
        ContextMenu contextmenu = new ContextMenu();
        MenuItem salir = new MenuItem("Salir");
//        MenuItem cerrarsesion = new MenuItem("Cerrar Sesión");
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
        
       /* cerrarsesion.setOnAction(e -> { 
            if(new dialogos("Salir", "¿Cerrar sesión?", confir).show()){
                //new LoginController().show();
                close();
            }
        });*/
        
    }
    
    
    private void clearIndicador(){
        validator.removeActive(qmodelTab);
        validator.removeActive(qmodelITab);
        validator.removeActive(pmodelTab);
        validator.removeActive(agregationplanTab);
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
