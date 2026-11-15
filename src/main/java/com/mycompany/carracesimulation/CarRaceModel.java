package com.mycompany.carracesimulation;

/**
 *
 * @author Leo
 */
public class CarRaceModel {

    public class Car {
        public Type type;
        public int num;
        public double[][] position;
        public double speed;
        public Color color;
        
        public Car(Type type, int num, double[][] position, double speed, Color color) {
            type = this.type;
            num = this.num;
            position = this.position;
            speed = this.speed;
            color = this.color;
        }
    }
    
    public enum Type {
        COUPE, SUV, SEDAN, SUPERCAR
    }
    
    public enum Color {
        RED, YELLOW, WHITE, BLUE
    }
}
