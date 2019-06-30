/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author espinoza
 */
public class SearchResult {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty modificationdate;
    
    public SearchResult(){
        this(null, null, null);
    }
    
    public SearchResult(String _id, String _name, String _md){
        this.id = new SimpleStringProperty(_id);
        this.name = new SimpleStringProperty(_name);
        this.modificationdate = new SimpleStringProperty(_md);
    }
    
    public String getId(){ return this.id.get(); }
    public void setId(String v){ this.id.set(v); }
    public StringProperty getIdProperty(){ return this.id; }
    
    public String getName(){ return this.name.get(); }
    public void setName(String v){ this.name.set(v); }
    public StringProperty getNameProperty(){ return this.name; }
    
    public String getModificationDate(){ return this.modificationdate.get(); }
    public void setModificationDate(String v){ this.modificationdate.set(v); }
    public StringProperty getModificationDateProperty(){ return this.modificationdate; }
}
