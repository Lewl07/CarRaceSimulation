package com.mycompany.carracesimulation;

import com.mycompany.carracesimulation.CarRaceModel.State;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CarRaceController {
    
    private final CarRaceModel model = new CarRaceModel();
    private List<Image> images = new ArrayList<>();
    private final List<Group> carShapes = new ArrayList<>();
    private final List<Line> backSpokes = new ArrayList<>();     // the line in each car's back wheel
    private final List<Line> frontSpokes = new ArrayList<>();    // the line in each car's front wheel

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
    
    @FXML private Label messageLbl;
    @FXML private ImageView trophyImage;
    
    @FXML private Line line1;
    @FXML private Line line2;
    @FXML private Line line3;
    
    public void initialize() {
        greetingLbl.setText(model.getGreeting());

        // Scale the whole track (lines and cars) with the window.
        // 900 and 380 are the track's design size. Using the smaller ratio keeps the proportions.
        trackPane.scaleXProperty().bind(Bindings.min(
                trackHolder.widthProperty().divide(900),
                trackHolder.heightProperty().divide(380)));
        trackPane.scaleYProperty().bind(trackPane.scaleXProperty());

        // The slideshow shows first, hence hide the race screen for now
        racePane.setVisible(false);
        racePane.setManaged(false);

        // Draw the 4 cars. Y is set once (the lane); only X changes during the race.
        for (CarRaceModel.Car car : model.getCars()) {
            Group shape = makeCar(car);
            shape.setLayoutY(car.getY());
            carShapes.add(shape);
            trackPane.getChildren().add(shape);
        }

        // Speed slider: updates the model and the "1.0x" label
        speedSlider.setMin(CarRaceModel.MIN_SPEED);
        speedSlider.setMax(CarRaceModel.MAX_SPEED);
        speedSlider.setValue(model.getSpeedMultiplier());
        speedSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            model.setSpeedMultiplier(newValue.doubleValue());
            multiplierLbl.setText(String.format("%.1fx", newValue.doubleValue()));
        });

        // Buttons
        startBtn.setOnAction(e -> model.start());
        pauseBtn.setOnAction(e -> model.pause());
        resetBtn.setOnAction(e -> model.reset());

        // Race loop: every 16 ms, move the cars and refresh the screen
        Timeline raceLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> updateRace()));
        raceLoop.setCycleCount(Animation.INDEFINITE);
        raceLoop.play();

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
            racePane.setVisible(true);
            racePane.setManaged(true);
            audioPlayer.stop();
        }));
        
        tl.play();
    }

    /** Build one car out of shapes. Coordinates are measured from the car's own top-left corner.
     */
    private Group makeCar(CarRaceModel.Car car) {
        Color color = Color.valueOf(car.color.name());     // RED becomes Color.RED

        Rectangle body = new Rectangle(0, 20, 80, 22);
        body.setFill(color);

        Rectangle cabin = new Rectangle(20, 6, 40, 16);
        cabin.setFill(color.darker());

        Rectangle window = new Rectangle(26, 9, 28, 10);
        window.setFill(Color.LIGHTCYAN);

        // Wheels: a black circle with a grey line through the middle
        Circle wheel1 = new Circle(20, 44, 8, Color.BLACK);
        Circle wheel2 = new Circle(60, 44, 8, Color.BLACK);

        Line spoke1 = new Line(14, 44, 26, 44);
        spoke1.setStroke(Color.LIGHTGRAY);
        spoke1.setStrokeWidth(2);

        Line spoke2 = new Line(54, 44, 66, 44);
        spoke2.setStroke(Color.LIGHTGRAY);
        spoke2.setStrokeWidth(2);

        backSpokes.add(spoke1);
        frontSpokes.add(spoke2);

        return new Group(body, cabin, window, numberBackground, number, wheel1, wheel2, spoke1, spoke2);
    }
    
        /** One tick: the model moves the cars, then the screen shows where they are.
         */
    private void updateRace() {
        model.update();
        
        if (model.getState() == State.IDLE) {
            trophyImage.setVisible(false);
        }
        
        for (int i = 0; i < carShapes.size(); i++) {
            double x = model.getCars().get(i).getX();
            carShapes.get(i).setLayoutX(x);       // only X changes

        // The wheel lines turn clockwise: 7 degrees for every pixel the car moves
            backSpokes.get(i).setRotate(x * 7);
            frontSpokes.get(i).setRotate(x * 7);
        }
        
        messageLbl.setText(model.getMessage());
        
        if (model.getState() == State.FINISHED) {
            trophyImage.setVisible(true);
        }
    }
    
}
