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
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author hespinoza
 */
public class QmodelController implements Initializable {
    
    @FXML private Button btnCalcular;
    @FXML private Button btnLimpiar;
    @FXML private TextField txtDemanda;
    @FXML private TextField txtPlazo;
    @FXML private TextField txtDiasHabiles;
    @FXML private TextField txtCostoProducto;
    @FXML private TextField txtCostoPedir;
    @FXML private TextField txtCostoMantenimiento;
    @FXML private TextField txtTasa;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtNumeroPedidos;
    @FXML private TextField txtTiempoPedidos;
    @FXML private TextField txtROP;
    @FXML private TextField txtCostoAnual;
    @FXML private TextField txtSS;
    double D, S, Rop, i, c, qopt, Pd, demp, Ct, np, tp,ss, h;
    int L,dh,vt;
    
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
        if(txtTasa.getText().isEmpty()==true || "0".equals(txtTasa.getText()))
        {
            txtTasa.clear();
        }
        if(txtCostoMantenimiento.getText().isEmpty() ==true || "0".equals(txtCostoMantenimiento.getText()))
        {
            txtCostoMantenimiento.clear();
        }
        if(txtSS.getText().isEmpty()==true)
        {
            ss=0;
        }
        if (txtCostoPedir.getText().equals("")== true || 
                txtDemanda.getText().equals("") == true /*|| 
                txtPlazo.getText().equals("") == true*/)
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
            D = Double.parseDouble(txtDemanda.getText());
            S = Double.parseDouble(txtCostoPedir.getText());
            if (txtDiasHabiles.getText().isEmpty()==true || txtDiasHabiles.getText().equals("0")==true) 
            {
                dh=360;
            }
            else
            {
                dh=Integer.parseInt(txtDiasHabiles.getText());
            }
            c = Double.parseDouble(txtCostoProducto.getText());
            if (txtSS.getText().isEmpty()==true || txtSS.getText().equals("0")==true) 
            {
                ss=0;
            }
            else
            {
                ss=Integer.parseInt(txtSS.getText());
            }
            if(txtCostoMantenimiento.getText().isEmpty() ==true || txtCostoMantenimiento.getText()=="0" )
            {
                i = Double.parseDouble(txtTasa.getText());
                txtCostoMantenimiento.setText(String.valueOf(i*c));
                qopt =Math.round(Math.sqrt((2 *D *S) / (i*c)));
                Ct = Math.round(((D * S) / qopt) + ((qopt * (i * c) / 2) + (D * c)));
            }
            else if (txtTasa.getText()=="" || txtTasa.getText()=="0")
            {
                h=Double.parseDouble(txtCostoMantenimiento.getText());
                txtTasa.setText(String.valueOf(h/c));
                qopt =Math.round(Math.sqrt((2*D*S)/(h)));
                Ct = Math.round(((D * S) / qopt) + ((qopt * (h) / 2) + (D * c)));
            }
            Pd = Math.round(D/qopt); 
            L=(int) Math.round(dh/Pd);
            demp = Math.round(D/Pd);
            Rop = Math.round((D / dh) * L)+ss;
            txtPlazo.setText(String.valueOf(L));
            txtCantidad.setText(String.valueOf(qopt));
            txtNumeroPedidos.setText(String.valueOf(Pd));
            np = Double.parseDouble(txtNumeroPedidos.getText());
            tp = Math.round(dh / np);
            txtTiempoPedidos.setText(String.valueOf(tp));
            txtROP.setText(String.valueOf(Rop));
            txtCostoAnual.setText(String.valueOf(Ct));
            
        }
    }
    public void LimpiarCampos()
    {
        txtDemanda.clear();
        txtPlazo.clear();
        txtDiasHabiles.clear();
        txtCostoProducto.clear();
        txtCostoPedir.clear();
        txtCostoMantenimiento.clear();
        txtTasa.clear();
        txtCantidad.clear();
        txtNumeroPedidos.clear();
        txtTiempoPedidos.clear();
        txtROP.clear();
        txtCostoAnual.clear();
        txtSS.clear();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        txtDemanda.setOnKeyTyped(e->{ Validar(e);});
        txtPlazo.setOnKeyTyped(e->{ Validar(e);});
        txtDiasHabiles.setOnKeyTyped(e->{ Validar(e);});
        txtSS.setOnKeyTyped(e->{ Validar(e);});
        txtCostoProducto.setOnKeyTyped(e->{ ValidarDec(e);});
        txtCostoMantenimiento.setOnKeyTyped(e->{ ValidarDec(e);});
        txtCostoPedir.setOnKeyTyped(e->{ ValidarDec(e);});
        
        btnCalcular.setOnAction(e -> { Calcular(); });
        btnLimpiar.setOnAction(e -> { LimpiarCampos(); });
    }
}