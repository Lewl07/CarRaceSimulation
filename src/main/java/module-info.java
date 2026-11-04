module com.mycompany.car.race.simulation {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.car.race.simulation to javafx.fxml;
    exports com.mycompany.car.race.simulation;
}
