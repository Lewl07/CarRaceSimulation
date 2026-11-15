package com.mycompany.carracesimulation;

/**
 *
 * @author Leo
 */
public class CarRaceModel {

    public static class Car {
        public Type type;
        public int num;
        public double[][] position;
        public double speed;
        public Color color;
        
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
    
    public enum Type {
        COUPE, SUV, SEDAN, SUPERCAR
    }
    
    public enum Color {
        RED, YELLOW, WHITE, BLUE
    }
}
