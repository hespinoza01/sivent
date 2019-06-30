/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import DB.conexion;
import java.math.BigDecimal;
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author hespinoza
 */
public abstract class OperationEntity {
    private final StringProperty id = new SimpleStringProperty("N/A");
    private Date fechaRealizacion = new Date();
    private final IntegerProperty tipoPago = new SimpleIntegerProperty(0);
    private BigDecimal total = new BigDecimal(0);
    private final StringProperty fechaModificacion = new SimpleStringProperty("N/A");
    
    final conexion c = new conexion();
    
    public String getId() { return id.get(); }
    public void setId(String value) { id.set(value); }
    public StringProperty getIdProperty() { return id; }
    
    public Date getFechaRealizacion() { return fechaRealizacion; }
    public void setFechaRealizacion(Date value) { this.fechaRealizacion = value; }

    public int getTipoPago() { return tipoPago.get(); }
    public void setTipoPago(int value) { tipoPago.set(value); }
    public IntegerProperty getTipoPagoProperty() { return tipoPago; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal value) { this.total=value; }

    public String getFechaModificacion() { return fechaModificacion.get(); }
    public void setFechaModificacion(String value) { fechaModificacion.set(value); }
    public StringProperty getFechaModificacionProperty() { return fechaModificacion; }
    
}
