package com.mycompany.carracesimulation;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class CarRaceController {
    
    private final CarRaceModel model = new CarRaceModel();
    private List<Image> images = new ArrayList<>();

    @FXML private Label greetingLbl;
    @FXML private StackPane racePane;
    @FXML private ImageView slideImage;
    @FXML private StackPane slideshowPane;
    
    public void initialize() {
        greetingLbl.setText(model.getGreeting());
        initSlideshow();
        
    }

    /**
     * Initialize the slideshow upon app launch.
     * Each car appears by fading in and out (0.7s fade-in, stays for 3s, then fades out for 0.7s)
     * The timeline of the slideshow is 16.9s (4 cars)
     */
    public void initSlideshow() {
        for (int i = 0; i < model.getSlideCount(); i++) {
            String path = "/com/mycompany/carracesimulation/images/" + model.getSlideImageName(i);
            Image image = new Image(getClass().getResource(path).toExternalForm());
            images.add(image);
        }
        
        slideImage.setImage(images.get(0));
        slideImage.setOpacity(0);
        
        Timeline tl = new Timeline();
        
        tl.getKeyFrames().add(new KeyFrame(Duration.ZERO, e -> {
            slideImage.setImage(images.get(0));
            slideImage.setOpacity(1);
            
            FadeTransition fade = new FadeTransition(Duration.millis(700), slideImage);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();
        }));
        
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(4.4), e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(700), slideImage);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> {
                slideImage.setImage(images.get(1));
                FadeTransition fadeIn = new FadeTransition(Duration.millis(700), slideImage);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            });
            fadeOut.play();
        }));
        
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(8.8), e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(700), slideImage);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> {
                slideImage.setImage(images.get(2));
                FadeTransition fadeIn = new FadeTransition(Duration.millis(700), slideImage);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            });
            fadeOut.play();
        }));
        
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(13.2), e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(700), slideImage);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> {
                slideImage.setImage(images.get(3));
                FadeTransition fadeIn = new FadeTransition(Duration.millis(700), slideImage);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            });
            fadeOut.play();
        }));
        
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(16.9), e -> {
            slideshowPane.setOpacity(0);
        }));
        
        tl.play();
    }
    
}
