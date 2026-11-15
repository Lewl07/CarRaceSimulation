package com.mycompany.carracesimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Leo
 */
public class CarRaceModel {
    
    public static final double START_X = 15;
    public static final double FINISH_X = 860;      // pane is 900 px wide
    public static final double CAR_WIDTH = 80;
    public static final double LANE_HEIGHT = 90;
    public static final double MIN_SPEED = 0.5;
    public static final double MAX_SPEED = 3.0;
    
    private static final String GREETING = "WELCOME TO CAR RACE SIMULATOR!";
    
    private static final String[] SLIDE_IMAGES = {
        "Blue Supercar #10.png",
        "White Sedan #9.png",
        "Red Coupe #7.png",
        "Yellow SUV #1.png"
    };

    public static class Car {
        public Type type;
        public int num;
        public double[][] position; // X position determines the final race ranking
        public double speed;        // pixels per second determined randomly
        public Color color;
        public boolean finished;    // Whether the car has finished the race
        
        public Car(Type type, int num, double[][] position, double speed, Color color) {
            this.type = type;
            this.num = num;
            this.position = position;
            this.speed = speed;
            this.color = color;
        }
        
        public double getX() {
            return position[0][0];
        }
        
        public double getY() {
            return position[0][1];
        
        }
        public void setX(double x) {
            position[0][0] = x;
        }
    }
    
    private final Random random = new Random();
    private final List<Car> cars = new ArrayList<>();
    private final List<Car> finishOrder = new ArrayList<>();
    private State state = State.IDLE;
    private double speedMultiplier = 1.0;

    public CarRaceModel() {
        cars.add(new Car((Type.COUPE), 7, startPosition(0), 0, Color.RED));
        cars.add(new Car((Type.SUV), 1, startPosition(1), 0, Color.YELLOW));
        cars.add(new Car((Type.SEDAN), 9, startPosition(0), 0, Color.WHITE));
        cars.add(new Car((Type.SUPERCAR), 10, startPosition(0), 0, Color.BLUE));
    }
    
    private double[][] startPosition(int lane) {
         return new double[][] {
             {START_X, 15 + lane * LANE_HEIGHT }
         };
    }

    public enum Type {
        COUPE, SUV, SEDAN, SUPERCAR
    }
    
    public enum Color {
        RED, YELLOW, WHITE, BLUE
    }
    
    public enum State {
        IDLE, RUNNING, PAUSED, FINISHED
    }
}
