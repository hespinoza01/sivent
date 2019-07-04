/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.AggregatePlanning;

import java.math.BigDecimal;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author hespinoza
 */

public class resultadoEntity {
    public final SimpleIntegerProperty periodo = new SimpleIntegerProperty(-1);
    public final SimpleIntegerProperty requerimientoProduccion = new SimpleIntegerProperty(0);
    public final SimpleIntegerProperty diasHabiles = new SimpleIntegerProperty(0);
    public ObjectProperty<BigDecimal> hrsProduccionRequeridas = new SimpleObjectProperty(new BigDecimal(0));
    public final SimpleIntegerProperty produccionReal = new SimpleIntegerProperty(0);
    public final SimpleIntegerProperty unidadesSubcontratadas = new SimpleIntegerProperty(0);
    public ObjectProperty<BigDecimal> costoUnidadesSubcontratadas = new SimpleObjectProperty(new BigDecimal(0));
    public ObjectProperty<BigDecimal> costoUnidadesProducidas = new SimpleObjectProperty(new BigDecimal(0));
    public ObjectProperty<BigDecimal> totalPeriodo = new SimpleObjectProperty(new BigDecimal(0));
    
    public resultadoEntity(){}

    public int getPeriodo(){ return this.periodo.get(); }
    public resultadoEntity setPeriodo(int value){
        this.periodo.set(value);
        return this;
    }
    public IntegerProperty getPeriodoProperty(){ return this.periodo; }
    
    public int getRequirimientosProduccion(){ return this.requerimientoProduccion.get(); }
    public resultadoEntity setRequerimientosProduccion(int value){
        this.requerimientoProduccion.set(value);
        return this;
    }
    public IntegerProperty getRequerimientosProduccion(){ return this.requerimientoProduccion; }
    
    public int getDiasHabiles(){ return this.diasHabiles.get(); }
    public resultadoEntity setDiasHabiles(int value){
        this.diasHabiles.set(value);
        return this;
    }
    public IntegerProperty getDiasHabilesProperty(){ return this.diasHabiles; }
    
    public BigDecimal getHrsProduccionRequeridas(){ return this.hrsProduccionRequeridas.get(); }
    public resultadoEntity setHrsProduccionRequeridas(BigDecimal value){
        this.hrsProduccionRequeridas.set(value);
        return this;
    }
    public ObjectProperty<BigDecimal> getHrsProduccionRequeridasProperty(){ return this.hrsProduccionRequeridas; }
    
    public int getProduccionReal(){ return this.produccionReal.get(); }
    public resultadoEntity setproduccionReal(int value){
        this.produccionReal.set(value);
        return this;
    }
    public IntegerProperty getProduccionRealProperty(){ return this.produccionReal; }
    
    public int getUnidadesSubcontratadas(){ return this.unidadesSubcontratadas.get(); }
    public resultadoEntity setUnidadesSubcontratadas(int value){
        this.unidadesSubcontratadas.set(value);
        return this;
    }
    public IntegerProperty getunidadesSubcontratadasProperty(){ return this.unidadesSubcontratadas; }
    
    public BigDecimal getCostoUnidadesSubcontratadas(){ return this.costoUnidadesSubcontratadas.get(); }
    public resultadoEntity setCostoUnidadesSubcontratadas(BigDecimal value){
        this.costoUnidadesSubcontratadas.set(value);
        return this;
    }
    public ObjectProperty<BigDecimal> getCostoUnidadesSubcontratadasProperty(){ return this.costoUnidadesSubcontratadas; }
    
    public BigDecimal getCostoUnidadesProducidas(){ return this.costoUnidadesProducidas.get(); }
    public resultadoEntity setCostoUnidadesProducidas(BigDecimal value){
        this.costoUnidadesProducidas.set(value);
        return this;
    }
    public ObjectProperty<BigDecimal> getCostoUnidadesProducidasProperty(){ return this.costoUnidadesProducidas; }
    
    public BigDecimal getTotalPeriodo(){ return this.totalPeriodo.get(); }
    public resultadoEntity setTotalPeriodo(BigDecimal value){
        this.totalPeriodo.set(value);
        return this;
    }
    public ObjectProperty<BigDecimal> getTotalPeriodoProperty(){ return this.totalPeriodo; }
}
