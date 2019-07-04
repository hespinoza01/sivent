/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.view.AggregatePlanning;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author hespinoza
 */
public class datosEntity {
    public final SimpleIntegerProperty periodo = new SimpleIntegerProperty(-1);
    public final SimpleIntegerProperty demanda = new SimpleIntegerProperty(0);
    public final SimpleIntegerProperty diasHabiles = new SimpleIntegerProperty(0);
    
    public datosEntity(){}
    
    public int getPeriodo(){ return this.periodo.get(); }
    public datosEntity setPeriodo(int value){
        this.periodo.set(value);
        return this;
    }
    public IntegerProperty getPeriodoProperty(){ return this.periodo; }
    
    public int getDemanda(){ return this.demanda.get(); }
    public datosEntity setDemanda(int value){
        this.demanda.set(value);
        return this;
    }
    public IntegerProperty getDemandaProperty(){ return this.demanda; }
    
    public int getDiasHabiles(){ return this.diasHabiles.get(); }
    public datosEntity setDiasHabiles(int value){
        this.diasHabiles.set(value);
        return this;
    }
    public IntegerProperty getDiasHabilesProperty(){ return this.diasHabiles; }
    
    public boolean isNew(){ return this.getPeriodo() == -1; }
}
