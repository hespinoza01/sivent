/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author espinoza
 */
public final class valueDate {
    private valueDate(){}
    
    
    public static LocalDate getValue(Date value){
        Instant i = value.toInstant();
        return i.atZone(ZoneId.systemDefault()).toLocalDate();        
    }
    
    public static Date setValue(LocalDate value){
        LocalDate localdate = value;
        Instant instant = Instant.from(localdate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        
        return date;
    }
}
