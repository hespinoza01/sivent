/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view;
import DB.sqlite;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.io.IOException;
import sivent.utilities.dialogos;
import sivent.view.home.HomeController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objetos.SearchResult;
import objetos.usuario;
import sivent.utilities.Load;
import sivent.utilities.SicemWindow;
import sivent.utilities.dialogos.tipo;
import static sivent.utilities.dialogos.tipo.confir;
import sivent.utilities.rounded;

/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class LoginController extends SicemWindow implements Initializable {
    
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button ingresar;
    @FXML private Button btnClose;
    @FXML private ImageView avatar;
    @FXML private Button iconfielduser;
    @FXML private Button iconfieldpass;
    
    private static Stage window;
    
    
    public LoginController(){ 
        super("/sivent/view/login.fxml");
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    } 
    
    
    private void inicia(){
        rounded.roundImageView(avatar, new Image("/sivent/images/avatar.png"));
        username.requestFocus();
        
        ingresar.disableProperty().bind(username.textProperty().isEmpty().or(password.textProperty().isEmpty()));
        btnClose.setTooltip(new Tooltip("Salir"));
        username.setTooltip(new Tooltip("Nombre de usuario"));
        password.setTooltip(new Tooltip("Contraseña"));
        
        
        // evento de escucha para las sugerencias
        username.textProperty().addListener((arg, oldValue, newValue) -> {
            if(username.getText().length() > 0)
                new AutoCompletionTextFieldBinding<>(username, SuggestionProvider.create(sugerencia(newValue)));

        });
        username.setOnKeyPressed(e -> {
            if(e.getCode() == ENTER)
                password.requestFocus();
        });
        iconfielduser.setOnAction(e -> { username.requestFocus(); });
        
        
        // evento propiedad onFocus de password
        password.focusedProperty().addListener((arg, oldValue, newValue) -> {
            if(newValue && username.getText().length() > 0)
                rounded.roundImageView(avatar, new usuario().obtenerFoto(username.getText().trim()));
        });
        password.setOnKeyPressed(e -> {
            if(e.getCode() == ENTER)
                    IngresarVoid();
        });
        iconfieldpass.setOnAction(e -> { password.requestFocus(); });
        
        
        //Evtv close
        btnClose.setOnAction(e -> {
            if(new dialogos("Cerrar sesión", "¿Salir del sistema?", confir).show())
                System.exit(0);
        });
        
        
        //Evt ingresar
        ingresar.setOnAction(e -> { try{ IngresarVoid(); }catch(Exception ex){ System.out.println("IngVoid: "+ex.getMessage()); } });
    }
    
    
    private ObservableList<String> sugerencia(String value){
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<SearchResult> sr = new usuario().Buscar(value, 0);
        for(SearchResult tem : sr){ items.add(tem.getId()); }
        
        return items;
    }
    
    
    private void IngresarVoid(){
        if(new usuario().Verifica(username.getText().trim(), password.getText().trim())){
            ingresar.setVisible(false);
            sqlite.setUser(username.getText());
            new HomeController().show();

            close();
        }else
            new dialogos(
                    "Usuario o contraseña incorrectos",
                    "Error al iniciar sesión. El usuario o contraseña son incorrectos.",
                    tipo.error).show();
    }
    
    @Override
    public void show(){
        try {
            window = Load.Form(new Scene(Load.Loader("/sivent/view/login.fxml").load()));
            window.show();
        } catch (IOException ex) { System.out.println("Error on showing Login: "+ex.toString()); }
    }
    
    @Override
    public void close(){
        System.out.println("Closing");
        window.close();
    }

}
