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
    public static final double LANE_HEIGHT = 95;
    public static final double MIN_SPEED = 0.5;
    public static final double MAX_SPEED = 3.0;

    private static final String GREETING = "WELCOME TO CAR RACE SIMULATOR!";

    private static final String[] SLIDE_IMAGES = {
        "Red Coupe #7.png",
        "White Sedan #9.png",
        "Blue Supercar #10.png",
        "Yellow SUV #1.png"
    };

    public static class Car {
        public Type type;
        public int num;
        public double[][] position; // X position determines the final race ranking
        public double speed;        // px moved per tick, picked randomly in reset()
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
    private final List<Car> finishOrder = new ArrayList<>();   // cars that crossed, first to last
    private State state = State.IDLE;
    private double speedMultiplier = 1.0;

    public CarRaceModel() {
        // Lane order from top to bottom: red, white, blue, yellow]
        cars.add(new Car(Type.COUPE,    7,  startPosition(0), 0, Color.RED));
        cars.add(new Car(Type.SEDAN,    9,  startPosition(1), 0, Color.WHITE));
        cars.add(new Car(Type.SUPERCAR, 10, startPosition(2), 0, Color.BLUE));
        cars.add(new Car(Type.SUV,      1,  startPosition(3), 0, Color.YELLOW));
        reset();    // gives every car its first random speed
    }

    /**
     * Y is the top of the car's shapes. 19 puts the car in the middle of its lane.
     * Y never changes during the race: only X moves.
     * Calculates the starting position of a car:
     * 1st lane: Red Coupe
     * 2nd lane: White Sedan
     * 3rd lane: Blue Supercar
     * 4rd  lane: Yellow SUV
     * @param lane the lane
     * @return the starting position of the car
     */
    private double[][] startPosition(int lane) {
        return new double[][] {
            {START_X, 19 + lane * LANE_HEIGHT}
        };
    }

    // Slideshow
    public String getGreeting() {
        return GREETING;
    }

    public int getSlideCount() {
        return SLIDE_IMAGES.length;
    }

    public String getSlideImageName(int i) {
        return SLIDE_IMAGES[i];
    }

    // Race
    public List<Car> getCars() {
        return cars;
    }

    public State getState() {
        return state;
    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(double num) {
        speedMultiplier = Math.max(MIN_SPEED, Math.min(num, MAX_SPEED));
    }
    
    public List<Car> getFinishOrder() {
        return finishOrder;
    }

    /** Start a race, resume after Pause, or start over after a finished race.
     */
    public void start() {
        if (state == State.FINISHED) {
            reset();
        }
        state = State.RUNNING;
    }

    /**
     * Pause the race
     */
    public void pause() {
        if (state == State.RUNNING) {
            state = State.PAUSED;
        }
    }

    /**
     * Back to the start line, with a new random speed for every car.
     */
    public void reset() {
        state = State.IDLE;
        finishOrder.clear();
        for (Car car : cars) {
            car.setX(START_X);
            car.finished = false;
            car.speed = 1.6 + random.nextDouble() * 0.6;    // stays the same for the whole race
        }
    }

    /** One step of the race. The controller calls this again and again.
     */
    public void update() {
        if (state != State.RUNNING) {
            return;
        }
        for (Car car : cars) {
            if (car.finished) {
                continue;
            }
            car.setX(car.getX() + car.speed * speedMultiplier);
            if (car.getX() + CAR_WIDTH >= FINISH_X) {
                car.setX(FINISH_X - CAR_WIDTH);
                car.finished = true;
                finishOrder.add(car);
            }
        }
        if (finishOrder.size() == cars.size()) {
            state = State.FINISHED;
        }
    }

    /** Cars that finished come first, then the others from front to back.
     */
    public List<Car> getRanking() {
        List<Car> ranking = new ArrayList<>(finishOrder);

        List<Car> others = new ArrayList<>();
        for (Car car : cars) {
            if (!car.finished) {
                others.add(car);
            }
        }
        while (!others.isEmpty()) {
            Car front = others.get(0);
            for (Car car : others) {
                if (car.getX() > front.getX()) {
                    front = car;
                }
            }
            ranking.add(front);
            others.remove(front);
        }
        return ranking;
    }

    /** The text for the message area.
     */
    public String getMessage() {
        String text;

        if (state == State.IDLE) {
            return "Press Start to begin the Race Simulation !";
        } else if (state == State.PAUSED) {
            text = "Paused\n";
        } else if (state == State.FINISHED) {
            text = carName(finishOrder.get(0)) + " wins!\n";
        } else {
            text = "Ranking\n";
        }

        for (int i = 0; i < finishOrder.size(); i++) {
            text += (i + 1) + ". " + carName(finishOrder.get(i)) + "\n";
        }

        return text;
    }


    private String carName(Car car) {
        return car.color + " " + car.type + " #" + car.num;
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
