package com.mycompany.carracesimulation;

import java.util.ArrayList;
import java.util.List;
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

    
    public void initSlideshow() {
        // sound
        
        // visual
         for (int i = 0; i < model.getSlideCount(); i++) {
            String path = "/com/mycompany/carracesimulation/images/" + model.getSlideImageName(i);
            Image image = new Image(getClass().getResource(path).toExternalForm());
            images.add(image);
         }
         
         slideImage.setImage(images.get(0));
        
        Duration duration = new Duration(3);
        
        
    }
}
