/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 * @author Mark Work Account
 */
public class ElementConfig {
    public void fadeIn(Node node) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(100));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
    
    public void fadeOut(Node node) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(100));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }
    
    public void setSelected(String imageLocation, Label label, Circle circle, ImageView icon) {
        Image newImage = new Image(imageLocation);
        label.setTextFill(Color.web("#2f9efe"));
        circle.setOpacity(1.0);
        icon.setImage(newImage);
    }
    
    public void setUnselected(String imageLocation, Label label, Circle circle, ImageView icon) {
        Image newImage = new Image(imageLocation);
        label.setTextFill(Color.web("#a5b4d9"));
        circle.setOpacity(0);
        icon.setImage(newImage);
    }
    
    public void pressIcon(ImageView image) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), image);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(0.9);
        scaleTransition.setToY(0.9);
        scaleTransition.play();
    }
    
    public void releaseIcon(ImageView image) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), image);
        scaleTransition.setFromX(0.9);
        scaleTransition.setFromY(0.9);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }
    
    public void hoverIcon(ImageView image) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), image);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.play();
    }
    
    public void unhoverIcon(ImageView image) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), image);
        scaleTransition.setFromX(1.1);
        scaleTransition.setFromY(1.1);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }
}
