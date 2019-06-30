/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

/**
 *
 * @author espinoza
 */
public class param {
    String type;
    String name;
    Object value;
    
    public param(String t, String n, Object v){
        this.type = t;
        this.name = n;
        this.value = v;
    }
    
    public String getType(){ return this.type; }
    public Object getValue(){ return this.value; }
    public String getName(){ return this.name; };
}
