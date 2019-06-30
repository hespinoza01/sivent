/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.administrar;

import DB.conexion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import objetos.categoria;
import sivent.utilities.Popup;
/**
 * FXML Controller class
 *
 * @author espinoza
 */
public class DetalleCategoriaController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtId;
    @FXML private TextArea txtDescripcion;
    @FXML private Button editar;
    @FXML private Label EstadoValue;
    @FXML private Label labelFechaCreacion;
    @FXML private Label labelFechaModificacion;
    @FXML private TextField txtCantidadProductosAlvergados;
    
    conexion c = new conexion();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicia();
    }    
    
    
    private void inicia(){
        labelFechaModificacion.setText("");
        labelFechaCreacion.setText("");
        EstadoValue.setText("");
        editar.setVisible(false);
        
        
        //Evt editar button
        editar.setOnAction(e -> {
            new CategoriaController()
                    .load(new CategoriaController())
                    .setDataView(Integer.parseInt(txtId.getText()), this)
                    .showAfterLoad();
        });
    }
    
    
    public void setInfo(int id){
        categoria data = new categoria().Detalle(id);

        if(data != null){
            editar.setVisible(true);

            txtId.setText(Integer.toString(data.getId()));
            txtNombre.setText(data.getNombre());
            txtDescripcion.setText(data.getDescripcion());
            EstadoValue.setText((data.getEstado() == 1) ? "Habilitado" : "Deshabilitado");
            labelFechaCreacion.setText(data.getFechaCreacion());
            labelFechaModificacion.setText(data.getFechaModificacion());
                int cantidad = (int)c.readerScalar("select count(*) from Producto where CategoriaID ="+data.getId(), Integer.class);
            txtCantidadProductosAlvergados.setText(Integer.toString(cantidad));
        }else
            Popup.error(
                "Error con la solicitud", 
                "Ocurrió un error inesperado al tratar de obtener la información solicitada. Por favor, intente nuevamente.");
    }
    
}
