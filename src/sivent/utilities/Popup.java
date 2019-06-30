/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author hespinoza
 */
public final class Popup {

    private Popup(){}
    
    public static void error(String title, String message){
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(NotificationType.ERROR);
        tray.setRectangleFill(Paint.valueOf("#dc143c"));
        tray.showAndDismiss(new Duration(5000));
    }
    
    public static void success(String title, String message){
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.setRectangleFill(Paint.valueOf("#2E8B57"));
        tray.showAndDismiss(new Duration(5000));
    }
    
    public static void warning(String title, String message){
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(NotificationType.WARNING);
        tray.setRectangleFill(Paint.valueOf("#ff8000"));
        tray.showAndDismiss(new Duration(5000));
    }
    
    public static void info(String title, String message){
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(NotificationType.INFORMATION);
        tray.setRectangleFill(Paint.valueOf("##4169e1"));
        tray.showAndDismiss(new Duration(5000));
    }
}

