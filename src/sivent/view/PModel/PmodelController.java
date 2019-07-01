/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.PModel;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author hespinoza
 */
public class PmodelController implements Initializable {
    double d, desv, desd, z, qopt;
    int I, L, T;
    @FXML private Button btnCalcular;
    @FXML private Button btnLimpiar;
    @FXML private TextField txtDemandaPromedio;
    @FXML private TextField txtPlazo;
    @FXML private TextField txtPeriodoRevision;
    @FXML private TextField txtInventarioActual;
    @FXML private TextField txtDesviacionEstandar;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtZ;
    @FXML private TextField txtDesviacionEstandarDemanda;
    @FXML private Spinner Num;
    SpinnerValueFactory<Integer> Per = new SpinnerValueFactory.IntegerSpinnerValueFactory(95,98,95);
    void ValidarDec(KeyEvent e)
    {
        boolean permiso=false;
        if (e.getCharacter().equals(".")) 
        {
            permiso=true;
        }
            
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
    void Validar(KeyEvent e)
    {
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
    public void Calcular()
    {
        if (txtPlazo.getText().isEmpty()==true|| 
                txtDemandaPromedio.getText().isEmpty()==true|| 
                txtInventarioActual.getText().isEmpty()==true|| 
                txtPeriodoRevision.getText().isEmpty()==true|| 
                txtDemandaPromedio.getText().isEmpty()==true)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText("Algo anda mal");
            alert.setContentText("Parece que aun hay campos vacios,\n revísalos e intenta de nuevo.");
            alert.showAndWait();
        }
        else
        {
            if (txtPlazo.getText().equals("0")==true || 
                txtDemandaPromedio.getText().equals("0")==true || 
                txtInventarioActual.getText().equals("0")==true || 
                txtPeriodoRevision.getText().equals("0")==true || 
                txtDemandaPromedio.getText().equals("0")==true )
            {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.initStyle(StageStyle.UTILITY);
                 alert.setTitle("Error");
                 alert.setHeaderText("Algo anda mal");
                 alert.setContentText("Parece que aun hay nulos,\n revísalos e intenta de nuevo.");
                 alert.showAndWait();
            }
            else
            {
                    d = Double.parseDouble(txtDemandaPromedio.getText());
                    I = Integer.parseInt(txtInventarioActual.getText());
                    L = Integer.parseInt(txtPlazo.getText());
                    T = Integer.parseInt(txtPeriodoRevision.getText());
                    desv = Double.parseDouble(txtDesviacionEstandar.getText());

                    desd = Math.round(Math.sqrt((L + T) * Math.pow(desv, 2)));
                    txtDesviacionEstandarDemanda.setText(String.valueOf(desd));

                    if (Num.getValue() == "95")
                    {
                        txtZ.setText("1.64");
                    }
                    else if (Num.getValue() == "96")
                    {
                        txtZ.setText("1.75");
                    }
                    else if (Num.getValue() == "97")
                    {
                        txtZ.setText("1.88");
                    }
                    else if (Num.getValue() ==  "98")
                    {
                        txtZ.setText("2.05");
                    }
                    else
                    {
                        txtZ.setText("2.33");

                    }
                    z = Double.parseDouble(txtZ.getText());
                    qopt = Math.round(((d * (T + L)) + (z * desd) - I));
                    txtCantidad.setText(String.valueOf(qopt));
            }
        }
    }
    public void LimpiarCampos()
    {
        txtDemandaPromedio.clear();
        txtPlazo.clear();
        txtPeriodoRevision.clear();
        txtInventarioActual.clear();
        txtDesviacionEstandar.clear();
        txtCantidad.clear();
        txtZ.clear();
        txtDesviacionEstandarDemanda.clear();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.Num.setValueFactory(Per);
        txtInventarioActual.setOnKeyTyped(e->{ Validar(e);});
        txtDemandaPromedio.setOnKeyTyped(e->{ Validar(e);});
        txtPlazo.setOnKeyTyped(e->{ Validar(e);});
        txtPeriodoRevision.setOnKeyTyped(e->{ Validar(e);});
        txtDesviacionEstandar.setOnKeyTyped(e->{ ValidarDec(e);});
    }    
    
}
