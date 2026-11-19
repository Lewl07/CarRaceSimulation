package com.mycompany.carracesimulation;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class CarRaceController {
    
    private final CarRaceModel model = new CarRaceModel();
    private List<Image> images = new ArrayList<>();

    // slideshow
    @FXML private Label greetingLbl;
    @FXML private ImageView slideImage;
    @FXML private StackPane slideshowPane;
    
    // race
    @FXML private StackPane racePane;
    @FXML private StackPane trackHolder;
    @FXML private AnchorPane trackPane;
    
    @FXML private Button startBtn;
    @FXML private Button pauseBtn;
    @FXML private Button resetBtn;
    
    @FXML private Slider speedSlider;
    @FXML private Label multiplierLbl;
    
    @FXML private Line line1;
    @FXML private Line line2;
    @FXML private Line line3;
    
    public void initialize() {
        greetingLbl.setText(model.getGreeting());
        
        
        // Scale the whole track (lines, and later the cars) with the window.
        // 900 and 380 are the track's design size. Using the smaller ratio keeps the proportions.
        trackPane.scaleXProperty().bind(Bindings.min(
                trackHolder.widthProperty().divide(900),
                trackHolder.heightProperty().divide(380)));
        
        trackPane.scaleYProperty().bind(trackPane.scaleXProperty());
        
//        racePane.setVisible(false);
//        racePane.setManaged(false);
//        initSlideshow();
        

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
        
        String audioPath = getClass()
        .getResource("/com/mycompany/carracesimulation/sounds/carSoundFX.mp3")
        .toExternalForm();
        
        Media media = new Media(audioPath);
        MediaPlayer audioPlayer = new MediaPlayer(media);

        audioPlayer.setStartTime(Duration.ZERO);
        audioPlayer.play();
        
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
        
        // The StackPane slideshowPane disappears after timeline reaches 16.9s
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(16.9), e -> {
            slideshowPane.setVisible(false);
            slideshowPane.setVisible(false);
            audioPlayer.stop();
        }));
        
        tl.play();
    }
    
}
