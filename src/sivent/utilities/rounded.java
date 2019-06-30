/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author espinoza
 */
public final class rounded {
    private rounded(){}
    
    public static void roundImageView(ImageView iv, Image i){
        double w = iv.getFitWidth(), h = iv.getFitHeight();
        iv.setImage(i);
        
        Rectangle clip = new Rectangle(w, h);
        clip.setArcWidth(w);
        clip.setArcHeight(h);
        iv.setClip(clip);
        
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = iv.snapshot(parameters, null);
        
        iv.setClip(null);
        iv.setImage(image);
    }
    
}
