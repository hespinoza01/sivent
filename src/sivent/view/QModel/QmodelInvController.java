/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.QModel;

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
public class QmodelInvController implements Initializable 
{
    double D, S, Dest, Rop, qopt, H, des, z,ded,ins;
    int L, dh;
    @FXML private Button btnCalcular;
    @FXML private Button btnLimpiar;
    @FXML private TextField txtDemanda;
    @FXML private TextField txtPlazo;
    @FXML private TextField txtDiasHabiles;
    @FXML private TextField txtDesviacion;
    @FXML private TextField txtCostoPedir;
    @FXML private TextField txtCostoMantenimiento;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtROP;
    @FXML private TextField txtZ;
    @FXML private TextField txtDesviacionEstandarDemanda;
    @FXML private TextField txtSS;
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
        if (txtDiasHabiles.getText().isEmpty()==true || 
                 txtCostoMantenimiento.getText().isEmpty()==true|| 
                 txtCostoPedir.getText().isEmpty()==true|| 
                 txtPlazo.getText().isEmpty()==true|| 
                 txtDemanda.getText().isEmpty()==true || 
                 txtDesviacion.getText().isEmpty()==true)
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
             if (txtDiasHabiles.getText().equals("0")==true || 
                 txtCostoMantenimiento.getText().equals("0")==true|| 
                 txtCostoPedir.getText().equals("0")==true|| 
                 txtPlazo.getText().equals("0")==true|| 
                 txtDemanda.getText().equals("0")==true || 
                 txtDesviacion.getText().equals("0")==true)
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
                 D = Double.parseDouble(txtDemanda.getText());
                 S = Double.parseDouble(txtCostoPedir.getText());
                 L = Integer.parseInt(txtPlazo.getText());
                 H = Double.parseDouble(txtCostoMantenimiento.getText());
                 dh = Integer.parseInt(txtDiasHabiles.getText());
                 des = Double.parseDouble(txtDesviacion.getText());
                 qopt = Math.round(Math.sqrt((2 * D * S) / (H)));
                 ded = Math.round(des / Math.sqrt(L));

                 txtCantidad.setText(String.valueOf(qopt));
                 txtDesviacionEstandarDemanda.setText(String.valueOf(ded));

                if (Num.getValue()== "95")
                {
                    txtZ.setText("1,64");

                }
                else if (Num.getValue() == "96")
                {
                    txtZ.setText("1,75");
                }
                else if (Num.getValue() == "97")
                {
                    txtZ.setText("1,88");
                }
                else if (Num.getValue() == "98")
                {
                    txtZ.setText("2,05");
                }
                else
                {
                    txtZ.setText("2,33");
                }
                    z = Double.parseDouble(txtZ.getText());
                    ins = Math.round((z * ded * Math.sqrt(L)));
                    txtSS.setText(String.valueOf(ins));
                    Rop = Math.round((((D / dh) * L) + ins));
                    txtROP.setText(String.valueOf(Rop));
             }
        }
    }
    public void LimpiarCampos()
    {
        txtDemanda.clear();
        txtPlazo.clear();
        txtDiasHabiles.clear();
        txtDesviacion.clear();
        txtCostoPedir.clear();
        txtCostoMantenimiento.clear();
        txtCantidad.clear();
        txtROP.clear();
        txtZ.clear();
        txtDesviacionEstandarDemanda.clear();
        txtSS.clear();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.Num.setValueFactory(Per);
        txtDemanda.setOnKeyTyped(e->{ Validar(e);});
        txtPlazo.setOnKeyTyped(e->{ Validar(e);});
        txtDiasHabiles.setOnKeyTyped(e->{ Validar(e);});
        txtDesviacion.setOnKeyTyped(e->{ ValidarDec(e);});
        txtCostoMantenimiento.setOnKeyTyped(e->{ ValidarDec(e);});
        txtCostoPedir.setOnKeyTyped(e->{ ValidarDec(e);});
    }    
    
}
