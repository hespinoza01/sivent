/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author espinoza
 */
public final class chooseImage {
    private chooseImage(){}
    
    public static void choose(ImageView i){
        FileChooser fc = new FileChooser();
            
        // Establecer filtro de extenciones
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("Archivos JPG(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("Archivos PNG (*.png)", "*.PNG");

        File file = fc.showOpenDialog(null);
        try{
            BufferedImage  bi = ImageIO.read(file);
            Image img = SwingFXUtils.toFXImage(bi, null);
            rounded.roundImageView(i, img);
        }catch(IOException ex){}
    }
}
