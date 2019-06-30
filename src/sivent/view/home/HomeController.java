/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.home;

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
import sivent.view.LoginController;
import sivent.view.administrar.AdministrarController;
import sivent.view.administrar.UsuarioFormController;
import sivent.view.directorio.DirectorioController;
import sivent.view.operaciones.OperacionesController;
import sivent.view.productos.ProductosController;
import sivent.view.reportes.ReportesController;

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
    @FXML private ImageView avatar;
    @FXML private Label lblUsername;
    @FXML private Label lblID;
    @FXML private Pane navbar;
    @FXML private Button inicioTab;
    @FXML private Button directorioTab;
    @FXML private Button operacionesTab;
    @FXML private Button productosTab;
    @FXML private Button reportesTab;
    @FXML private Button administrarTab;
    @FXML private VBox vbox;
    
    Pane inicio;
    Pane directorio;
    Pane operaciones;
    Pane productos;
    Pane reportes;
    Pane administrar;
    
    InicioController inicioController;
    DirectorioController directorioController;
    OperacionesController operacionesController;
    ProductosController productosController;
    ReportesController reportesController;
    AdministrarController administrarController;
    
    private static Stage window;
    
    public HomeController(){ super("/sivent/view/home/home.fxml"); }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }
    
    
    public void inicia(){
        Dragged.set(header);
        rounded.roundImageView(avatar, new Image("sivent/images/perfil.jpg"));
        menu.setTooltip(new Tooltip("Menú"));
        inicioTab.setTooltip(new Tooltip("Inicio"));
        directorioTab.setTooltip(new Tooltip("Directorio"));
        operacionesTab.setTooltip(new Tooltip("Operaciones"));
        productosTab.setTooltip(new Tooltip("Productos"));
        reportesTab.setTooltip(new Tooltip("Reportes"));
        administrarTab.setTooltip(new Tooltip("Administrar"));
        contextmenu();
        components();
        setInfo();
        
        
        //Evt inicioTab click
        inicioTab.setOnAction(e -> {
            clearIndicador();
            validator.setActive(inicioTab);
            content.setCenter(inicio);
            inicioController.cargar();
        });
        
        
        //Evt directorioTab click
        directorioTab.setOnAction(e -> {
            clearIndicador();
            validator.setActive(directorioTab);
            content.setCenter(directorio);
            directorioController.cargar(false);
        });
        
        
        //Evt operacionesTab click
        operacionesTab.setOnAction(e -> {
            clearIndicador();
            validator.setActive(operacionesTab);
            content.setCenter(operaciones);
            operacionesController.cargar(false);
        });
        
        
        //Evt productosTab click
        productosTab.setOnAction(e -> {
            clearIndicador();
            validator.setActive(productosTab);
            content.setCenter(productos);
            productosController.cargar(false);
        });
        
        
        //Evt reportesTab click
        reportesTab.setOnAction(e -> {
            clearIndicador();
            validator.setActive(reportesTab);
            content.setCenter(reportes);
            reportesController.cargar();
        });
        
        
        //Evt administrarTab click
        administrarTab.setOnAction(e -> {
            clearIndicador();
            validator.setActive(administrarTab);
            content.setCenter(administrar);
            administrarController.cargar(false);
        });
    }
    
    
    public void setInfo(){
        usuario data = new usuario().Detalle(sqlite.getUser());

        if(data != null){
            lblID.setText(data.getUsername());
            lblUsername.setText(data.getNombre()+" "+data.getApellido());
            rounded.roundImageView(avatar, data.getFoto());
            verificaPermisos(data.getUsername());
        }
        else
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error al tratar de obtener la información solicitada. \nPor favor, intente nuevamente.");
    }
    
    
    private void components(){
        try{
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
        }catch(IOException ex){ System.out.println("Error carga components"); }
    }
    
    
    private void contextmenu(){
        ContextMenu contextmenu = new ContextMenu();
        MenuItem salir = new MenuItem("Salir");
        MenuItem cerrarsesion = new MenuItem("Cerrar Sesión");
        MenuItem configurarperfil = new MenuItem("Configurar perfil");
        contextmenu.getItems().addAll(configurarperfil, cerrarsesion, salir);
        
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
        
        cerrarsesion.setOnAction(e -> { 
            if(new dialogos("Salir", "¿Cerrar sesión?", confir).show()){
                new LoginController().show();
                close();
            }
        });
        
        configurarperfil.setOnAction(e -> {
            new UsuarioFormController()
                    .load(new UsuarioFormController())
                    .setDataView(lblID.getText(), this)
                    .showAfterLoad();
        });
    }
    
    
    private void clearIndicador(){
        validator.removeActive(inicioTab);
        validator.removeActive(directorioTab);
        validator.removeActive(operacionesTab);
        validator.removeActive(productosTab);
        validator.removeActive(reportesTab);
        validator.removeActive(administrarTab);
    }
    
    
    private void verificaPermisos(String id){
        vbox.getChildren().clear();
        vbox.getChildren().addAll(inicioTab, directorioTab, operacionesTab, productosTab, reportesTab, administrarTab);
        permisos p = new permisos().get(id);

        if((p.getDconsultar()==0 && p.getDeditar()==0) && p.getDcrear()==0)
            vbox.getChildren().remove(directorioTab);

        if((p.getOconsultar()==0 && p.getOeditar()==0) && p.getOcrear()==0)
            vbox.getChildren().remove(operacionesTab);

        if((p.getPconsultar()==0 && p.getPeditar()==0) && p.getPcrear()==0)
            vbox.getChildren().remove(productosTab);

        if(p.getReportes()==0)
            vbox.getChildren().remove(reportesTab);

        if(p.getAccesototal()==0)
            vbox.getChildren().remove(administrarTab);
        
        directorioController.verificaPermisos();
        productosController.verificaPermisos();
        operacionesController.verificaPermisos();
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
